package com.cis.confluence.plugins.utils;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.confluence.user.ConfluenceUser;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistence;
import com.cis.confluence.plugins.persistence.ConfluencerPersistenceImpl;
import com.cis.confluence.plugins.persistence.EventUserServ;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.DatabaseUpdater;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
//import org.junit.Test;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import com.atlassian.activeobjects.test.TestActiveObjects;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(ActiveObjectsJUnitRunner.class)
@Data(ConfluencerManagerTest.ConfluencerManagerTestDatabaseUpdater.class)
//@Jdbc(DerbyEmbedded.class)
//@NameConverters
@Ignore
public class ConfluencerManagerTest {

    private ConfluencerManager objectToTest;

    private EntityManager entityManager;

    private ActiveObjects ao;

    @Mock
    private ConfluencerPersistence persistence;


    @Test
    void findUsers() {
//        EventUser eventUser = new EventUser(mock(ConfluenceUser.class));
//
//        when(eventUser.getName()).thenReturn("someName");
//        when(eventUser.getUser().getEmail()).thenReturn("some@email");
//        //when(objectToTest.getPersistence()).thenReturn(new ConfluencerPersistenceImpl(mock(ActiveObjects.class)));
//        //System.out.println(new ConfluencerPersistenceImpl(mock(TestActiveObjects.class)));
//        //when(objectToTest.getPersistence()).thenReturn(new ConfluencerPersistenceImpl(new TestActiveObjects(entityManager)));
//
//                System.out.println(" :: " + entityManager.toString());
//        objectToTest.getPersistence().save(eventUser);
//
//
//        //assertEquals(1, ao.find(EventUser.class).length);
//        System.out.println("::::: " + objectToTest.getPersistence().getAll());
//        assertEquals(1, objectToTest.getPersistence().getAll().size());
//        //verify(objectToTest.getPersistence()).getAll();
    }


    @Test
    void addUser() {
//        EventUser eventUser = new EventUser(mock(ConfluenceUser.class));
//
//        when(eventUser.getName()).thenReturn("someName");
//        when(eventUser.getUser().getEmail()).thenReturn("someEmail");
//
//        objectToTest.addUser(eventUser);
//
//        assertTrue(objectToTest.getList().size() > 0);
    }

    @Test
    void getSortedList() {
//        EventUser eventUser = new EventUser(mock(ConfluenceUser.class));
//        eventUser.addSpace();
//        EventUser eventUser2 = new EventUser(mock(ConfluenceUser.class));
//        eventUser2.addSpace();
//        eventUser2.addSpace();
//
//        when(eventUser.getName()).thenReturn("someName");
//        when(eventUser.getUser().getEmail()).thenReturn("someEmail");
//
//        when(eventUser2.getName()).thenReturn("someName2");
//        when(eventUser2.getUser().getEmail()).thenReturn("someEmail2");
//
//        objectToTest.addUser(eventUser);
//        objectToTest.addUser(eventUser2);
//
//        boolean result = objectToTest.getSortedList().get(0).getSpace() > objectToTest.getSortedList().get(1).getSpace();
//
//        assertTrue(result);
    }

    @Test
    public void getFirst() {
//        EventUser eventUser = new EventUser(mock(ConfluenceUser.class));
//
//        when(eventUser.getName()).thenReturn("someName");
//        when(eventUser.getUser().getEmail()).thenReturn("someEmail");
//
//        objectToTest.addUser(eventUser);
//
//        assertEquals(1, objectToTest.getList().size());
    }

    @BeforeEach
    public void setUp() {

//        assertNotNull(entityManager);
//
//        EventSeekerManager eventSeekerManager = mock(EventSeekerManager.class);
//        objectToTest = new ConfluencerManager(eventSeekerManager);
//        //persistence = mock(ConfluencerPersistence.class);
//
//        //entityManager = mock(EntityManager.class);
//        ao = new TestActiveObjects(entityManager);
//        persistence = new ConfluencerPersistenceImpl(ao);
//        //persistence = mock(ConfluencerPersistence.class);
//
//        objectToTest.setPersistence(persistence);

    }

    public static final class ConfluencerManagerTestDatabaseUpdater implements DatabaseUpdater {
        @Override
        public void update(EntityManager entityManager) throws Exception {
            entityManager.migrate(EventUserServ.class);
            //em.migrate(Credentials.class);

            final EventUserServ eventUserServ = entityManager.create(EventUserServ.class);
//            eventUserServ.
//
//                    setUrl("https://www.zxc.com");
//            credentials.setAuthToken("f1%Z8Z3YPxQ01NhBMj6&");
            EventUser eventUser = new EventUser(mock(ConfluenceUser.class));

            when(eventUser.getName()).thenReturn("someName");
            when(eventUser.getUser().getEmail()).thenReturn("some@email");
            eventUser.save();
            eventUserServ.save();
        }
    }
}