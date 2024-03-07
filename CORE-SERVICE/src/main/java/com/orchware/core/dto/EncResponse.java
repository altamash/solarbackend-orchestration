package com.orchware.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EncResponse {

//    Public key, hash value and 5 chars code
    private String publicKey;
    private String privateKey;
    private List<String> hashValues;
    private String charCode;
}
