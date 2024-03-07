package com.orchware.commons.module.encryption.service.encryption;

import com.orchware.commons.module.encryption.dto.enc.asymmetric.AsymmetricPem;
import com.orchware.commons.module.encryption.model.SystemAttribute;
import com.orchware.commons.module.encryption.service.attribute.ESystemAttribute;
import com.orchware.commons.module.encryption.service.attribute.SystemAttributeService;
import com.orchware.commons.module.storage.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class EncryptionUtils {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final String AES = "AES";
    private final String BLOCK_OPERATION_MODE = "CBC";
    private final String PADDING_TYPE = "PKCS5Padding";
    private final String AES_MODE = AES + "/" + BLOCK_OPERATION_MODE + "/" + PADDING_TYPE;
    private final int SYMMETRIC_KEY_SIZE = 256;
    private final int ASYMMETRIC_KEY_SIZE = 2048;
    private final String SPLITTER = "\\.";
    private final int AES_IV_SIZE = 16;
    private final int DEFAULT_SALT_SIZE = 32;
    private final int DEFAULT_ITERATIONS = 128;
    private final int DEFAULT_AES_KEY_SIZE = 128;
    private final int INDEX_SALT = 0;
    private final int INDEX_IV = 1;
    private final int INDEX_ENCRYPTED_DATA = 2;

    @Value("${app.profile}")
    private String appProfile;
    @Autowired
    private SystemAttributeService systemAttributeService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private KeyUtils keyUtils;
    @Autowired
    private PEMUtils pemUtils;

    public void init() {

        // Generate secret key
        SecretKey secretKey = keyUtils.generateSymmetricKey("AES", SYMMETRIC_KEY_SIZE);

        // Generates New public and private keys
        KeyPair keyPair = keyUtils.generateAsymmetricKeys("RSA", ASYMMETRIC_KEY_SIZE);

        // Save secret key
        saveOrUpdate(ESystemAttribute.SECRET_KEY, secretKey.getEncoded());

        // Save public key
        X509EncodedKeySpec x509EncodedKeySpec =
                new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
        saveOrUpdate(ESystemAttribute.PUBLIC_KEY, x509EncodedKeySpec.getEncoded());

        // Save private key
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec =
                new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
        saveOrUpdate(ESystemAttribute.PRIVATE_KEY, pkcs8EncodedKeySpec.getEncoded());
    }

    private void saveOrUpdate(ESystemAttribute eSystemAttribute, byte[] bytes) {
        SystemAttribute keyAttribute =
                systemAttributeService.findByAttributeKey(eSystemAttribute.getAttributeKey());
        if (keyAttribute == null) {
            systemAttributeService.save(SystemAttribute.builder()
                    .attributeKey(eSystemAttribute.getAttributeKey())
                    .attributeValueLob(bytes)
                    .attributeDescription(eSystemAttribute.getAttributeDescription())
                    .build());
        } else {
            keyAttribute.setAttributeValueLob(bytes);
            keyAttribute.setAttributeDescription(eSystemAttribute.getAttributeDescription());
            systemAttributeService.update(keyAttribute);
        }
        /*if (eSystemAttribute == ESystemAttribute.PRIVATE_KEY) {
            try {
                int pkLength = keyAttribute.getAttributeValueLob().length;
                byte[] ppk2 = new byte[pkLength - 10];
                System.arraycopy(keyAttribute.getAttributeValueLob(), 10, ppk2, 0, pkLength - 10);
                storageService.uploadByteArray(getPPK2().getBytes(), appProfile, "orchware/keyvalue", "ppk2");
            } catch (URISyntaxException | StorageException e) {
                LOGGER.error(e.getMessage(), e);
                throw e;
            }
        }*/
    }

    // https://stackify.dev/639389-aes-encryption-using-java-and-decryption-using-javascript
//    private String encryptAES(String password, String data) {
//        SecureRandom random = new SecureRandom();
//        byte[] iv = new byte[AES_IV_SIZE];
//        random.nextBytes(iv);
//        byte[] salt = new byte[DEFAULT_SALT_SIZE];
//        random.nextBytes(salt);
//        byte[] encrypted = new byte[0];
//        try {
//            byte[] key = pbkdf2(password, salt);
//            Cipher cipher = Cipher.getInstance(ENCRYPTION_MODE);
//            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ENCRYPTION_ALGORITHM), new IvParameterSpec(iv));
//            encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
//        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
//            System.out.println("Exception caught while encrypting : " + ex);
//        }
//        return toBase64(salt) + "," + toBase64(iv) + "," + toBase64(encrypted);
//    }

    // Symmetric Encryption ////////////////////////////////////////////////////
    String generateSecretKey(String algorithm) {
        SecretKey secretKey = keyUtils.generateSymmetricKey(algorithm, SYMMETRIC_KEY_SIZE);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    String generateSecretKeyFromPassword(String algorithm, String password, String salt) {
        SecretKey secretKey = keyUtils.generateSymmetricKeyFromPassword(algorithm, password, salt, SYMMETRIC_KEY_SIZE);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    byte[] generateIVBytes() {
        byte[] iv = new byte[AES_IV_SIZE];
        new SecureRandom().nextBytes(iv);
//        return new IvParameterSpec(iv);
        return iv;
    }

    String encryptSymmetric(String algorithm, String input, SecretKey key, byte[] ivBytes)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] encrypted = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivBytes != null ? new IvParameterSpec(ivBytes) : null);
            encrypted = cipher.doFinal(input.getBytes());
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException
                 | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            LOGGER.error("Exception caught while encrypting", ex);
            throw ex;
        }
        if (ivBytes != null) {
            return Base64.getEncoder().encodeToString(encrypted) + Base64.getEncoder().encodeToString(".".getBytes())
                    + Base64.getEncoder().encodeToString(ivBytes);
        } else {
            return Base64.getEncoder().encodeToString(encrypted);
        }
    }

    String decryptSymmetric(String algorithm, String cipherText, SecretKey key, byte[] ivBytes)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] plainText = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, ivBytes != null ? new IvParameterSpec(ivBytes) : null);
            plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException
                 | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            LOGGER.error("Exception caught while decrypting", e);
            throw e;
        }
        return new String(plainText);
    }
    // Asymmetric Encryption ////////////////////////////////////////////////////
    AsymmetricPem generatePemStrings(String algorithm) {
        KeyPair keyPair = keyUtils.generateAsymmetricKeys("RSA", ASYMMETRIC_KEY_SIZE);
        return AsymmetricPem.builder()
                .privateKeyPem(pemUtils.getPemString(algorithm, keyPair.getPrivate()))
                .publicKeyPem(pemUtils.getPemString(algorithm, keyPair.getPublic()))
                .build();
    }

    String encryptAsymmetric(String plainText, String algorithm, Key key) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (plainText == null || plainText.isEmpty()) {
            System.out.println("No data to encrypt!");
            return plainText;
        }
        Cipher cipher;
        String encryptedString = null;
        try {
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            encryptedString = Base64.getEncoder().encodeToString(encryptedText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                 | BadPaddingException e) {
            LOGGER.error("Exception caught while encrypting", e);
            throw e;
        }
        return encryptedString;
    }

    String decryptAsymmetric(String cipherText, String algorithm, Key key) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (cipherText == null || cipherText.isEmpty()) {
            System.out.println("No data to decrypt!");
            return cipherText;
        }
        String decryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(cipherText.getBytes());
            decryptedString = new String(cipher.doFinal(encryptedText));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException
                 | BadPaddingException e) {
            LOGGER.error("Exception caught while decrypting", e);
            throw e;
        }
        return decryptedString;
    }
    ////////////////////////////////////////////////////



    /*private byte[] pbkdf2(String password, byte[] salt) throws NoSuchAlgorithmException {
        KeySpec keySpec =
                new PBEKeySpec(password.toCharArray(), salt, DEFAULT_ITERATIONS, DEFAULT_AES_KEY_SIZE);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = null;
        try {
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException ex) {
            System.out.println("InvalidKeySpecException : " + ex);
        }
        return secretKey.getEncoded();
    }*/

    public String getPPK2() {
        String privateKeyPEMString = null;
//                pemUtils.getPEMString("RSA", PrivateKey.class);
        return Base64.getEncoder().encodeToString(privateKeyPEMString.substring(10).getBytes());
    }

    /**
     * <pre>
     * Encrypt plain text using AES
     * Encrypt the key using RSA public key
     * Refer README.md
     * </pre>
     */
    /*public List<String> encryptWithRSAWorkflow(String plainText) {
//        UserDetails details = (UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        String password = null;
        *//*if (details instanceof UserDetailsImpl) {
            password = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getPassword();
        } else if (details instanceof TenantDetailsImpl) {
            password = ((TenantDetailsImpl) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getPassword();
        }*//*
        List<String> strings = new ArrayList<>();
        SecretKey secretKey = keyUtils.loadSecretKey("AES");
//        strings.add(encrypt(plainText, "AES/CBC/PKCS5Padding", secretKey));
        String encrypted = encrypt(password, plainText);
        String[] encryptedParts = encrypted.split(",");
        strings.add(encryptedParts[2]);
        String key = toBase64(password.getBytes()) + "," + encryptedParts[0] + "," + encryptedParts[1];
//        strings.add(encrypt(Base64.getEncoder().encodeToString(secretKey.getEncoded()), "RSA/ECB/PKCS1Padding", keyUtils.loadPublicKey("RSA")));
        strings.add(encryptAsymmetric(Base64.getEncoder().encodeToString(key.getBytes()), "RSA/ECB/PKCS1Padding", keyUtils.loadPublicKey("RSA")));
//        strings.add(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        return strings;
    }*/

    /**
     * <pre>
     * On the client side, decrypt symmetric key using RSA private key
     * Decrypt the cipher text using decrypted symmetric key
     * Refer README.md
     * </pre>
     */
    /*public String decryptWithRSAWorkflow(List<String> cipherTextStrings) {
        byte[] bytes = Base64.getDecoder().decode(decrypt(cipherTextStrings.get(1), "RSA/ECB/PKCS1Padding", keyUtils.loadPrivateKey("RSA")));
        SecretKeySpec secretKey = new SecretKeySpec(bytes, 0, bytes.length, "AES");
        return decrypt(cipherTextStrings.get(0), "AES", secretKey);
    }*/
}