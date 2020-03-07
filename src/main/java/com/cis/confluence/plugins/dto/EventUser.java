package com.cis.confluence.plugins.dto;

import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.user.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class EventUser implements Serializable, User, Comparable<EventUser> {

    private Icon icon;
    private String email;
    private String name;
    private int space;
    private int page;
    private int blog;
    private int comment;
    private int like;

    public EventUser(String email, String name, Icon icon) {
        this.email = email;
        this.name = name;
        this.icon = icon;
        this.space = 0;
        this.blog = 0;
        this.page = 0;
        this.comment = 0;
        this.like = 0;
    }

    public Icon getIcon() {
        return icon;
    }

    @Override
    public String getFullName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
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
        return name +
                " :: email='" + email + '\'' +
                ", space=" + space +
                ", page=" + page +
                ", blog=" + blog +
                ", comment=" + comment +
                ", like=" + like +
                '}';
    }

    @Override
    public int compareTo(@NotNull EventUser user) {

        if (user == null){
            return -1;
        }

        int thisScore = space+page+blog+comment+like;
        int userScore = user.getSpace()+user.getPage()+user.getBlog()+user.getComment()+user.getLike();

        if (userScore == thisScore){
            return 0;
        }

        return thisScore < userScore ? 1 : -1;
    }

    public int totalScore(){
        return space+page+blog+comment+like;
    }
}
