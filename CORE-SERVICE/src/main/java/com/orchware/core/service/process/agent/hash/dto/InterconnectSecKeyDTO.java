package com.orchware.core.service.process.agent.hash.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InterconnectSecKeyDTO {

    private Long secKeyId;
    private Long accountId;
    private String corssourceId;
    private String keyhash;
    private String uniqueKey;
    private Boolean activeInd;
    private Date startDate;
    private Date expiryDate;
    private Integer maxCallsPerDay;
    private String corsrefTag;
    private Boolean refreshcorsInd;
    private Boolean lockedInd;
    private Integer failedAttempts;
    private String symmetricAlgo;
    private String asymmetricAlgo;
    private String asymmetricPublicKey;
    private String asymmetricPrivateKey;
    private String symmetricSecretKey;
    private long timeStamp;
}
