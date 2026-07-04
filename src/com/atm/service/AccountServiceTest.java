package com.atm.service;

import com.atm.entity.Account;
import com.atm.exception.InsufficientBalanceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private AccountService accountService;
    private Account testAccount;

    @BeforeEach
    public void setUp() {
        accountService = new AccountService();
        testAccount = new Account(1, "testuser", "123456", "USER",
                "1234567890", 500_000, "1234", false);
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
}
