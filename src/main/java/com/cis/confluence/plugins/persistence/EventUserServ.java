package com.cis.confluence.plugins.persistence;

import com.cis.confluence.plugins.dto.EventUser;
import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface EventUserServ extends Entity {

    EventUser getEventUser();
    void addEventUser(EventUser user);


}
