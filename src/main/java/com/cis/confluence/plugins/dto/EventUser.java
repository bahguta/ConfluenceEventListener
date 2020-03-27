package com.cis.confluence.plugins.dto;

import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.sal.api.user.UserKey;
import net.java.ao.schema.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table("EventUser")
public class EventUser implements ConfluenceUser, Serializable, Comparable<EventUser> {

    private Icon icon;
    private String email;
    private String name;
    private String fullName;
    private UserKey key;
    private int space;
    private int page;
    private int blog;
    private int comment;
    private int like;
    private boolean participate;

    public EventUser(String email, String name, String fullName, String key, Icon icon) {
        this.key = new UserKey(key);
        this.email = email;
        this.name = name;
        this.fullName = fullName;
        this.icon = icon;
        this.space = 0;
        this.blog = 0;
        this.page = 0;
        this.comment = 0;
        this.like = 0;
        this.participate = false;
    }

    public Icon getIcon() {
        return icon;
    }

    @Override
    public UserKey getKey() {
        return key;
    }

    public boolean isParticipate() {
        return participate;
    }

    public void setParticipate(boolean participate) {
        this.participate = participate;
    }

    @Override
    public String getFullName() {
        return fullName;
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

    public void setSpaces(int space) {
        this.space = space;
    }

    public void setPages(int page) {
        this.page = page;
    }

    public void setBlogs(int blog) {
        this.blog = blog;
    }

    public void setComments(int comment) {
        this.comment = comment;
    }

    public void setLikes(int like) {
        this.like = like;
    }

    public void restSpace() {
        if (space - 1 < 0){
            space = 0;
        } else {
            space--;
        }
    }

    public void restPage() {
        if (page - 1 < 0){
            page = 0;
        } else {
            page--;
        }
    }

    public void restBlog() {
        if (blog - 1 < 0){
            blog = 0;
        } else {
            blog--;
        }
    }

    public void restComment() {
        if (comment - 1 < 0){
            comment = 0;
        } else {
            comment--;
        }
    }

    public void restLike() {
        if (like - 1 < 0){
            like = 0;
        } else {
            like--;
        }
    }

    @Override
    public String toString() {
        return "EventUser{" +
                "icon=" + icon.getPath() +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", key=" + key.getStringValue() +
                ", space=" + space +
                ", page=" + page +
                ", blog=" + blog +
                ", comment=" + comment +
                ", like=" + like +
                ", participate=" + participate +
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

//    @Override
//    public int getID() {
//        return 0;
//    }
//
//    @Override
//    public void init() {
//        System.out.println("--------------------- INITT ");
//    }
//
//    @Override
//    public void save() {
//        System.out.println("-0-0-0-0-0-0-0-0-0 ::::: SAVE");
//    }
//
//    @Override
//    public EntityManager getEntityManager() {
//        return null;
//    }
//
//    @Override
//    public <X extends RawEntity<Integer>> Class<X> getEntityType() {
//        return null;
//    }
//
//    @Override
//    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
//
//    }
//
//    @Override
//    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
//
//    }
}
