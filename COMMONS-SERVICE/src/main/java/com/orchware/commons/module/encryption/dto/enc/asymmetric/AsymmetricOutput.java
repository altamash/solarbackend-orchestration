package com.orchware.commons.module.encryption.dto.enc.asymmetric;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AsymmetricOutput {

    private String algo;
    private String keySource;
    private String privateKeyPEMString;
    private String cipherText;
}
