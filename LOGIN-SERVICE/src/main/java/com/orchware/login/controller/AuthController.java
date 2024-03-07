package com.orchware.login.controller;

import com.orchware.login.constants.WebResourceKeyConstants;
import com.orchware.login.requestDTO.JwtRequest;
import com.orchware.login.responseDTO.register.RegisterRequestDTO;
import com.orchware.login.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = WebResourceKeyConstants.AUTH_CONTROLLER)
@Slf4j
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = WebResourceKeyConstants.SIGNUP)
    public BaseResponse registerAccount(@RequestBody RegisterRequestDTO RegisterRequestDTO){
        return authService.signup(RegisterRequestDTO);
    }

    @PostMapping(value = WebResourceKeyConstants.SIGNIN)
    public BaseResponse<?> loginUser(HttpServletRequest request, @RequestBody JwtRequest jwtRequest) {
        try {
            return authService.signin(request, jwtRequest);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        return "test done";
    }
}
