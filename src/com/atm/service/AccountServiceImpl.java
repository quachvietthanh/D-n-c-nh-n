package com.atm.service;

import com.atm.entity.Account;
import com.atm.exception.AccountLockedException;
import com.atm.exception.InsufficientBalanceException;
import com.atm.exception.InvalidPinException;
import com.atm.repository.AccountRepository;
import java.util.List;

public class AccountServiceImpl implements AccountServiceInterface {

    private final AccountRepository accountRepository;

    public AccountServiceImpl() {
        this.accountRepository = new AccountRepository();
    }

    @Override
    public synchronized void withdraw(Account account, double amount)
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

    @Override
    public synchronized void deposit(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("So tien nap phai lon hon 0");
        }
        account.setBalance(account.getBalance() + amount);
        updateAccountInFile(account);
    }

    @Override
    public synchronized void transfer(Account sourceAccount, String targetAccountNumber, double amount)
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
        if (targetAccount.isLocked()) {
            throw new AccountLockedException("Tai khoan dich da bi khoa");
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

    @Override
    public synchronized void changePin(Account account, String oldPin, String newPin)
            throws InvalidPinException {
        if (!account.getPinCode().equals(oldPin)) {
            throw new InvalidPinException("Ma PIN cu khong dung");
        }
        if (newPin == null || !newPin.matches("\\d{6}")) {
            throw new InvalidPinException("Ma PIN moi phai gom 6 chu so");
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
