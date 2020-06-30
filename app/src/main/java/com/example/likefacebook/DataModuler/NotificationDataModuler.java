package com.example.likefacebook.DataModuler;

public class NotificationDataModuler {
    String postid;
    String userid;
    String value;


    public NotificationDataModuler() {
    }

    public NotificationDataModuler(String postid, String userid, String value) {
        this.postid = postid;
        this.userid = userid;
        this.value = value;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
