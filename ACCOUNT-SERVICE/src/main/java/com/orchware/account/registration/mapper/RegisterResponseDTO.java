package com.orchware.account.registration.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orchware.account.mapper.AccountDTO;
import com.orchware.account.mapper.AccountUserDTO;
import com.orchware.account.mapper.UserAuthDTO;
import lombok.*;

@Getter
@Setter
//@AllArgsConstructor
@Builder
//@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponseDTO {

    private AccountDTO accountDTO;
    private AccountUserDTO accountUserDTO;

    private UserAuthDTO userAuthDTO;
}
