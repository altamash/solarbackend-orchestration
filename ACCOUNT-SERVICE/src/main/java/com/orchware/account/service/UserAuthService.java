package com.orchware.account.service;

import com.orchware.account.model.UserAuth;

public interface UserAuthService {

    UserAuth saveOrUpdate(UserAuth userAuth);

    UserAuth findUserAuthByUsername(String username);
    UserAuth findUserAuthByUsernameNoThrow(String username);
}
