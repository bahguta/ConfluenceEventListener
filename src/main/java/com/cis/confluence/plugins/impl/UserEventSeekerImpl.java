package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.user.ConfluenceUser;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.utils.EventSeekerManager;

public class UserEventSeekerImpl {

    private final EventSeekerManager eventSeekerManager;
    private EventUser user;
    private ConfluencerParticipate confluencerParticipate;

    public UserEventSeekerImpl(EventUser user) {
        this.user = user;
        this.eventSeekerManager = new EventSeekerManager();
        //confluencerParticipate = new ConfluencerParticipate();
    }



}
