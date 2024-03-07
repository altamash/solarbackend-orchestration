package com.orchware.account.registration.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orchware.account.model.accountType.AccountUserType;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequestDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private Set<AccountUserType> accountUserType;


}
