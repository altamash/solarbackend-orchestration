package com.orchware.account.service;

import com.orchware.account.exception.NotFoundException;
import com.orchware.account.mapper.AccountMapper;
import com.orchware.account.model.AccountUser;
import com.orchware.account.repository.AccountUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountUserServiceImpl implements AccountUserService {

    @Autowired
    AccountUserRepository accountUserRepository;

    @Override
    public AccountUser saveOrUpdate(AccountUser accountUser) {
        if (accountUser.getUserId() != null) {
            AccountUser accountUserData = findById(accountUser.getUserId());
            if (accountUserData == null) {
                throw new NotFoundException(AccountUser.class, accountUser.getUserId());
            }
            return accountUserRepository.save(AccountMapper.toUpdatedAccountUser(accountUserData, accountUser));
        }
        return accountUserRepository.save(accountUser);
    }

    @Override
    public AccountUser findById(Long id) {
        return accountUserRepository.findById(id).orElseThrow(() -> new NotFoundException(AccountUser.class, id));
    }

    @Override
    public boolean isValidAccount(Long acctId) {
        return accountUserRepository.findById(acctId).isPresent();
    }

}
