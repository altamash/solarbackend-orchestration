package com.orchware.core.dto.symmetric;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SymmetricInput {

    private String algo;
    private String keySource;
    private String keyString;
    private String text;
}
