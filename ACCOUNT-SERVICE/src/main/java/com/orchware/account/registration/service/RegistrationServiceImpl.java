package com.orchware.account.registration.service;

import com.orchware.account.constants.RegistrationConstants;
import com.orchware.account.exception.AlreadyExistsException;
import com.orchware.account.model.Account;
import com.orchware.account.model.AccountUser;
import com.orchware.account.model.UserAuth;
import com.orchware.account.model.accountType.EAccountUserType;
import com.orchware.account.registration.controller.BaseResponse;
import com.orchware.account.registration.mapper.RegisterRequestDTO;
import com.orchware.account.registration.mapper.RegisterResponseDTO;
import com.orchware.account.service.AccountService;
import com.orchware.account.service.AccountUserService;
import com.orchware.account.service.UserAuthService;
import com.orchware.account.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.orchware.account.constants.RegistrationConstants.Message.Service.REGISTERED_SUCCESS;


@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountUserService accountUserService;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public BaseResponse registerAccountUser(RegisterRequestDTO registerRequestDTO) {
        try {
            return BaseResponse.builder()
                    .data(RegisterResponseDTO.builder()
                            .accountDTO(setAccount(registerRequestDTO))
                            .userAuthDTO(setUserAuthentication(registerRequestDTO))
                            .accountUserDTO(setAccountUser(registerRequestDTO))
                            .build())
                    .message(String.format(REGISTERED_SUCCESS, registerRequestDTO.getUsername()))
                    .code(200)
                    .build();

        } catch (Exception e) {
            return BaseResponse.builder()
                    .message(e.getMessage())
                    .throwable(e.getCause())
                    .code(409)
                    .build();

        }

    }

    private AccountDTO setAccount(RegisterRequestDTO registerRequestDTO) {
        return AccountMapper.toAccountDTO(
                accountService.saveOrUpdate(
                        Account.builder()
                                .acctName(registerRequestDTO.getFirstName() + " " + registerRequestDTO.getLastName())
                                .status("Active")
                                .acctType(String.valueOf(EAccountUserType.BASIC))
                                .build()));
    }

    private AccountUserDTO setAccountUser(RegisterRequestDTO registerRequestDTO) {
        return AccountMapper.toAccountUserDTO(
                accountUserService.saveOrUpdate(
                        AccountUser.builder()
                                .firstName(registerRequestDTO.getFirstName())
                                .lastName(registerRequestDTO.getLastName())
                                .accountUserTypes(registerRequestDTO.getAccountUserType())
                                .email(registerRequestDTO.getEmail()).build()));
    }

    private UserAuthDTO setUserAuthentication(RegisterRequestDTO registerRequestDTO) throws AlreadyExistsException {
        return UserAuthMapper.toUserAuthDTO(
                userAuthService.saveOrUpdate(
                        UserAuth.builder()
                                .username(registerRequestDTO.getUsername())
                                .authType(RegistrationConstants.STANDARD)
                                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                                .build()));
    }
}
