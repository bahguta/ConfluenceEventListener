package com.cis.confluence.plugins.dto;

import com.atlassian.confluence.user.ConfluenceUser;
import com.cis.confluence.plugins.persistence.EventUserServ;
import net.java.ao.EntityManager;
import net.java.ao.Implementation;
import net.java.ao.RawEntity;
import net.java.ao.schema.PrimaryKey;
import net.java.ao.schema.Table;
import javax.validation.constraints.NotNull;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

@Implementation(EventUser.class)
@Table("EventUser")
public class EventUser  implements EventUserServ, Serializable, Comparable<EventUser> {

    private ConfluenceUser user;
    private int space;
    private int page;
    private int blog;
    private int comment;
    private int like;
    private boolean participate;

    public EventUser(ConfluenceUser user) {
        this.user = user;
        this.space = 0;
        this.blog = 0;
        this.page = 0;
        this.comment = 0;
        this.like = 0;
        this.participate = false;
    }

    public boolean isParticipate() {
        return participate;
    }

    public void setParticipate(boolean participate) {
        this.participate = participate;
    }

    public ConfluenceUser getUser() { return user; }

    public String getName() {
        return user.getName();
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
                ", email='" + user.getEmail() + '\'' +
                ", name='" + user.getName() + '\'' +
                ", fullName='" + user.getFullName() + '\'' +
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

        int thisScore = totalScore();
        int userScore = user.getSpace()+user.getPage()+user.getBlog()+user.getComment()+user.getLike();

        if (userScore == thisScore){
            return 0;
        }

        return thisScore < userScore ? 1 : -1;
    }


    /**
     * Algoritmo complicado para calcular el total de los puntos :)
     * @return el total de los puntos
     */
    public int totalScore(){
        return space+page+blog+comment+like;
    }

    @PrimaryKey
    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init() {
    }

    @Override
    public void save() {
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }

    @Override
    public <X extends RawEntity<Integer>> Class<X> getEntityType() {
        return null;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
    }

}
