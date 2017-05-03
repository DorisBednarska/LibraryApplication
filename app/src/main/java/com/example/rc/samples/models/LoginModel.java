package com.example.rc.samples.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class LoginModel implements Serializable {

    private String email;

    private String password;

    private String name;

    private String surname;

    @SerializedName("birthday")
    private Date birthday;

    private String token;

    private Long id;

    public LoginModel(String s, String s1) {
        this.email = s;
        this.password = s1;
    }

    public LoginModel(String email, String password, String name, String surname, Date birthday) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
