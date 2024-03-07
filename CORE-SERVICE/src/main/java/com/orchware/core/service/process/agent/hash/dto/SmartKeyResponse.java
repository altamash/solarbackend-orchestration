package com.orchware.core.service.process.agent.hash.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmartKeyResponse {

    private String smartKey;
    private String publicKey;
}
