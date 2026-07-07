package com.atm.service;

import com.atm.entity.Account;
import com.atm.exception.AccountLockedException;
import com.atm.exception.InsufficientBalanceException;
import com.atm.exception.InvalidPinException;
import com.atm.repository.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private AccountServiceImpl accountService;
    private Account testAccount;
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountService = new AccountServiceImpl();
        accountRepository = new AccountRepository();

        testAccount = new Account(99, "testuser", "123456", "USER",
                "9999999999", 500_000, "123456", false);
        accountRepository.add(testAccount);

        Account targetAccount = new Account(98, "target", "654321", "USER",
                "8888888888", 300_000, "654321", false);
        accountRepository.add(targetAccount);
    }

    @Test
    public void testWithdraw_Success() throws Exception {
        double withdrawAmount = 200_000;
        double expectedBalance = 300_000;

        accountService.withdraw(testAccount, withdrawAmount);

        assertEquals(expectedBalance, testAccount.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw_InsufficientBalance_ShouldThrowException() throws Exception {
        double withdrawAmount = 600_000;

        assertThrows(InsufficientBalanceException.class, () -> {
            accountService.withdraw(testAccount, withdrawAmount);
        });
    }

    @Test
    public void testWithdraw_AccountLocked_ShouldThrowException() throws Exception {
        testAccount.setLocked(true);

        assertThrows(AccountLockedException.class, () -> {
            accountService.withdraw(testAccount, 100_000);
        });
    }

    @Test
    public void testDeposit_Success() throws Exception {
        double depositAmount = 100_000;
        double expectedBalance = 600_000;

        accountService.deposit(testAccount, depositAmount);

        assertEquals(expectedBalance, testAccount.getBalance(), 0.001);
    }

    @Test
    public void testDeposit_NegativeAmount_ShouldThrowException() throws Exception {
        double depositAmount = -50_000;

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(testAccount, depositAmount);
        });
    }

    @Test
    public void testDeposit_ZeroAmount_ShouldThrowException() throws Exception {
        double depositAmount = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(testAccount, depositAmount);
        });
    }

    @Test
    public void testTransfer_Success() throws Exception {
        double transferAmount = 200_000;
        double expectedSourceBalance = 300_000;

        accountService.transfer(testAccount, "8888888888", transferAmount);

        assertEquals(expectedSourceBalance, testAccount.getBalance(), 0.001);
    }

    @Test
    public void testTransfer_InsufficientBalance_ShouldThrowException() throws Exception {
        double transferAmount = 600_000;

        assertThrows(InsufficientBalanceException.class, () -> {
            accountService.transfer(testAccount, "8888888888", transferAmount);
        });
    }

    @Test
    public void testTransfer_AccountNotFound_ShouldThrowException() throws Exception {
        double transferAmount = 100_000;

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.transfer(testAccount, "0000000000", transferAmount);
        });
    }

    @Test
    public void testChangePin_Success() throws Exception {
        String oldPin = "123456";
        String newPin = "654321";

        accountService.changePin(testAccount, oldPin, newPin);

        assertEquals(newPin, testAccount.getPinCode());
    }

    @Test
    public void testChangePin_WrongOldPin_ShouldThrowException() throws Exception {
        String wrongOldPin = "000000";
        String newPin = "654321";

        assertThrows(InvalidPinException.class, () -> {
            accountService.changePin(testAccount, wrongOldPin, newPin);
        });
    }

    @Test
    public void testChangePin_NewPinNotSixDigits_ShouldThrowException() throws Exception {
        String oldPin = "123456";

        assertThrows(InvalidPinException.class, () -> {
            accountService.changePin(testAccount, oldPin, "12345");
        });
    }

    @Test
    public void testChangePin_NewPinContainsLetters_ShouldThrowException() throws Exception {
        String oldPin = "123456";

        assertThrows(InvalidPinException.class, () -> {
            accountService.changePin(testAccount, oldPin, "abc123");
        });
    }
}
