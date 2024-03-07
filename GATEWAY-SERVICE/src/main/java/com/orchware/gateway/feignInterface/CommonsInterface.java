package com.orchware.gateway.feignInterface;

import com.orchware.gateway.constants.MicroServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Key;

@FeignClient(name = MicroServiceConstants.CommonsService.BASE, path = MicroServiceConstants.CommonsService.ENCRYPTION)
@Service
public interface CommonsInterface {

    @GetMapping(value = MicroServiceConstants.CommonsService.GET_PEM_STRING)
    String getPEMString(@RequestParam String algorithm, @RequestParam Class<? extends Key> keyClass);
}
