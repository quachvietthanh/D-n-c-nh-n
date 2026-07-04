package com.atm.service;

import com.atm.entity.Account;
import com.atm.exception.AccountLockedException;
import com.atm.exception.InsufficientBalanceException;
import com.atm.exception.InvalidPinException;
import com.atm.repository.AccountRepository;
import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public void withdraw(Account account, double amount)
            throws AccountLockedException, InsufficientBalanceException {
        if (account.isLocked()) {
            throw new AccountLockedException("Tai khoan da bi khoa");
        }
        if (amount > account.getBalance()) {
            throw new InsufficientBalanceException("So du khong du");
        }
        account.setBalance(account.getBalance() - amount);
        updateAccountInFile(account);
    }

    public void deposit(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        updateAccountInFile(account);
    }

    public void transfer(Account sourceAccount, String targetAccountNumber, double amount)
            throws AccountLockedException, InsufficientBalanceException {
        if (sourceAccount.isLocked()) {
            throw new AccountLockedException("Tai khoan nguon da bi khoa");
        }
        if (amount > sourceAccount.getBalance()) {
            throw new InsufficientBalanceException("So du khong du");
        }
        List<Account> accounts = accountRepository.findAll();
        Account targetAccount = null;
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(targetAccountNumber)) {
                targetAccount = acc;
                break;
            }
        }
        if (targetAccount == null) {
            throw new IllegalArgumentException("Khong tim thay tai khoan dich");
        }
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId() == sourceAccount.getId()) {
                accounts.set(i, sourceAccount);
            } else if (accounts.get(i).getId() == targetAccount.getId()) {
                accounts.set(i, targetAccount);
            }
        }
        accountRepository.saveAll(accounts);
    }

    public void changePin(Account account, String oldPin, String newPin)
            throws InvalidPinException {
        if (!account.getPinCode().equals(oldPin)) {
            throw new InvalidPinException("Ma PIN cu khong dung");
        }
        account.setPinCode(newPin);
        updateAccountInFile(account);
    }

    private void updateAccountInFile(Account account) {
        List<Account> accounts = accountRepository.findAll();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId() == account.getId()) {
                accounts.set(i, account);
                break;
            }
        }
        accountRepository.saveAll(accounts);
    }
}
