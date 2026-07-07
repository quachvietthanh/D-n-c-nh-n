package com.atm.service;

import com.atm.entity.Account;
import com.atm.exception.AccountLockedException;
import com.atm.exception.InsufficientBalanceException;
import com.atm.exception.InvalidPinException;

public interface AccountServiceInterface {

    void withdraw(Account account, double amount)
            throws AccountLockedException, InsufficientBalanceException;

    void deposit(Account account, double amount);

    void transfer(Account sourceAccount, String targetAccountNumber, double amount)
            throws AccountLockedException, InsufficientBalanceException;

    void changePin(Account account, String oldPin, String newPin)
            throws InvalidPinException;
}
