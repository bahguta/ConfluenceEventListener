package com.cis.confluence.plugins.persistence;

import com.cis.confluence.plugins.dto.EventUser;
import net.java.ao.Entity;
import net.java.ao.OneToOne;
import net.java.ao.Preload;
import net.java.ao.schema.NotNull;

@Preload
public interface EventUserServ extends Entity {

//    @NotNull
//    void setUser(EventUser user);
//
//    @NotNull
//    @OneToOne
//    EventUser getUser();

//    EventUser getEventUser();
//    void addEventUser(EventUser user);

    String getName();
    void setName(String name);

    String getEmail();
    void setEmail(String email);


}
