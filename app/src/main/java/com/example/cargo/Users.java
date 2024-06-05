package com.example.cargo;

public class Users {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String password;
    public boolean isAdmin;

    public Users() {

    }

    public Users(String firstName, String lastName, String email, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isAdmin = false;
    }
}
