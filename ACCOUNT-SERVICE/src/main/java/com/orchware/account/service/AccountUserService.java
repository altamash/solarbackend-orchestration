package com.orchware.account.service;

import com.orchware.account.model.AccountUser;

public interface AccountUserService {

    AccountUser saveOrUpdate(AccountUser accountUser);

    AccountUser findById(Long id);

    boolean isValidAccount(Long acctId);
}
