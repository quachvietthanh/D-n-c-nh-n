package com.atm;

import com.atm.controller.ATMController;
import com.atm.entity.Account;
import com.atm.repository.AccountRepository;
import com.atm.service.AccountServiceImpl;
import com.atm.view.ATMView;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String DEFAULT_ROLE = "USER";

    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
        AccountServiceImpl accountService = new AccountServiceImpl();
        ATMController atmController = new ATMController(accountService);
        ATMView atmView = new ATMView(atmController);

        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            accountRepository.add(new Account(1, "user1", "pass1", DEFAULT_ROLE,
                    "1234567890", 5000000, "123456", false));
            accountRepository.add(new Account(2, "user2", "pass2", DEFAULT_ROLE,
                    "0987654321", 3000000, "654321", false));
            accountRepository.add(new Account(3, "admin", "admin", DEFAULT_ROLE,
                    "1111111111", 10000000, "111111", false));
            System.out.println("Da tao san du lieu tai khoan mau.");
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("-----------------------------------");
            System.out.println("=== CHAO MUNG DEN VOI BANK ATM ===");
            System.out.println("1. Dang nhap");
            System.out.println("2. Dang ky tai khoan moi");
            System.out.println("3. Thoat");
            System.out.println("-----------------------------------");
            System.out.print("Chon chuc nang (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleLogin(accountRepository, atmController, atmView, scanner);
                    break;
                case 2:
                    handleRegister(accountService, scanner);
                    break;
                case 3:
                    System.out.println("Tam biet!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Chuc nang khong hop le. Vui long chon lai!");
            }
        }
    }

    private static void handleLogin(AccountRepository accountRepository,
                                     ATMController atmController,
                                     ATMView atmView,
                                     Scanner scanner) {
        while (true) {
            System.out.println("========= DANG NHAP HE THONG ATM =========");
            System.out.print("Nhap so tai khoan: ");
            String accountNumber = scanner.nextLine();
            System.out.print("Nhap mat khau: ");
            String password = scanner.nextLine();

            boolean loginSuccess = atmController.login(accountNumber, password);
            if (loginSuccess) {
                List<Account> allAccounts = accountRepository.findAll();
                Account currentAccount = null;
                for (Account acc : allAccounts) {
                    if (acc.getAccountNumber().equals(accountNumber)) {
                        currentAccount = acc;
                        break;
                    }
                }
                System.out.println("Dang nhap thanh cong! Xin chao " + currentAccount.getUsername());
                atmView.displayMainMenu(currentAccount);
                break;
            } else {
                System.out.println("Sai so tai khoan hoac mat khau. Vui long nhap lai!");
            }
        }
    }

    private static void handleRegister(AccountServiceImpl accountService, Scanner scanner) {
        System.out.println("========= DANG KY TAI KHOAN MOI =========");
        System.out.print("Nhap so tai khoan: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Nhap ten dang nhap: ");
        String username = scanner.nextLine();
        System.out.print("Nhap mat khau: ");
        String password = scanner.nextLine();
        System.out.print("Nhap so du khoi tao: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Nhap ma PIN (6 so): ");
        String pin = scanner.nextLine();

        if (pin == null || !pin.matches("\\d{6}")) {
            System.out.println("Ma PIN phai gom dung 6 chu so. Vui long dang ky lai!");
            return;
        }

        try {
            Account newAccount = new Account(0, username, password, DEFAULT_ROLE,
                    accountNumber, balance, pin, false);
            accountService.registerAccount(newAccount);
            System.out.println("Dang ky tai khoan thanh cong! Bay gio ban co the dang nhap.");
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}
