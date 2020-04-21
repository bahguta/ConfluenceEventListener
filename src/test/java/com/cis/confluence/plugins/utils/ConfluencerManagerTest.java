package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.ConfluenceUserImpl;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConfluencerManagerTest {

    @Mock
    private ConfluencerManager objectToTest;

    @BeforeEach
    public void setUp() {
        EventSeekerManager eventSeekerManager = new EventSeekerManager();
        objectToTest = new ConfluencerManager();

        SpaceManager spaceManager = mock(SpaceManager.class);
        PageManager pageManager = mock(PageManager.class);
        LikeManager likeManager = mock(LikeManager.class);

        eventSeekerManager.setSpaceManager(spaceManager);
        eventSeekerManager.setPageManager(pageManager);
        eventSeekerManager.setLikeManager(likeManager);

        objectToTest.setPersistence(mock(ConfluencerPersistenceImpl.class));
        objectToTest.setEventSeekerManager(eventSeekerManager);

    }

    @Test
    void getList() {
        objectToTest.addUser(new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail")));

        assertEquals(1, objectToTest.getList().size());
    }

    @Test
    void getSortedList() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));
        EventUser eventUser1 = new EventUser(new ConfluenceUserImpl("someUsername1", "someFullname1", "someEmail1"));

        objectToTest.addUser(eventUser);
        objectToTest.addUser(eventUser1);

        //añado 2 putnos al segundo usuario
        objectToTest.addSpace(eventUser1.getUser().getEmail());
        objectToTest.addSpace(eventUser1.getUser().getEmail());

        //compruebo que el primer usuario tiene mas puntos (se sorteado la lista, el segundo usuario ha ido primero)
        assertTrue(objectToTest.getSortedList().get(0).getSpace() > objectToTest.getSortedList().get(1).getSpace());
    }

    @Test
    void getFirst() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));
        EventUser eventUser1 = new EventUser(new ConfluenceUserImpl("someUsername1", "someFullname1", "someEmail1"));

        objectToTest.addUser(eventUser);
        objectToTest.addUser(eventUser1);

        //añado 2 putnos al segundo usuario
        objectToTest.addSpace(eventUser1.getUser().getEmail());
        objectToTest.addSpace(eventUser1.getUser().getEmail());

        //compruebo que el segundo usuario ha pasado primero
        assertEquals(eventUser1, objectToTest.getFirst());
    }

    @Test
    void sortedListWithoutFirst() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));
        EventUser eventUser1 = new EventUser(new ConfluenceUserImpl("someUsername1", "someFullname1", "someEmail1"));

        objectToTest.addUser(eventUser);
        objectToTest.addUser(eventUser1);

        //añado 2 putnos al segundo usuario
        objectToTest.addSpace(eventUser1.getUser().getEmail());
        objectToTest.addSpace(eventUser1.getUser().getEmail());

        //compruebo que no existe el primer usuario en la lista
        assertFalse(objectToTest.sortedListWithoutFirst().contains(eventUser1));
    }

    @Test
    void participa() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        assertTrue(objectToTest.participa(eventUser.getName()));
    }

    @Test
    void setParticipa() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        assertTrue(eventUser.isParticipate());
    }

    @Test
    void cancelarParticipacion() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        assertTrue(eventUser.isParticipate());

        objectToTest.cancelarParticipacion(eventUser.getName());

        assertFalse(eventUser.isParticipate());
    }

    @Test
    void containsUser() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        assertTrue(objectToTest.getList().contains(eventUser));
    }

    @Test
    void addUser() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        assertTrue(objectToTest.getList().contains(eventUser));
    }

    @Test
    void addSpace() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addSpace(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getSpace());
    }

    @Test
    void addPage() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addPage(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getPage());
    }

    @Test
    void addBlog() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addBlog(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getBlog());
    }

    @Test
    void addComment() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addComment(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getComment());
    }

    @Test
    void addLike() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addLike(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getLike());
    }

    @Test
    void restSpace() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addSpace(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getSpace());

        objectToTest.restSpace(eventUser.getUser().getEmail());

        assertEquals(0, eventUser.getSpace());
    }

    @Test
    void restPage() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addPage(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getPage());

        objectToTest.restPage(eventUser.getUser().getEmail());

        assertEquals(0, eventUser.getPage());
    }

    @Test
    void restBlog() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addBlog(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getBlog());

        objectToTest.restBlog(eventUser.getUser().getEmail());

        assertEquals(0, eventUser.getBlog());
    }

    @Test
    void restComment() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addComment(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getComment());

        objectToTest.restComment(eventUser.getUser().getEmail());

        assertEquals(0, eventUser.getComment());
    }

    @Test
    void restLike() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        objectToTest.addUser(eventUser);

        objectToTest.addLike(eventUser.getUser().getEmail());

        assertEquals(1, eventUser.getLike());

        objectToTest.restLike(eventUser.getUser().getEmail());

        assertEquals(0, eventUser.getLike());
    }
}