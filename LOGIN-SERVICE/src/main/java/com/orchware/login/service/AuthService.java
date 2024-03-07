package com.orchware.login.service;

import com.orchware.login.controller.BaseResponse;
import com.orchware.login.requestDTO.JwtRequest;
import com.orchware.login.responseDTO.account.UserAuthDTO;
import com.orchware.login.responseDTO.register.RegisterRequestDTO;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    BaseResponse signup(RegisterRequestDTO registerRequestDTO);
    BaseResponse signin(HttpServletRequest request, JwtRequest requestDTO);
}
