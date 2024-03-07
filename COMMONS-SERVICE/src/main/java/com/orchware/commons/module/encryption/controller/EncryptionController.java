package com.orchware.commons.module.encryption.controller;

import com.microsoft.azure.storage.StorageException;
import com.orchware.commons.module.encryption.dto.HybridEncInput;
import com.orchware.commons.module.encryption.dto.HybridEncOutput;
import com.orchware.commons.module.encryption.dto.enc.asymmetric.AsymmetricInput;
import com.orchware.commons.module.encryption.dto.enc.asymmetric.AsymmetricOutput;
import com.orchware.commons.module.encryption.dto.enc.asymmetric.AsymmetricPem;
import com.orchware.commons.module.encryption.dto.enc.symmetric.SecretKeyInput;
import com.orchware.commons.module.encryption.dto.enc.symmetric.SymmetricInput;
import com.orchware.commons.module.encryption.dto.enc.symmetric.SymmetricOutput;
import com.orchware.commons.module.encryption.service.encryption.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@CrossOrigin
@RestController("EncryptionController")
@RequestMapping(value = "/enc")
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("/getPEMString")
    public String getPEMString(@RequestParam String algorithm, @RequestParam Key keyClass) {
        return encryptionService.getPemString(algorithm, keyClass);
    }

    @GetMapping("/initKeys")
    public void initKeys() throws URISyntaxException, StorageException {
        encryptionService.init();
    }

    // Asymmetric Encryption ////////////////////////////////////////////
    @PostMapping("/symmetric/generateSecretKey")
    public String generateSecretKey(@RequestBody String algorithm) {
        return encryptionService.generateSecretKey(algorithm);
    }

    @PostMapping("/symmetric/generateSecretKey/algo/{algo}/key/{key}")
    public String generateSecretKeyAndSave(@PathVariable String algo, @PathVariable String key) {
        return encryptionService.generateSecretKeyAndSave(algo, key);
    }

    @PostMapping("/symmetric/generateSecretKeyFromPassword")
    public String generateSecretKeyFromPassword(@RequestBody SecretKeyInput secretKeyInput) {
        return encryptionService.generateSecretKeyFromPassword(secretKeyInput.getAlgorithm(),
                secretKeyInput.getPassword(), secretKeyInput.getSalt());
    }

    @PostMapping("/symmetric/encrypt")
    public String encryptSymmetric(@RequestBody SymmetricInput data) throws InvalidAlgorithmParameterException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return encryptionService.encryptSymmetric(data);
    }

    @PostMapping("/symmetric/decrypt")
    public String decryptSymmetric(@RequestBody SymmetricOutput data) throws InvalidAlgorithmParameterException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return encryptionService.decryptSymmetric(data);
    }

    // Symmetric Encryption ////////////////////////////////////////////
    @PostMapping("/asymmetric/generatePemStrings")
    public AsymmetricPem generatePemStrings(@RequestBody String algorithm) {
        return encryptionService.generatePemStrings(algorithm);
    }

    @PostMapping("/asymmetric/encrypt")
    public String encryptAsymmetric(@RequestBody AsymmetricInput data) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return encryptionService.encryptAsymmetric(data);
    }

    @PostMapping("/asymmetric/decrypt")
    public String decryptAsymmetric(@RequestBody AsymmetricOutput data) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return encryptionService.decryptAsymmetric(data);
    }

    // AES/RSA Workflow ////////////////////////////////////////////
    @PostMapping("/encryptHybrid")
    public HybridEncOutput encryptWithRSAWorkflow(@RequestBody HybridEncInput hybridEncInput)
            throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return encryptionService.encryptWithRSAWorkflow(hybridEncInput);
    }

    @PostMapping("/decryptHybrid")
    public String decryptWithRSAWorkflow(@RequestBody HybridEncOutput hybridEncOutput)
            throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return encryptionService.decryptWithRSAWorkflow(hybridEncOutput);
    }
}
