package com.orchware.account.service;
import com.orchware.account.model.Account;

public interface AccountService {

    Account saveOrUpdate(Account account);

    Account findById(Long accountId);
}
