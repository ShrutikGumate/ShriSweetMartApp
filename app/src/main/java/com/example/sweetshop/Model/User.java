package com.example.sweetshop.Model;

public class User {
    public String fullname,email,phonenumber,password;

    public User(){

    }
    public User(String fullname,String email,String phonenumber,String password){
        this.fullname = fullname;
        this.email = email;
        this.phonenumber=phonenumber;
        this.password=password;
    }
}
