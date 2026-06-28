package com.atm.entity;

/**
 * Lớp trừu tượng User (Tính chất: Trừu tượng hóa & Đóng gói)
 *
 * Đây là lớp cha trong hệ thống phân quyền ATM.
 * - Sử dụng 'abstract' để định nghĩa một khuôn mẫu chung cho tất cả các đối tượng người dùng.
 * - Các thuộc tính đều ở chế độ 'private' để đảm bảo tính đóng gói (Encapsulation),
 *   chỉ có thể truy cập và thay đổi thông qua các phương thức Getter/Setter.
 *
 * @author Advanced ATM Simulator (CodeGym)
 */
public abstract class User {

    // ===== Thuộc tính (Encapsulation) =====
    private int id;
    private String username;
    private String password;
    private String role;

    // ===== Constructor không tham số =====
    public User() {
    }

    // ===== Constructor đầy đủ tham số =====
    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ===== Getter / Setter =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
