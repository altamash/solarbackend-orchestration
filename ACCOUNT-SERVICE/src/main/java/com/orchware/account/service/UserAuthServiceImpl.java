package com.orchware.account.service;

import com.orchware.account.exception.AlreadyExistsException;
import com.orchware.account.model.UserAuth;
import com.orchware.account.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    UserAuthRepository userAuthRepository;

    @Override
    public UserAuth saveOrUpdate(UserAuth userAuth) {
        //Check for username
        UserAuth userAuthExists = findUserAuthByUsernameNoThrow(userAuth.getUsername());
        if (userAuthExists != null) {
            throw new AlreadyExistsException(userAuth.getUsername());
        }
        return userAuthRepository.save(userAuth);
    }

    @Override
    public UserAuth findUserAuthByUsername(String username) {
        return userAuthRepository.findUserAuthByUsername(username).orElseThrow(() -> new AlreadyExistsException(username));
    }

    @Override
    public UserAuth findUserAuthByUsernameNoThrow(String username) {
        return userAuthRepository.findUserAuthByUsername(username).orElse(null);
    }
}
