package com.orchware.commons.module.encryption.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.security.PublicKey;

@Getter
@Setter
@Builder
public class HybridEncInput {

    private String symmetricAlgo;
    private String asymmetricAlgo;
    private String publicKeyPEMString;
    private String keyString;
    private String plainText;
}
