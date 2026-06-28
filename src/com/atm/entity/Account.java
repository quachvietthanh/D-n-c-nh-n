package com.atm.entity;


public class Account extends User {
   
    private String accountNumber;
    private double balance;
    private String pinCode;
    private boolean isLocked;

    public Account() {
        super();
    }

    public Account(int id, String username, String password, String role,
                   String accountNumber, double balance, String pinCode, boolean isLocked) {
        super(id, username, password, role);
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pinCode = pinCode;
        this.isLocked = isLocked;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
