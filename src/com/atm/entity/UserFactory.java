package com.atm.entity;

// Factory Pattern: che giấu logic khởi tạo, trả về User (abstract)
public class UserFactory {

    // static: không cần tạo instance, gọi trực tiếp UserFactory.createUser(...)
    // Return type là User (abstract), nhưng object thực tế là Account hoặc Admin
    public static User createUser(String role, int id, String username, String password) {
        if ("ADMIN".equalsIgnoreCase(role)) {               // equalsIgnoreCase: không phân biệt hoa/thường
            return new Admin(id, username, password, "ADMIN");
        } else if ("USER".equalsIgnoreCase(role)) {
            return new Account(                             // Polymorphism: trả về User, nhưng là Account
                    id, username, password, "USER",
                    "",                                     
                    0.0,                                   
                    "0000",                                 
                    false                                   
            );
        } else {
            throw new IllegalArgumentException("Vai tro khong hop le: " + role
                    + ". Chi chap nhan 'ADMIN' hoac 'USER'.");
        }
    }
}
