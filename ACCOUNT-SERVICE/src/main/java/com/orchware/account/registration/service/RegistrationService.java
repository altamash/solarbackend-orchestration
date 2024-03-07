package com.orchware.account.registration.service;

import com.orchware.account.registration.controller.BaseResponse;
import com.orchware.account.registration.mapper.RegisterRequestDTO;

public interface RegistrationService {

    BaseResponse registerAccountUser(RegisterRequestDTO registerRequestDTO);
}
