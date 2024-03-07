package com.orchware.login.responseDTO.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {

    private Long acctId;
    private String acctName;
    private String acctType;
    private String businessName;
    private String status;
}
