package com.orchware.core.feignInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.orchware.core.constants.MicroserviceConstants.AccountService.*;

@FeignClient(name = BASE, path = ACCOUNT_BASE_API)
public interface AccountInterface {

    @GetMapping(value = IS_VALID_ACCOUNT)
    boolean isValidAccount(@PathVariable Long acctId);
}
