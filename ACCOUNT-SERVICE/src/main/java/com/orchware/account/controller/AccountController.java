package com.orchware.account.controller;

import com.orchware.account.mapper.AccountUserDTO;
import com.orchware.account.mapper.UserAuthDTO;
import com.orchware.account.mapper.UserAuthMapper;
import com.orchware.account.service.AccountUserService;
import com.orchware.account.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.orchware.account.mapper.AccountMapper.toAccountUser;
import static com.orchware.account.mapper.AccountMapper.toAccountUserDTO;

@CrossOrigin
@RestController("AccountController")
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountUserService accountUserService;
    @Autowired
    UserAuthService userAuthService;

    @PostMapping("/save")
    public AccountUserDTO saveOrUpdate(@RequestBody AccountUserDTO accountUserDTO) throws Exception {
        return toAccountUserDTO(accountUserService.saveOrUpdate(toAccountUser(accountUserDTO)));
    }

    @GetMapping("/findByUsername/{username}")
    public UserAuthDTO findByUsername(@PathVariable("username") String username) {
        UserAuthDTO userAuthDTO = UserAuthMapper.toUserAuthDTO(userAuthService.findUserAuthByUsernameNoThrow(username));
//        return new ResponseEntity<>(userAuthDTO, HttpStatus.OK);
        return userAuthDTO;
    }

    @GetMapping("/isValidAccount/{acctId}")
    public boolean isValidAccount(@PathVariable Long acctId) {
        return accountUserService.isValidAccount(acctId);
    }
}
