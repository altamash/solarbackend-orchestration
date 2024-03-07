package com.orchware.commons.module.encryption.service.encryption;

import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class PEMUtils {

    /*@Autowired
    private KeyUtils keyUtils;*/

    /*public String getPEMString(Key key) {
        StringWriter sw = new StringWriter();
        try (JcaPEMWriter pw = new JcaPEMWriter(sw)) {
            pw.writeObject(key);
        } catch (IOException e) {
        }
        return sw.toString();
    }*/

    /*public String getPEMString(String algorithm, Class<? extends Key> keyClass) {
        if (SecretKey.class.isAssignableFrom(keyClass)) {
            return getPEMString(keyUtils.loadSecretKey(algorithm));
        } else if (PublicKey.class.isAssignableFrom(keyClass)) {
            return getPEMString(keyUtils.loadPublicKey(algorithm));
        } else if (PrivateKey.class.isAssignableFrom(keyClass)) {
            return getPEMString(keyUtils.loadPrivateKey(algorithm));
        } else {
            throw new UnsupportedOperationException(keyClass + " is not supported");
        }
    }*/

    public String getPemString(String algorithm, Key key) {
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

    public Key getKeyFromPem(byte[] pemBytes, Class<? extends Key> keyClass, String algorithm) {
        try (PemReader pemReader = new PemReader(new InputStreamReader(new ByteArrayInputStream(pemBytes)))) {
            byte[] bytes = pemReader.readPemObject().getContent();
            if (SecretKey.class.isAssignableFrom(keyClass)) {
                return new SecretKeySpec(bytes, 0, bytes.length, algorithm);
            } else if (PublicKey.class.isAssignableFrom(keyClass)) {
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
                KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
                return keyFactory.generatePublic(x509EncodedKeySpec);
            } else if (PrivateKey.class.isAssignableFrom(keyClass)) {
                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
                return KeyFactory.getInstance(algorithm).generatePrivate(spec);
            } else {
                throw new UnsupportedOperationException(keyClass + " is not supported");
            }
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
