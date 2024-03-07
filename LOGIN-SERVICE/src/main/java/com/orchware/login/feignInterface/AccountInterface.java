package com.orchware.login.feignInterface;

import com.orchware.login.constants.MicroServiceConstants;
import com.orchware.login.controller.BaseResponse;
import com.orchware.login.responseDTO.account.UserAuthDTO;
import com.orchware.login.responseDTO.register.RegisterRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = MicroServiceConstants.AccountServiceConstants.BASE,
        path = MicroServiceConstants.ACCOUNT_BASE_API)
//        ,fallback = AccountInterfaceFallBack.class)
public interface AccountInterface {

    @PostMapping(value = MicroServiceConstants.AccountServiceConstants.REGISTER_ACCOUNT)
    BaseResponse registerAccount(@RequestBody RegisterRequestDTO registerRequestDTO);
    @GetMapping(value = MicroServiceConstants.AccountServiceConstants.FETCH_ACCOUNT_BY_USERNAME)
    UserAuthDTO findByUsername(@PathVariable("username") String username);
}
