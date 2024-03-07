package com.orchware.commons.module.encryption.dto.enc.symmetric;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SecretKeyInput {

    private String algorithm;
    private String password;
    private String salt;
}
