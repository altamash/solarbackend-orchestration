package com.orchware.core.service.process.agent.hash;

import com.orchware.core.Utility;
import com.orchware.core.dto.asymmetric.AsymmetricOutput;
import com.orchware.core.dto.asymmetric.AsymmetricPem;
import com.orchware.core.feignInterface.CommonsInterface;
import com.orchware.core.model.InterconnectSecKey;
import com.orchware.core.repository.InterconnectSecKeyRepository;
import com.orchware.core.service.process.agent.hash.dto.SmartKeyResponse;
import com.orchware.core.service.process.agent.hash.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import static com.orchware.core.constants.OrchConstants.SMART_KEY_VALIDITY_MINUTES;
import static com.orchware.core.service.process.EKeySource.LOAD_FROM_PEM;

@Service
public class KeyGenerationServiceImpl implements KeyGenerationService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String ASYMMETRIC_ENCRYPTED_HASH_ALGO = "RSA";
    private final JwtTokenUtil jwtTokenUtil;
    private final InterconnectSecKeyRepository interconnectSecKeyRepository;
    private final CommonsInterface commonsInterface;

    public KeyGenerationServiceImpl(JwtTokenUtil jwtTokenUtil,
                                    InterconnectSecKeyRepository interconnectSecKeyRepository,
                                    CommonsInterface commonsInterface) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.interconnectSecKeyRepository = interconnectSecKeyRepository;
        this.commonsInterface = commonsInterface;
    }

    /**
     * Once Generate is pressed the system concatenates ID with the timestamp in number as.
     * <AgentAuthID><5 char auth code>
     * without any spaces or other separators and both hash and Agent ID are saved.
     * @param symmetricAlgo
     * @param asymmetricAlgo
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    /*@Override
    public HybridEncOutput getSmartKey(String symmetricAlgo, String asymmetricAlgo)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        InterconnectSecKey interconnectSecKey = generateAgentAuthorizationId(symmetricAlgo, asymmetricAlgo);
        String uniqueKey = interconnectSecKey.getUniqueKey();
        String keyhash = interconnectSecKey.getKeyhash();
        long timeStamp = interconnectSecKey.getTimeStamp();
        String smartKey = uniqueKey + keyhash + timeStamp;
        HybridEncOutput hybridEncOutput = getEncryptedSmartKey(interconnectSecKey, smartKey);
        hybridEncOutput.setSymmetricAlgo(symmetricAlgo);
        hybridEncOutput.setAsymmetricAlgo(asymmetricAlgo);
        hybridEncOutput.setPrivateKeyPEMString(interconnectSecKey.getAsymmetricPrivateKey());
        return hybridEncOutput;
    }*/
    @Override
    public SmartKeyResponse getSmartKey(String symmetricAlgo, String asymmetricAlgo) {
        InterconnectSecKey interconnectSecKey = generateAgentAuthorizationId(symmetricAlgo, asymmetricAlgo);
        String uniqueKey = interconnectSecKey.getUniqueKey();
        String keyhash = interconnectSecKey.getKeyhash();
        long timeStamp = interconnectSecKey.getTimeStamp();
        String smartKey = uniqueKey + keyhash + timeStamp;
        return SmartKeyResponse.builder()
                .smartKey(smartKey)
                .publicKey(interconnectSecKey.getAsymmetricPublicKey())
                .build();
    }

    /* The system generates a random 10 digit unique alphanumeric Agent Authorization ID, displays it on screen If this
     is rotated, a new random ID is shown and updated to the database along with a timestamp. */
    private InterconnectSecKey generateAgentAuthorizationId(String symmetricAlgo, String asymmetricAlgo) {
        String authorizationId = Utility.randomAlphanumeric(10);
        while (interconnectSecKeyRepository.findByUniqueKey(authorizationId).isPresent()) {
            authorizationId = Utility.randomAlphanumeric(10);
        }
        Date startDate = new Date();
        AsymmetricPem asymmetricPem = commonsInterface.generatePemStrings(asymmetricAlgo);
        String symmetricSecretKey = commonsInterface.generateSecretKey(symmetricAlgo);
        return interconnectSecKeyRepository.save(InterconnectSecKey.builder()
                .uniqueKey(authorizationId)
                .keyhash(Utility.randomAlphanumeric(5))
                .activeInd(true)
                .lockedInd(false)
                .failedAttempts(0)
                .symmetricAlgo(symmetricAlgo)
                .asymmetricAlgo(asymmetricAlgo)
                .asymmetricPublicKey(asymmetricPem.getPublicKeyPem())
                .asymmetricPrivateKey(asymmetricPem.getPrivateKeyPem())
                .symmetricSecretKey(symmetricSecretKey)
                .timeStamp(System.currentTimeMillis())
                .startDate(startDate)
                .expiryDate(Utility.addMinutes(startDate, SMART_KEY_VALIDITY_MINUTES))
                .build());
    }

    /*private HybridEncOutput getEncryptedSmartKey(InterconnectSecKey interconnectSecKey, String smartKey)
            throws InvalidAlgorithmParameterException,IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return commonsInterface.encryptWithRSAWorkflow(HybridEncInput.builder()
                .symmetricAlgo(interconnectSecKey.getSymmetricAlgo())
                .asymmetricAlgo(interconnectSecKey.getAsymmetricAlgo())
                .publicKeyPEMString(interconnectSecKey.getAsymmetricPublicKey())
                .keyString(interconnectSecKey.getSymmetricSecretKey())
                .plainText(smartKey)
                .build());
    }*/

    @Override
    public String getToken(String hash) {
        InterconnectSecKey interconnectSecKey = getInterconnectSecKey(hash, ASYMMETRIC_ENCRYPTED_HASH_ALGO);
        return  jwtTokenUtil.generateToken(interconnectSecKey);
    }

    private InterconnectSecKey getInterconnectSecKey(String hash, String asymmetricAlgo) {
        try {
            String uniqueKey = hash.substring(0, 10);
            String keyhash = hash.substring(10, 15);
            long creationTimeStamp = Long.parseLong(hash.substring(15, 28));
            String encryptedReceivedTimestamp = hash.substring(28);
            Optional<InterconnectSecKey> keyOptional = interconnectSecKeyRepository
                    .findByUniqueKeyAndKeyhashAndTimeStamp(uniqueKey, keyhash, creationTimeStamp);
            if (validateSmartKey(keyOptional, encryptedReceivedTimestamp, asymmetricAlgo)) {
                return keyOptional.get();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        return null;
    }

    private boolean validateSmartKey(Optional<InterconnectSecKey> keyOptional, String encryptedReceivedTimestamp,
                                     String asymmetricAlgo) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (keyOptional.isPresent()) {
            InterconnectSecKey key = keyOptional.get();

            String dencryptedReceivedTimestamp = commonsInterface.decryptAsymmetric(AsymmetricOutput.builder()
                    .algo(asymmetricAlgo)
                    .keySource(LOAD_FROM_PEM.toString())
                    .privateKeyPEMString(key.getAsymmetricPrivateKey())
                    .cipherText(encryptedReceivedTimestamp)
                    .build());
            long receivedTimestamp = Long.parseLong(dencryptedReceivedTimestamp);

            Date date = new Date(receivedTimestamp);
            return key.getActiveInd() && key.getStartDate().before(date) && key.getExpiryDate().after(date);
        }
        return false;
    }
}
