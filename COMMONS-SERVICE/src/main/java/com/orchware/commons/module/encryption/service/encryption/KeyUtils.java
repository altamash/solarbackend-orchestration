package com.orchware.commons.module.encryption.service.encryption;

import com.orchware.commons.module.encryption.model.SystemAttribute;
import com.orchware.commons.module.encryption.service.attribute.ESystemAttribute;
import com.orchware.commons.module.encryption.service.attribute.SystemAttributeService;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class KeyUtils {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private SystemAttributeService systemAttributeService;

    // Generate Keys /////////////////////////////////////////////
    public SecretKey generateSymmetricKey(String algorithm, int keySize) {
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Exception caught while generating secret key: ", e);
        }
        generator.init(keySize);
        return generator.generateKey();
    }

    public SecretKey generateSymmetricKeyFromPassword(String algorithm, String password, String salt, int keySize) {
        SecretKey secret = null;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, keySize);
            secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), algorithm);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error("Exception caught while generating secret key: ", e);
        }
        return secret;
    }

    public static KeyPair generateAsymmetricKeys(String algorithm, int keySize) {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(keySize, random);
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    // Load Keys /////////////////////////////////////////////
    public SecretKey loadSecretKey(String algorithm) {
        SystemAttribute secretKeyAttribute =
                systemAttributeService.findByAttributeKey(ESystemAttribute.SECRET_KEY.getAttributeKey());
        if (secretKeyAttribute != null) {
            byte[] bytes = secretKeyAttribute.getAttributeValueLob();
            return new SecretKeySpec(bytes, 0, bytes.length, algorithm);
        }
        return null;
    }

    public SecretKey loadSecretKeyFromString(String algorithm, String keyString) {
        if (keyString != null) {
            byte[] bytes = Base64.getDecoder().decode(keyString);
            return new SecretKeySpec(bytes, 0, bytes.length, algorithm);
        }
        return null;
    }

    public PublicKey loadPublicKey(String algorithm) {
        SystemAttribute publicKeyAttribute =
                systemAttributeService.findByAttributeKey(ESystemAttribute.PUBLIC_KEY.getAttributeKey());
        if (publicKeyAttribute != null) {
            return loadPublicKey(algorithm, publicKeyAttribute.getAttributeValueLob());
        }
        return null;
    }

    public PublicKey loadPublicKeyFromFile(String algorithm, File file) {
        byte[] bytes = new byte[0];
        try (FileReader reader = new FileReader(file);
             PemReader pemReader = new PemReader(reader)) {
            PemObject pemObject = pemReader.readPemObject();
            bytes = pemObject.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadPublicKey(algorithm, bytes);
    }

    public PublicKey loadPublicKeyFromPemString(String algorithm, String pemString) {
        byte[] bytes = new byte[0];
        try (PemReader pemReader = new PemReader(new StringReader(pemString))) {
            PemObject pemObject = pemReader.readPemObject();
            bytes = pemObject.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadPublicKey(algorithm, bytes);
    }

    private PublicKey loadPublicKey(String algorithm, byte[] bytes) {
        PublicKey publicKey = null;
        if (bytes != null) {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
                publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return publicKey;
    }

    public PrivateKey loadPrivateKey(String algorithm) {
        SystemAttribute privateKeyAttribute =
                systemAttributeService.findByAttributeKey(ESystemAttribute.PRIVATE_KEY.getAttributeKey());
        if (privateKeyAttribute != null) {
            return loadPrivateKey(algorithm, privateKeyAttribute.getAttributeValueLob());
        }
        return null;
    }

    public PrivateKey loadPrivaKeyFromPemString(String algorithm, String pemString) {
        byte[] bytes = new byte[0];
        try (PemReader pemReader = new PemReader(new StringReader(pemString))) {
            PemObject pemObject = pemReader.readPemObject();
            bytes = pemObject.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadPrivateKey(algorithm, bytes);
    }

    private PrivateKey loadPrivateKey(String algorithm, byte[] bytes) {
        PrivateKey privateKey = null;
        if (bytes != null) {
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytes);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
                privateKey = keyFactory.generatePrivate(privateKeySpec);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return privateKey;
    }

    public static void main(String[] args) {
        System.out.println("sample".getBytes());
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
//        PemObject pemObject = new PemObject("RSA PRIVATE KEY", keyPair.getPrivate().getEncoded());
        System.out.println(getPemString("RSA", keyPair.getPrivate()));
        System.out.println(getPemString("RSA", keyPair.getPublic()));
    }

    public static String getPemString(String algorithm, Key key) {
        PemObject pemObject = null;
        if (key instanceof PrivateKey) {
            pemObject = new PemObject(algorithm + " PRIVATE KEY", key.getEncoded());
        } else if (key instanceof PublicKey) {
            pemObject = new PemObject(algorithm + " PUBLIC KEY", key.getEncoded());
        }
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             PemWriter pemWriter = new PemWriter(new OutputStreamWriter(byteStream))) {
            pemWriter.writeObject(pemObject);
            pemWriter.close();
            return new String(byteStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
