package com.atm.entity;

public class UserFactory {

    public static User createUser(String role, int id, String username, String password) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            return new Admin(id, username, password, "ADMIN");
        } else if ("USER".equalsIgnoreCase(role)) {
            return new Account(
                    id,
                    username,
                    password,
                    "USER",
                    "",     
                    0.0,    
                    "0000", 
                    false   
            );
        } else {
            throw new IllegalArgumentException("Vai trò không hợp lệ: " + role
                    + ". Chỉ chấp nhận 'ADMIN' hoặc 'USER'.");
        }
    }
}
