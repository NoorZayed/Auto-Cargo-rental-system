package com.example.cargo;

public class Users {

        private String fname;
        private String lname;
        private String email;
        private String phone;
        private String pass;
        public Users(){}

    public Users(String fname, String lname, String email, String phone, String pass) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
