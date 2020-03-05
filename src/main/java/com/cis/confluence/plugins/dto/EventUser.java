package com.cis.confluence.plugins.dto;

import java.io.Serializable;

public class EventUser implements Serializable {

    private String correo;
    private String fullName;
    private int space;
    private int page;
    private int blog;
    private int comment;
    private int like;

    public EventUser(String correo, String fullName) {
        this.correo = correo;
        this.fullName = fullName;
        this.space = 0;
        this.blog = 0;
        this.page = 0;
        this.comment = 0;
        this.like = 0;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFullName() {
        return fullName;
    }

    public int getSpace() {
        return space;
    }

    public int getPage() {
        return page;
    }

    public int getBlog() {
        return blog;
    }

    public int getComment() {
        return comment;
    }

    public int getLike() {
        return like;
    }

    public void addSpace(){
        space += 1;
    }

    public void addBlog(){
        blog += 1;
    }

    public void addPage(){
        page += 1;
    }

    public void addComment(){
        comment += 1;
    }

    public void addLike(){
        like += 1;
    }

    @Override
    public String toString() {
        return "EventUser{" +
                "correo='" + correo + '\'' +
                ", fullName='" + fullName + '\'' +
                ", space=" + space +
                ", page=" + page +
                ", blog=" + blog +
                ", comment=" + comment +
                ", like=" + like +
                '}';
    }
}
