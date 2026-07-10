package com.atm.view;

import com.atm.controller.ATMController;
import com.atm.entity.Account;
import com.atm.exception.AccountLockedException;
import com.atm.exception.InsufficientBalanceException;
import com.atm.exception.InvalidPinException;
import java.util.Scanner;

public class ATMView {

    private final ATMController atmController;

    public ATMView(ATMController atmController) {
        this.atmController = atmController;
    }

    @SuppressWarnings("resource")
    public void displayMainMenu(Account currentAccount) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("-----------------------------------");
            System.out.println("========= MENU CHUC NANG =========");
            System.out.println("1. Xem so du");
            System.out.println("2. Rut tien");
            System.out.println("3. Nap tien");
            System.out.println("4. Chuyen tien");
            System.out.println("5. Doi ma PIN");
            System.out.println("6. Dang xuat");
            System.out.println("-----------------------------------");
            System.out.print("Chon chuc nang (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("So du hien tai: " + currentAccount.getBalance());
                    break;

                case 2:
                    System.out.print("Nhap so tien can rut: ");
                    String withdrawInput = scanner.nextLine().replace(",", "");
                    try {
                        double withdrawAmount = Double.parseDouble(withdrawInput);
                        atmController.handleWithdraw(currentAccount, withdrawAmount);
                        System.out.println("Rut tien thanh cong!");
                    } catch (NumberFormatException e) {
                        System.out.println("So tien nhap khong hop le, vui long nhap lai!");
                    } catch (AccountLockedException e) {
                        System.out.println("Loi: " + e.getMessage());
                    } catch (InsufficientBalanceException e) {
                        System.out.println("Loi: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Loi: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Nhap so tien can nap: ");
                    String depositInput = scanner.nextLine().replace(",", "");
                    try {
                        double depositAmount = Double.parseDouble(depositInput);
                        atmController.handleDeposit(currentAccount, depositAmount);
                        System.out.println("Nap tien thanh cong!");
                    } catch (NumberFormatException e) {
                        System.out.println("So tien nhap khong hop le, vui long nhap lai!");
                    } catch (Exception e) {
                        System.out.println("Loi: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Nhap so tai khoan dich: ");
                    String targetAccountNumber = scanner.nextLine();
                    System.out.print("Nhap so tien can chuyen: ");
                    String transferInput = scanner.nextLine().replace(",", "");
                    try {
                        double transferAmount = Double.parseDouble(transferInput);
                        atmController.handleTransfer(currentAccount, targetAccountNumber, transferAmount);
                        System.out.println("Chuyen tien thanh cong!");
                    } catch (NumberFormatException e) {
                        System.out.println("So tien nhap khong hop le, vui long nhap lai!");
                    } catch (AccountLockedException e) {
                        System.out.println("Loi: " + e.getMessage());
                    } catch (InsufficientBalanceException e) {
                        System.out.println("Loi: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Loi: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Nhap ma PIN cu: ");
                    String oldPin = scanner.nextLine();
                    System.out.print("Nhap ma PIN moi: ");
                    String newPin = scanner.nextLine();
                    try {
                        atmController.handleChangePin(currentAccount, oldPin, newPin);
                        System.out.println("Doi ma PIN thanh cong!");
                    } catch (InvalidPinException e) {
                        System.out.println("Loi: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Loi: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Da dang xuat. Tam biet!");
                    return;

                default:
                    System.out.println("Chuc nang khong hop le. Vui long chon lai!");
            }
        }
    }
}
