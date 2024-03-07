package com.orchware.core.service.process.pool.registration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orchware.core.dto.HybridEncOutput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceRegistration {

    private String smartKey;
    private RegisteredDeviceDTO deviceData;
}
