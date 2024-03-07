package com.orchware.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HybridEncOutput {

    private String symmetricAlgo;
    private String asymmetricAlgo;
    private String asymmetricEncryptedPrivateKey;
    private String symmetricEncriptedText;
    private String privateKeyPEMString;
}
