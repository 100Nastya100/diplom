package com.example.diplom.Models;

import java.util.PrimitiveIterator;

public class User {
    private  String name,fam,tel,email,password;
    public User() {}

    public User(String name, String fam, String tel, String email, String password) {
        this.name = name;
        this.fam = fam;
        this.tel = tel;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
