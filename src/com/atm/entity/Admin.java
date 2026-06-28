package com.atm.entity;

/**
 * Lớp Admin (Tính chất: Kế thừa - Inheritance)
 *
 * Admin kế thừa tất cả các thuộc tính và phương thức từ lớp cha User.
 * Đại diện cho người dùng có quyền quản trị hệ thống ATM.
 * - Sử dụng từ khóa 'extends' để thể hiện quan hệ IS-A (Admin là một User).
 * - Constructor gọi lại constructor của lớp cha thông qua 'super(...)'.
 *
 * @author Advanced ATM Simulator (CodeGym)
 */
public class Admin extends User {

    // ===== Constructor không tham số =====
    public Admin() {
        super();
    }

    // ===== Constructor đầy đủ tham số =====
    public Admin(int id, String username, String password, String role) {
        super(id, username, password, role);
    }
}
