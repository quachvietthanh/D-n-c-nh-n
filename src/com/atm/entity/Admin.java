package com.atm.entity;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(int id, String username, String password, String role) {
        super(id, username, password, role);
    }
}
