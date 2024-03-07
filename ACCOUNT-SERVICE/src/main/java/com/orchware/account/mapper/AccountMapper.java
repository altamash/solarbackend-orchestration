package com.orchware.account.mapper;

import com.orchware.account.model.Account;
import com.orchware.account.model.AccountUser;
import com.orchware.account.model.accountType.AccountUserType;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    /**
     * For Accounts Table
     *
     * @param accountDTO
     * @return
     */
    public static Account toAccount(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        return Account.builder()
                .acctId(accountDTO.getAcctId())
                .acctType(accountDTO.getAcctType())
                .acctName(accountDTO.getAcctName())
                .status(accountDTO.getStatus())
                .businessName(accountDTO.getBusinessName())
                .build();
    }

    public static AccountDTO toAccountDTO(Account account) {
        if (account == null) {
            return null;
        }
        return AccountDTO.builder()
                .acctId(account.getAcctId())
                .acctType(account.getAcctType())
                .acctName(account.getAcctName())
                .status(account.getStatus())
                .businessName(account.getBusinessName())
                .build();
    }

    public static Account toUpdatedAccount(Account account, Account accountUpdate) {
        account.setAcctName(accountUpdate.getAcctName() == null ? account.getAcctName() : accountUpdate.getAcctName());
        account.setAcctType(accountUpdate.getAcctType() == null ? account.getAcctType() : accountUpdate.getAcctType());
        account.setStatus(accountUpdate.getStatus() == null ? account.getStatus() : accountUpdate.getStatus());
        account.setBusinessName(accountUpdate.getBusinessName() == null ? account.getBusinessName() : accountUpdate.getBusinessName());
        return account;
    }

    public static List<Account> toAccounts(List<AccountDTO> accountDTOS) {
        return accountDTOS.stream().map(c -> toAccount(c)).collect(Collectors.toList());
    }

    public static List<AccountDTO> toAccountDTOs(List<Account> accounts) {
        return accounts.stream().map(c -> toAccountDTO(c)).collect(Collectors.toList());
    }


    /**
     * For AccountUsers Table
     *
     * @param AccountUserDTO
     * @return
     */
    public static AccountUser toAccountUser(AccountUserDTO accountUserDTO) {
        if (accountUserDTO == null) {
            return null;
        }
        return AccountUser.builder()
                .userId(accountUserDTO.getUserId())
                .firstName(accountUserDTO.getFirstName())
                .lastName(accountUserDTO.getLastName())
                .middleName(accountUserDTO.getMiddleName())
                .phone(accountUserDTO.getPhone())
                .userType(accountUserDTO.getUserType())
                .pVerified(accountUserDTO.isPVerified())
                .email(accountUserDTO.getEmail())
                .add1(accountUserDTO.getAdd1())
                .add2(accountUserDTO.getAdd2())
                .add3(accountUserDTO.getAdd3())
                .state(accountUserDTO.getState())
                .postalCode(accountUserDTO.getPostalCode())
                .poBox(accountUserDTO.getPoBox())
                .country(accountUserDTO.getCountry())
                .ownerInd(accountUserDTO.isOwnerInd())
                .role(accountUserDTO.getRole())
                .build();
    }

    public static AccountUserDTO toAccountUserDTO(AccountUser accountUser) {
        if (accountUser == null) {
            return null;
        }
        return AccountUserDTO.builder()
                .userId(accountUser.getUserId())
                .firstName(accountUser.getFirstName())
                .lastName(accountUser.getLastName())
                .middleName(accountUser.getMiddleName())
                .phone(accountUser.getPhone())
                .userType(accountUser.getUserType())
                .pVerified(accountUser.isPVerified())
                .email(accountUser.getEmail())
                .add1(accountUser.getAdd1())
                .add2(accountUser.getAdd2())
                .add3(accountUser.getAdd3())
                .state(accountUser.getState())
                .postalCode(accountUser.getPostalCode())
                .poBox(accountUser.getPoBox())
                .country(accountUser.getCountry())
                .accountUserType(accountUser.getAccountUserTypes() != null ?
                        accountUser.getAccountUserTypes().stream().map(AccountUserType::getName).collect(Collectors.toSet()) : null)
                .ownerInd(accountUser.isOwnerInd())
                .role(accountUser.getRole())
                .build();
    }

    public static AccountUser toUpdatedAccountUser(AccountUser AccountUser, AccountUser AccountUserUpdate) {
        AccountUser.setFirstName(AccountUserUpdate.getFirstName() == null ? AccountUser.getFirstName() : AccountUserUpdate.getFirstName());
        AccountUser.setMiddleName(AccountUserUpdate.getMiddleName() == null ? AccountUser.getMiddleName() : AccountUserUpdate.getMiddleName());
        AccountUser.setLastName(AccountUserUpdate.getLastName() == null ? AccountUser.getLastName() : AccountUserUpdate.getLastName());
        AccountUser.setUserType(AccountUserUpdate.getUserType() == null ? AccountUser.getUserType() : AccountUserUpdate.getUserType());
        AccountUser.setPVerified(AccountUserUpdate.isPVerified() ? AccountUser.isPVerified() : AccountUserUpdate.isPVerified());
        AccountUser.setEmail(AccountUserUpdate.getEmail() == null ? AccountUser.getEmail() : AccountUserUpdate.getEmail());
        AccountUser.setAdd1(AccountUserUpdate.getAdd1() == null ? AccountUser.getAdd1() : AccountUserUpdate.getAdd1());
        AccountUser.setAdd2(AccountUserUpdate.getAdd2() == null ? AccountUser.getAdd2() : AccountUserUpdate.getAdd2());
        AccountUser.setAdd3(AccountUserUpdate.getAdd3() == null ? AccountUser.getAdd3() : AccountUserUpdate.getAdd3());
        AccountUser.setState(AccountUserUpdate.getState() == null ? AccountUser.getState() : AccountUserUpdate.getState());
        AccountUser.setPostalCode(AccountUserUpdate.getPostalCode() == null ? AccountUser.getPostalCode() : AccountUserUpdate.getPostalCode());
        AccountUser.setPoBox(AccountUserUpdate.getPoBox() == null ? AccountUser.getPoBox() : AccountUserUpdate.getPoBox());
        AccountUser.setCountry(AccountUserUpdate.getCountry() == null ? AccountUser.getCountry() : AccountUserUpdate.getCountry());
        AccountUser.setOwnerInd(AccountUserUpdate.isOwnerInd() ? AccountUser.isOwnerInd() : AccountUserUpdate.isOwnerInd());
        AccountUser.setRole(AccountUserUpdate.getRole() == null ? AccountUser.getRole() : AccountUserUpdate.getRole());
        return AccountUser;
    }

    public static List<AccountUser> toAccountUsers(List<AccountUserDTO> AccountUserDTOS) {
        return AccountUserDTOS.stream().map(c -> toAccountUser(c)).collect(Collectors.toList());
    }

    public static List<AccountUserDTO> toAccountUserDTOs(List<AccountUser> AccountUserList) {
        return AccountUserList.stream().map(c -> toAccountUserDTO(c)).collect(Collectors.toList());
    }

}
