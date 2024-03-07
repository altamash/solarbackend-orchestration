package com.orchware.core.service.process.agent.registration;

import com.orchware.core.Utility;
import com.orchware.core.dto.EncResponse;
import com.orchware.core.dto.asymmetric.AsymmetricInput;
import com.orchware.core.dto.asymmetric.AsymmetricOutput;
import com.orchware.core.dto.asymmetric.AsymmetricPem;
import com.orchware.core.dto.symmetric.SymmetricInput;
import com.orchware.core.dto.symmetric.SymmetricOutput;
import com.orchware.core.feignInterface.CommonsInterface;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static com.orchware.core.constants.OrchConstants.SMART_KEY_VALIDITY_MINUTES;
import static com.orchware.core.service.process.EKeySource.LOAD_FROM_PEM;
import static com.orchware.core.service.process.EKeySource.LOAD_FROM_STRING;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final CommonsInterface commonsInterface;

    public RegistrationServiceImpl(CommonsInterface commonsInterface) {
        this.commonsInterface = commonsInterface;
    }

    @Override
    public String generateSecretKey(String algorithm) {
        return commonsInterface.generateSecretKey(algorithm);
    }

    @Override
    public AsymmetricPem generatePemStrings(String algorithm) {
        return commonsInterface.generatePemStrings(algorithm);
    }

    @Override
    public String encryptSymmetric(SymmetricInput data) throws InvalidAlgorithmParameterException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        return commonsInterface.encryptSymmetric(data);
    }

    @Override
    public String decryptSymmetric(SymmetricOutput data) throws InvalidAlgorithmParameterException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        return commonsInterface.decryptSymmetric(data);
    }

    @Override
    public String encryptAsymmetric(AsymmetricInput data) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return commonsInterface.encryptAsymmetric(data);
    }

    @Override
    public String decryptAsymmetric(AsymmetricOutput data) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return commonsInterface.decryptAsymmetric(data);
    }

    /**
     * 1 Generate RSA key pair.
     * 2 Random 5 chars auth code.
     * 3 Random 10 chars authentication id
     * 4 14 digit timestamp
     * 5 Concatinate 10 chars and 5 chars and encrypt them with public key
     *
     * @param symmetricAlgo (AES), asymmetricAlgo (RSA)
     * @return Public key, hash value (encrypted [authCode] + [authenticationId]), and 5 chars code
     */
    static String symmetricSecretKey;
    static AsymmetricPem asymmetricPem;
    static String authCode;
    static String authorizationId;
    static long timestampAllowed;

    @Override
    public EncResponse generateEncryptionForAgent(String symmetricAlgo, String asymmetricAlgo)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        authCode = Utility.randomAlphanumeric(5);           // 2
        return generateEncryption(symmetricAlgo, asymmetricAlgo, authCode);
    }

    // authorizationId + agentAuthId + timeStamp
    private EncResponse generateEncryption(String symmetricAlgo, String asymmetricAlgo, String agentAuthId)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        symmetricSecretKey = generateSecretKey(symmetricAlgo);                                 // 1
        asymmetricPem = generatePemStrings(asymmetricAlgo);                                 // 1
        //        agentAuthId = Utility.generateRandomChars(5);                                   // 2
        authorizationId = Utility.randomAlphanumeric(10);                        // 3
        timestampAllowed = System.currentTimeMillis() + SMART_KEY_VALIDITY_MINUTES; // 4  = 2 days
        String concatenatedChars = authorizationId + agentAuthId;
        String aesEncryptedData = encryptSymmetric(SymmetricInput.builder()
                .algo(symmetricAlgo)
                .keySource(LOAD_FROM_STRING.toString())
                .keyString(symmetricSecretKey)
                .text(concatenatedChars)
                .build());
        String rsaEncryptedAESKey = encryptAsymmetric(AsymmetricInput.builder()
                .algo(asymmetricAlgo)
                .keySource(LOAD_FROM_PEM.toString())
                .publicKeyPEMString(asymmetricPem.getPublicKeyPem())
                .text(symmetricSecretKey)
                .build());
        // = encrypted [agentAuthId] + [authenticationId] OR
        // encrypted [agentAuthId] + [authenticationId] with AES, and AES key with RSA
        // return RSA encrypted (AES key) + AES (encrypted [agentAuthId] + [authenticationId])
        return EncResponse.builder()
                //                .publicKey(asymmetricPem.getPublicKeyPem())
                .privateKey(asymmetricPem.getPrivateKeyPem())
                .hashValues(Arrays.asList(rsaEncryptedAESKey, aesEncryptedData))
                .charCode(agentAuthId)
                .build();
    }

    /**
     * In case of rotation same as before except auth code remains same
     *
     * @param symmetricAlgo (AES), asymmetricAlgo (RSA)
     * @return
     */
    @Override
    public EncResponse generateEncryptionForAgentRotation(String symmetricAlgo, String asymmetricAlgo)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return generateEncryption(symmetricAlgo, asymmetricAlgo, authCode);
    }

    /**
     * cipherText = encrypted [originalHash sent] + [authCode sent] + [timestamp from agent]
     *
     * @param symmetricAlgo (AES), asymmetricAlgo (RSA);
     *                      hashValues; hashValues(0) = rsaEncryptedAESKey, hashValues(1) = aesEncryptedData
     * @return
     */
    @Override
    public boolean validateAgent(String symmetricAlgo, String asymmetricAlgo, List<String> hashValues)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        String aesKey = decryptAsymmetric(AsymmetricOutput.builder()
                .algo(asymmetricAlgo)
                .keySource(LOAD_FROM_PEM.toString())
                .privateKeyPEMString(asymmetricPem.getPrivateKeyPem())
                .cipherText(hashValues.get(0))
                .build());
        String decryptedText = decryptSymmetric(SymmetricOutput.builder()
                .algo(symmetricAlgo)
                .keySource(LOAD_FROM_STRING.toString())
                .keyString(aesKey)
                .cipherText(hashValues.get(1))
                .build());
        String authCodeFromAgent = decryptedText.substring(0, 5);
        long receivedTimestamp;
        try {
            receivedTimestamp = Long.parseLong(decryptedText.substring(5, 18));
        } catch (Exception e) {
            return false;
        }
        String originalHash = decryptedText.substring(19);
        String authCodeFromHash = originalHash.substring(0, 5);
        String authenticationIdFromHash = originalHash.substring(5, 15);
        return authCode.equals(authCodeFromHash) &&
                authorizationId.equals(authenticationIdFromHash) &&
                authCode.equals(authCodeFromAgent) &&
                receivedTimestamp <= timestampAllowed;
    }
}
