package com.atm.controller;

import com.atm.entity.Account;
import com.atm.exception.AccountLockedException;
import com.atm.exception.InsufficientBalanceException;
import com.atm.exception.InvalidPinException;
import com.atm.repository.AccountRepository;
import com.atm.service.AccountServiceInterface;
import java.util.List;

public class ATMController {

    private final AccountServiceInterface accountService;

    public ATMController(AccountServiceInterface accountService) {
        this.accountService = accountService;
    }

    public boolean login(String accountNumber, String password) {
        AccountRepository accountRepository = new AccountRepository();
        List<Account> accounts = accountRepository.findAll();
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)
                    && acc.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void handleWithdraw(Account account, double amount)
            throws AccountLockedException, InsufficientBalanceException {
        accountService.withdraw(account, amount);
    }

    public void handleDeposit(Account account, double amount) {
        accountService.deposit(account, amount);
    }

    public void handleTransfer(Account sourceAccount, String targetAccountNumber, double amount)
            throws AccountLockedException, InsufficientBalanceException {
        accountService.transfer(sourceAccount, targetAccountNumber, amount);
    }

    public void handleChangePin(Account account, String oldPin, String newPin)
            throws InvalidPinException {
        accountService.changePin(account, oldPin, newPin);
    }
}
