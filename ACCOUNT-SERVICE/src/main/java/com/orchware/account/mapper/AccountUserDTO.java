package com.orchware.account.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountUserDTO {

    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String userType; //PRIMARY, LINKED
    private boolean pVerified;
    private String email;
    private boolean eVerified;
    private String add1;
    private String add2;
    private String add3;
    private Long state;
    private String postalCode;
    private String poBox;
    private String country;
    private Set<String> accountUserType;
    private boolean ownerInd;
    private Long role;
}
