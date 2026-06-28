package com.atm.entity;

/**
 * Lớp Account (Tính chất: Kế thừa - Inheritance)
 *
 * Account kế thừa từ lớp cha User, đại diện cho một tài khoản ngân hàng của người dùng.
 * - Mở rộng các thuộc tính riêng: accountNumber, balance, pinCode, isLocked.
 * - Constructor gọi lại constructor của lớp cha thông qua 'super(...)'.
 * - Đảm bảo tính đóng gói với các thuộc tính private và Getter/Setter tương ứng.
 *
 * @author Advanced ATM Simulator (CodeGym)
 */
public class Account extends User {

    // ===== Thuộc tính riêng (Encapsulation) =====
    private String accountNumber;
    private double balance;
    private String pinCode;
    private boolean isLocked;

    // ===== Constructor không tham số =====
    public Account() {
        super();
    }

    // ===== Constructor đầy đủ tham số (kế thừa từ User) =====
    public Account(int id, String username, String password, String role,
                   String accountNumber, double balance, String pinCode, boolean isLocked) {
        super(id, username, password, role);
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pinCode = pinCode;
        this.isLocked = isLocked;
    }

    // ===== Getter / Setter =====
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
