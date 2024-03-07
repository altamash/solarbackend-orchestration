package com.orchware.core.dto.asymmetric;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AsymmetricPem {

    private String privateKeyPem;
    private String publicKeyPem;
}
