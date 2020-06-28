package com.example.likefacebook.DataModuler;

public class UserData {

        String email;
        String password;
        String uid;
        String phonenumber;
        String name;


    public UserData() {
    }

    public UserData(String email, String password, String uid,String phonenumber,String name) {
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.phonenumber=phonenumber;
        this.name=name;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
