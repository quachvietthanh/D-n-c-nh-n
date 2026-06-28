package com.atm.entity;

/**
 * Lớp UserFactory (Design Pattern: Factory Pattern)
 *
 * Factory Pattern là một Creational Design Pattern giúp che giấu logic khởi tạo đối tượng
 * khỏi phía client. Thay vì gọi trực tiếp 'new Admin()' hay 'new Account()',
 * client chỉ cần gọi UserFactory.createUser(...) và truyền vào tham số role.
 *
 * Lợi ích:
 * - Giảm sự phụ thuộc (loose coupling) giữa client và các lớp cụ thể.
 * - Dễ dàng mở rộng khi có thêm loại User mới trong tương lai.
 *
 * @author Advanced ATM Simulator (CodeGym)
 */
public class UserFactory {

    /**
     * Phương thức static tạo đối tượng User dựa trên tham số role.
     *
     * @param role     "ADMIN" hoặc "USER"
     * @param id       Mã định danh của người dùng
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return Đối tượng Admin hoặc Account tương ứng
     * @throws IllegalArgumentException Nếu role không hợp lệ
     */
    public static User createUser(String role, int id, String username, String password) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            return new Admin(id, username, password, "ADMIN");
        } else if ("USER".equalsIgnoreCase(role)) {
            // Các thuộc tính riêng của Account tạm thời để giá trị mặc định
            return new Account(
                    id,
                    username,
                    password,
                    "USER",
                    "",     // accountNumber: để trống, sẽ được cập nhật sau
                    0.0,    // balance: mặc định 0
                    "0000", // pinCode: mặc định
                    false   // isLocked: mặc định không khóa
            );
        } else {
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + role
                    + ". Chỉ chấp nhận 'ADMIN' hoặc 'USER'.");
        }
    }
}
