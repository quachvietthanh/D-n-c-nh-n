package com.atm.entity;

import java.time.LocalDateTime;

/**
 * Lớp Transaction (Lưu trữ lịch sử giao dịch)
 *
 * Đây là một class độc lập, dùng để lưu lại thông tin chi tiết của mỗi giao dịch
 * mà người dùng thực hiện trên hệ thống ATM.
 * - Mỗi giao dịch có một mã giao dịch duy nhất (transactionId).
 * - type: loại giao dịch (ví dụ: "RUT" - rút tiền, "NAP" - nạp tiền, "CHUYEN" - chuyển khoản).
 * - timestamp: thời điểm giao dịch được thực hiện, sử dụng java.time.LocalDateTime.
 *
 * @author Advanced ATM Simulator (CodeGym)
 */
public class Transaction {

    // ===== Thuộc tính (Encapsulation) =====
    private String transactionId;
    private String accountNumber;
    private String type;          // RUT, NAP, CHUYEN
    private double amount;
    private LocalDateTime timestamp;

    // ===== Constructor không tham số =====
    public Transaction() {
    }

    // ===== Constructor đầy đủ tham số =====
    public Transaction(String transactionId, String accountNumber, String type,
                       double amount, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // ===== Getter / Setter =====
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
