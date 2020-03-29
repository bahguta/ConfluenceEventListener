package com.cis.confluence.plugins.persistence;

import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface EventUserServ extends Entity {

    boolean isParticipate();
    void setParticipate(boolean participate);

    String getIconPath();
    void setIconPath(String icon);

    String getName();
    void setName(String name);

    String getFullName();
    void setFullName(String Fullname);

    String getKeyString();
    void setKeyString(String key);

    String getEmail();
    void setEmail(String email);

    int getSpace();
    void setSpace(int space);

    int getPage();
    void setPage(int page);

    int getBlog();
    void setBlog(int blog);

    int getComment();
    void setComment(int comment);

    int getLike();
    void setLike(int like);


}
