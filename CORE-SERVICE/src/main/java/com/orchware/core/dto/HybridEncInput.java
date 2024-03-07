package com.orchware.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
