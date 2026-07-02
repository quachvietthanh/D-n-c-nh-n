package com.atm.repository;

import com.atm.entity.Account;

public class AccountRepository extends BaseRepositoryImpl<Account> {

    public AccountRepository() {
        super("data/accounts.dat", Account.class);
    }
}
