package com.orchware.commons.module.encryption.service.encryption;

import com.orchware.commons.exception.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EAlgo {

    AES("AES", "CBC", "PKCS5Padding", true),
    RSA("RSA", "ECB", "PKCS1Padding", null);

    private String name;
    private String mode;
    private String padding;
    private Boolean hasIv;

    EAlgo(String name, String mode, String padding, Boolean hasIv) {
        this.name = name;
        this.mode = mode;
        this.padding = padding;
        this.hasIv = hasIv;
    }

    public static EAlgo get(String name) {
        return Arrays.stream(values()).filter(value -> name.equalsIgnoreCase(value.name)).findFirst()
                .orElseThrow(() -> new NotFoundException(EAlgo.class, "name", name));
    }

    String getAlgo() {
        return this.name + "/" + this.mode + "/" + this.padding;
    }
}
