package com.orchware.commons.module.encryption.service.encryption;

import com.orchware.commons.module.encryption.dto.HybridEncInput;
import com.orchware.commons.module.encryption.dto.HybridEncOutput;
import com.orchware.commons.module.encryption.dto.enc.EKeySource;
import com.orchware.commons.module.encryption.dto.enc.asymmetric.AsymmetricInput;
import com.orchware.commons.module.encryption.dto.enc.asymmetric.AsymmetricOutput;
import com.orchware.commons.module.encryption.dto.enc.asymmetric.AsymmetricPem;
import com.orchware.commons.module.encryption.dto.enc.symmetric.SymmetricInput;
import com.orchware.commons.module.encryption.dto.enc.symmetric.SymmetricOutput;
import com.orchware.commons.module.encryption.model.SystemAttribute;
import com.orchware.commons.module.encryption.repository.SystemAttributeRepository;
import com.orchware.commons.module.storage.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.*;
import java.util.Base64;
import java.util.Optional;

@Service
public class EncryptionService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
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

    @Autowired
    private StorageService storageService;
    @Autowired
    private KeyUtils keyUtils;
    @Autowired
    private PEMUtils pemUtils;
    @Autowired
    private EncryptionUtils encryptionUtils;
    @Autowired
    private SystemAttributeRepository systemAttributeRepository;

    public void init() {
        encryptionUtils.init();
    }

    public String getPemString(String algorithm, Key keyClass) {
        return pemUtils.getPemString(algorithm, keyClass);
    }

    // Symmetric Encryption ////////////////////////////////////////////////////
    public String generateSecretKey(String algorithm) {
        return encryptionUtils.generateSecretKey(algorithm);
    }

    public String generateSecretKeyAndSave(String algorithm, String keyName) {
        String secretKey = encryptionUtils.generateSecretKey(algorithm);
        Optional<SystemAttribute> attributeKeyOptional = systemAttributeRepository.findByAttributeKey(keyName);
        if (attributeKeyOptional.isPresent()) {
            SystemAttribute attributeKey = attributeKeyOptional.get();
            attributeKey.setAttributeValueLob(secretKey.getBytes());
            systemAttributeRepository.save(attributeKey);
        } else {
            systemAttributeRepository.save(SystemAttribute.builder()
                    .attributeKey(keyName)
                    .attributeValueLob(secretKey.getBytes())
                    .build());
        }
        return secretKey;
    }

    public String generateSecretKeyFromPassword(String algorithm, String password, String salt) {
        return encryptionUtils.generateSecretKeyFromPassword(algorithm, password, salt);
    }

    public String encryptSymmetric(SymmetricInput data) throws InvalidAlgorithmParameterException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        SecretKey key = getPrivateKey(data.getKeyString(), data.getKeySource(), data.getAlgo());
        EAlgo eAlgo = EAlgo.get(data.getAlgo());
        return encryptionUtils.encryptSymmetric(eAlgo.getAlgo(), data.getText(), key,
                eAlgo.getHasIv() != null && eAlgo.getHasIv() ? encryptionUtils.generateIVBytes() : null);
    }

    public String decryptSymmetric(SymmetricOutput data) throws InvalidAlgorithmParameterException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        SecretKey key = getPrivateKey(data.getKeyString(), data.getKeySource(), data.getAlgo());
        EAlgo eAlgo = EAlgo.get(data.getAlgo());
        if (eAlgo.getHasIv()) {
            String cipherParts[] = data.getCipherText().split(Base64.getEncoder().encodeToString(".".getBytes()));
            return encryptionUtils.decryptSymmetric(eAlgo.getAlgo(), cipherParts[0], key, Base64.getDecoder().decode(cipherParts[1]));
        } else {
            return encryptionUtils.decryptSymmetric(eAlgo.getAlgo(), data.getCipherText(), key, null);
        }
    }

    private SecretKey getPrivateKey(String keyString, String keySource, String algo) {
        SecretKey key = null;
        if (keyString != null && EKeySource.LOAD_FROM_STRING.toString().equals(keySource)) {
            key = keyUtils.loadSecretKeyFromString(algo, keyString);
        } else if (EKeySource.LOAD_FROM_DB.toString().equals(keySource)) {
            key = keyUtils.loadSecretKey(algo);
        }
        return key;
    }

    // Asymmetric Encryption ////////////////////////////////////////////////////
    public AsymmetricPem generatePemStrings(String algorithm) {
        return encryptionUtils.generatePemStrings(algorithm);
    }

    public String encryptAsymmetric(AsymmetricInput data) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PublicKey publicKey = null;
        if (EKeySource.LOAD_FROM_PEM.toString().equals(data.getKeySource())) {
            publicKey = keyUtils.loadPublicKeyFromPemString(data.getAlgo(), data.getPublicKeyPEMString());
        } else if (EKeySource.LOAD_FROM_DB.toString().equals(data.getKeySource())) {
            publicKey = keyUtils.loadPublicKey(data.getAlgo());
        }
        return encryptionUtils.encryptAsymmetric(data.getText(), EAlgo.get(data.getAlgo()).getAlgo(), publicKey);
    }

    public String decryptAsymmetric(AsymmetricOutput data) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        PrivateKey privateKey = null;
        if (EKeySource.LOAD_FROM_PEM.toString().equals(data.getKeySource())) {
            privateKey = keyUtils.loadPrivaKeyFromPemString(data.getAlgo(), data.getPrivateKeyPEMString());
        } else if (EKeySource.LOAD_FROM_DB.toString().equals(data.getKeySource())) {
            privateKey = keyUtils.loadPrivateKey(data.getAlgo());
        }
        return encryptionUtils.decryptAsymmetric(data.getCipherText(), EAlgo.get(data.getAlgo()).getAlgo(), privateKey);
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

    /**
     * <pre>
     * Encrypt plain text using AES
     * Encrypt the AES key using RSA public key
     * Refer README.md
     * </pre>
     */
    public HybridEncOutput encryptWithRSAWorkflow(HybridEncInput hybridEncInput)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        /* Workflow
           1. Generate a symmetric key
           2. Encrypt the data with the symmetric key AES
           3. Encrypt the symmetric key with RSA
           4. Send the encrypted key and the data
           5. Decrypt the encrypted symmetric key with RSA (on client)
           6. Decrypt the data with the symmetric key AES (on client) */
        String keyString = hybridEncInput.getKeyString();
        return HybridEncOutput.builder() // 4
                .symmetricEncriptedText(encryptSymmetric(SymmetricInput.builder() // 2
                                .algo(hybridEncInput.getSymmetricAlgo())
                                .keySource(EKeySource.LOAD_FROM_STRING.toString())
                                .text(hybridEncInput.getPlainText())
                                .keyString(keyString)
                        .build()))
                .asymmetricEncryptedPrivateKey(encryptAsymmetric(AsymmetricInput.builder() // 3
                                .algo(hybridEncInput.getAsymmetricAlgo())
                                .keySource(EKeySource.LOAD_FROM_PEM.toString())
                                .text(keyString)
                                .publicKeyPEMString(hybridEncInput.getPublicKeyPEMString())
                        .build()))
                .build();
    }

    public String decryptWithRSAWorkflow(HybridEncOutput hybridEncOutput) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException {
        /* 1. Decrypt the encrypted symmetric key with RSA
           2. Decrypt the data with the symmetric key AES */
        String secretKey = decryptAsymmetric(AsymmetricOutput.builder() // 1
                    .algo(hybridEncOutput.getAsymmetricAlgo())
                    .keySource(EKeySource.LOAD_FROM_PEM.toString())
                    .cipherText(hybridEncOutput.getAsymmetricEncryptedPrivateKey())
                    .privateKeyPEMString(hybridEncOutput.getPrivateKeyPEMString())
                .build());
        return decryptSymmetric(SymmetricOutput.builder() // 2
                    .algo(hybridEncOutput.getSymmetricAlgo())
                    .keySource(EKeySource.LOAD_FROM_STRING.toString())
                    .keyString(secretKey)
                    .cipherText(hybridEncOutput.getSymmetricEncriptedText())
                .build());
    }
}
