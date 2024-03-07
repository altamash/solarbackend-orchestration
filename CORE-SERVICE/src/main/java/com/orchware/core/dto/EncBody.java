package com.orchware.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class EncBody {

    private String symmetricAlgo;
    private String asymmetricAlgo;
}
