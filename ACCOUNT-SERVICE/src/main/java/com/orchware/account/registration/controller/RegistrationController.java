package com.orchware.account.registration.controller;

import com.orchware.account.registration.mapper.RegisterRequestDTO;
import com.orchware.account.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("RegistrationController")
@RequestMapping("/account")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    /**
     * Standard User Registration
     * @param registerRequestDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/stdRegistration")
    public BaseResponse<?> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return registrationService.registerAccountUser(registerRequestDTO);
    }
}
