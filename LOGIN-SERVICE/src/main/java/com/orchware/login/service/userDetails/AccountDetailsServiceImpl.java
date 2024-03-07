package com.orchware.login.service.userDetails;

import com.orchware.login.feignInterface.AccountInterface;
import com.orchware.login.responseDTO.account.UserAuthDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsServiceImpl {

    private AccountInterface accountInterface;
//    private static final ThreadLocal<UserAuthDTO> userAuthDTOThreadLocal = new ThreadLocal<>();

    public AccountDetailsServiceImpl(AccountInterface accountInterface) {
        this.accountInterface = accountInterface;
    }

    public UserAuthDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthDTO userAuthDTO = accountInterface.findByUsername(username);
        if (userAuthDTO == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
//        this.userAuthDTOThreadLocal.set(userAuthDTO);
        userAuthDTO.setUserDetails(AccountDetailsImpl.build(userAuthDTO));
        return userAuthDTO;
    }

    public UserAuthDTO getUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthDTO userAuthDTO = accountInterface.findByUsername(username);
        if (userAuthDTO == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
//        this.userAuthDTOThreadLocal.set(userAuthDTO);
        return userAuthDTO;
    }

    /*public UserAuthDTO getUser() {
        return this.userAuthDTOThreadLocal.get();
    }*/

}
