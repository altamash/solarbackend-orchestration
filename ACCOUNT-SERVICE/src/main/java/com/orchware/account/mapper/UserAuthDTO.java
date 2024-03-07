package com.orchware.account.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orchware.account.model.AccountUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthDTO {


    private Long userAuthId;

    @Transient
    private String jwtToken;
    private AccountUser accountUser;
    private String authType; // OAUTH, STANDARD,
    private String username;
    private String password;
    private String authLinkEmail;
    private String passcode; //Hash
    private String secQues1;
    private String sec1Ans;
    private String secQues2;
    private String sec2Ans;
    private Long consecutiveFailedAttemptCount;
    private boolean expiredInd;
    private Long nextExpireDate;
    private String var1;
    private String var2;
    private String var3;
}
