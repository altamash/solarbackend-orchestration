package com.orchware.core.controller.process.agent;

import com.orchware.core.dto.EncBody;
import com.orchware.core.dto.EncResponse;
import com.orchware.core.dto.EncValicationBody;
import com.orchware.core.dto.asymmetric.AsymmetricInput;
import com.orchware.core.dto.asymmetric.AsymmetricOutput;
import com.orchware.core.dto.asymmetric.AsymmetricPem;
import com.orchware.core.service.process.agent.registration.RegistrationService;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/agent")
public class ProcessAgentRegistrationController {

    private final RegistrationService registrationService;

    public ProcessAgentRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/test/asymmetric/generatePemStrings")
    public AsymmetricPem generatePemStrings(@RequestBody String algorithm) {
        return registrationService.generatePemStrings(algorithm);
    }

    @PostMapping("/asymmetric/encrypt")
    public String encryptAsymmetric(@RequestBody AsymmetricInput data) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return registrationService.encryptAsymmetric(data);
    }

    @PostMapping("/asymmetric/decrypt")
    public String decryptAsymmetric(@RequestBody AsymmetricOutput data) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return registrationService.decryptAsymmetric(data);
    }

    @PostMapping("/asymmetric/generateEncryption")
    public EncResponse generateEncryptionForAgent(@RequestBody EncBody encBody)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return registrationService.generateEncryptionForAgent(encBody.getSymmetricAlgo(), encBody.getAsymmetricAlgo());
    }
    @PostMapping("/asymmetric/generateEncryptionRotation")
    public EncResponse generateEncryptionForAgentRotation(@RequestBody EncBody encBody)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return registrationService.generateEncryptionForAgentRotation(encBody.getSymmetricAlgo(),
                encBody.getAsymmetricAlgo());
    }

    @PostMapping("/validate")
    public boolean decryptAsymmetric(@RequestBody EncValicationBody encBody) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        return registrationService.validateAgent(encBody.getSymmetricAlgo(), encBody.getAsymmetricAlgo(),
                encBody.getHashValues());
    }
}
