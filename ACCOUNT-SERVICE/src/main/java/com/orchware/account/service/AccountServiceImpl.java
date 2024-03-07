package com.orchware.account.service;

import com.orchware.account.exception.NotFoundException;
import com.orchware.account.mapper.AccountMapper;
import com.orchware.account.model.Account;
import com.orchware.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account saveOrUpdate(Account account) {
        if (account.getAcctId() != null) {
            Account accountData = findById(account.getAcctId());
            if (accountData == null) {
                throw new NotFoundException(Account.class, account.getAcctId());
            }
            return accountRepository.save(AccountMapper.toUpdatedAccount(accountData, account));
        }
        return accountRepository.save(account);
    }

    @Override
    public Account findById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()
                -> new NotFoundException(Account.class, accountId));
    }
}
