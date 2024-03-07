package com.orchware.login.responseDTO.register;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orchware.login.responseDTO.account.AccountDTO;
import com.orchware.login.responseDTO.account.AccountUserDTO;
import com.orchware.login.responseDTO.account.UserAuthDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponseDTO {

    private AccountDTO accountDTO;
    private AccountUserDTO accountUserDTO;

    private UserAuthDTO userAuthDTO;
}
