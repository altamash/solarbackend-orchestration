package com.orchware.core.service.process.pool.registration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerBrandDTO {

    private Long brandId;
    private String brandName;
    private String brandCategory;
    private Long parentBrandId;
    private String manufacturerName;
    private String countryOfOrigin;
    private String countryOfManufacturing;
    private String defaultAgentCommsStrategy;
    private String prefix;
    private List<DeviceBrVarDefDTO> deviceBrVarDefs;
}
