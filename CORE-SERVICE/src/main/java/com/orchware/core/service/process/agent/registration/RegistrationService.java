package com.orchware.core.service.process.agent.registration;

import com.orchware.core.dto.EncResponse;
import com.orchware.core.dto.asymmetric.AsymmetricInput;
import com.orchware.core.dto.asymmetric.AsymmetricOutput;
import com.orchware.core.dto.asymmetric.AsymmetricPem;
import com.orchware.core.dto.symmetric.SymmetricInput;
import com.orchware.core.dto.symmetric.SymmetricOutput;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface RegistrationService {

    String generateSecretKey(String algorithm);

    AsymmetricPem generatePemStrings(String algorithm);

    String encryptSymmetric(SymmetricInput data) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    String decryptSymmetric(SymmetricOutput data) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    String encryptAsymmetric(AsymmetricInput data) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    String decryptAsymmetric(AsymmetricOutput data) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    EncResponse generateEncryptionForAgent(String symmetricAlgo, String asymmetricAlgo) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    EncResponse generateEncryptionForAgentRotation(String symmetricAlgo, String asymmetricAlgo) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    boolean validateAgent(String symmetricAlgo, String asymmetricAlgo, List<String> hashValues) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException;
}
