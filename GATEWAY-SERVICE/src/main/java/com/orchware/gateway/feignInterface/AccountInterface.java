package com.orchware.gateway.feignInterface;

import com.orchware.gateway.constants.MicroServiceConstants;
import com.orchware.gateway.responseDTO.AccountUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@FeignClient(name = MicroServiceConstants.AccountServiceConstants.BASE, path = MicroServiceConstants.ACCOUNT_BASE_API)
@Service
public interface AccountInterface {

    @RequestMapping(value = MicroServiceConstants.CoreServiceConstants.FETCH_ACCOUNT_BY_USERNAME)
    AccountUserDTO fetchUserByUsername(@PathVariable("username") String username);
}
