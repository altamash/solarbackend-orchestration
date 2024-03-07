package com.orchware.core.feignInterface;

import com.orchware.core.dto.HybridEncInput;
import com.orchware.core.dto.HybridEncOutput;
import com.orchware.core.dto.asymmetric.AsymmetricInput;
import com.orchware.core.dto.asymmetric.AsymmetricOutput;
import com.orchware.core.dto.asymmetric.AsymmetricPem;
import com.orchware.core.dto.symmetric.SymmetricInput;
import com.orchware.core.dto.symmetric.SymmetricOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.orchware.core.constants.MicroserviceConstants.CommonsService.*;

@FeignClient(name = BASE, path = ENCRYPTION_BASE_API)
public interface CommonsInterface {

    @PostMapping(value = GENERATE_SECRETKEY)
    String generateSecretKey(@RequestBody String algorithm);

    @PostMapping(value = GENERATE_PEM_STRINGS)
    AsymmetricPem generatePemStrings(@RequestBody String algorithm);

    @PostMapping(value = ENCRYPT_SYMMETRIC)
    String encryptSymmetric(@RequestBody SymmetricInput data) throws InvalidAlgorithmParameterException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException;

    @PostMapping(value = DECRYPT_SYMMETRIC)
    String decryptSymmetric(@RequestBody SymmetricOutput data) throws InvalidAlgorithmParameterException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException;

    @PostMapping(value = ENCRYPT_ASYMMETRIC)
    String encryptAsymmetric(@RequestBody AsymmetricInput data) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    @PostMapping(value = DECRYPT_ASYMMETRIC)
    String decryptAsymmetric(@RequestBody AsymmetricOutput data) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    @PostMapping(value = ENCRYPT_HYBRID)
    HybridEncOutput encryptWithRSAWorkflow(@RequestBody HybridEncInput hybridEncInput)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    @PostMapping(value = DECRYPT_HYBRID)
    String decryptWithRSAWorkflow(@RequestBody HybridEncOutput hybridEncOutput)
            throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
}
