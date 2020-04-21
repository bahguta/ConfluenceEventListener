package com.cis.confluence.plugins.rest;

import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.ConfluenceUserImpl;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistenceImpl;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import com.cis.confluence.plugins.utils.EventSeekerManager;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ConfluencerRESTTest {

    private ConfluencerREST objectToTest;

    private EventUser eventUser;


    @Test
    public void getUser() {

        objectToTest.getConfluencerManager().addUser(eventUser);

        assertEquals(true, objectToTest.getConfluencerManager().participa(eventUser.getName()));

    }

    @Test
    public void setParticipate() {

        objectToTest.getConfluencerManager().addUser(eventUser);

        assertEquals(true, eventUser.isParticipate());
    }

    @Test
    public void cancelarParticipate() {

        objectToTest.getConfluencerManager().addUser(eventUser);

        assertEquals(true, eventUser.isParticipate());

        objectToTest.getConfluencerManager().cancelarParticipacion(eventUser.getName());

        assertEquals(false, eventUser.isParticipate());
    }

    @Before
    public void setUp() {
        objectToTest = new ConfluencerREST();

        EventSeekerManager eventSeekerManager = new EventSeekerManager();

        eventSeekerManager.setLikeManager(mock(LikeManager.class));
        eventSeekerManager.setPageManager(mock(PageManager.class));
        eventSeekerManager.setSpaceManager(mock(SpaceManager.class));

        ConfluencerManager confluencerManager = new ConfluencerManager();
        confluencerManager.setPersistence(mock(ConfluencerPersistenceImpl.class));
        confluencerManager.setEventSeekerManager(eventSeekerManager);

        objectToTest.setConfluencerManager(confluencerManager);

        eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));
    }

}