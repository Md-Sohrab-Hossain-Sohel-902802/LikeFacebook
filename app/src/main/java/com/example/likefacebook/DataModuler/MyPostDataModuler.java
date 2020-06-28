package com.example.likefacebook.DataModuler;

public class MyPostDataModuler
{

    String text;
    String image;
    String postid;
    String userid;
    String like;
    String comment;
    String name;


    public MyPostDataModuler() {
    }


    public MyPostDataModuler(String text, String image, String postid, String userid, String like, String comment,String name) {
        this.text = text;
        this.image = image;
        this.postid = postid;
        this.userid = userid;
        this.like = like;
        this.comment = comment;
        this.name=name;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
