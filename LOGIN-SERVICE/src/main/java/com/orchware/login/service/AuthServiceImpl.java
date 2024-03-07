package com.orchware.login.service;

import com.orchware.login.controller.BaseResponse;
import com.orchware.login.feignInterface.AccountInterface;
import com.orchware.login.jwt.JwtTokenUtil;
import com.orchware.login.requestDTO.JwtRequest;
import com.orchware.login.responseDTO.account.UserAuthDTO;
import com.orchware.login.responseDTO.register.RegisterRequestDTO;
import com.orchware.login.service.userDetails.AccountDetailsServiceImpl;
import com.orchware.login.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AccountDetailsServiceImpl accountDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AccountInterface accountInterface;

    @Autowired
    private PasswordEncoder encoder;

    public AuthServiceImpl(AccountDetailsServiceImpl accountDetailsService, JwtTokenUtil jwtTokenUtil, AccountInterface accountInterface) {
        this.accountDetailsService = accountDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountInterface = accountInterface;
    }

    public BaseResponse signup(RegisterRequestDTO registerRequestDTO) {
        return accountInterface.registerAccount(registerRequestDTO);
    }

    @Override
    public BaseResponse signin(HttpServletRequest request, JwtRequest requestDTO) {

        LOGGER.info("LOGIN PROCESS STARTED ::::");
        long startTime = DateUtils.getTimeInMillisecondsFromLocalDate();
        String userName = requestDTO.getUsername();
        final UserAuthDTO userAuthDTO = accountDetailsService.loadUserByUsername(userName);

        if (!encoder.matches(requestDTO.getPassword(), userAuthDTO.getPassword())) {
            return BaseResponse.builder()
                    .message("Invalid username or password")
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }

        String jwtToken = jwtTokenUtil.generateToken(userAuthDTO.getUserDetails());
//        UserAuthDTO userAuthDTO = accountDetailsService.getUser();
        userAuthDTO.setJwtToken(jwtToken);

        LOGGER.info("LOGIN PROCESS COMPLETED IN ::: " + (DateUtils.getTimeInMillisecondsFromLocalDate() - startTime)
                + " ms");
        LOGGER.info("Logged in as " + userName);
        return BaseResponse.builder()
                .data(userAuthDTO)
                .code(200)
                .build();
    }
}
