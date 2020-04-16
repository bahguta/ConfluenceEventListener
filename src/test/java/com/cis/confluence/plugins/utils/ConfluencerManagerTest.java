package com.cis.confluence.plugins.utils;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistence;
import com.cis.confluence.plugins.persistence.ConfluencerPersistenceImpl;
import com.cis.confluence.plugins.persistence.EventUserServ;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.test.converters.NameConverters;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.DatabaseUpdater;
import net.java.ao.test.jdbc.DerbyEmbedded;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import com.atlassian.activeobjects.test.TestActiveObjects;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(ActiveObjectsJUnitRunner.class)
@Data(ConfluencerManagerTest.ConfluencerManagerTestDatabaseUpdater.class)
//@Jdbc(DerbyEmbedded.class)
//@NameConverters
class ConfluencerManagerTest {

    private ConfluencerManager objectToTest;

    private EntityManager entityManager;

    private ActiveObjects ao;

    private ConfluencerPersistenceImpl confluencerPersistence;


    @Test
    void findUsers() {
        EventUser eventUser = new EventUser(mock(ConfluenceUser.class));

        when(eventUser.getName()).thenReturn("someName");
        when(eventUser.getUser().getEmail()).thenReturn("some@email");
        //when(objectToTest.getPersistence().getAll()).thenReturn(confluencerPersistence.getAll());

        objectToTest.getPersistence().save(eventUser);

        System.out.println(" :: " + ao.find(EventUserServ.class, Query.select().where("NAME = ?", eventUser.getName())));

        assertEquals(1, ao.find(EventUser.class).length);

        //verify(objectToTest.getPersistence()).getAll();
    }


    @Test
    void addUser(){
        EventUser eventUser = new EventUser(mock(ConfluenceUser.class));

        when(eventUser.getName()).thenReturn("someName");
        when(eventUser.getUser().getEmail()).thenReturn("someEmail");

        objectToTest.addUser(eventUser);

        assertTrue(objectToTest.getList().size() > 0);
    }

    @Test
    void getSortedList() {
        EventUser eventUser = new EventUser(mock(ConfluenceUser.class));
        eventUser.addSpace();
        EventUser eventUser2 = new EventUser(mock(ConfluenceUser.class));
        eventUser2.addSpace();
        eventUser2.addSpace();

        when(eventUser.getName()).thenReturn("someName");
        when(eventUser.getUser().getEmail()).thenReturn("someEmail");

        when(eventUser2.getName()).thenReturn("someName2");
        when(eventUser2.getUser().getEmail()).thenReturn("someEmail2");

        objectToTest.addUser(eventUser);
        objectToTest.addUser(eventUser2);

        boolean result = objectToTest.getSortedList().get(0).getSpace() > objectToTest.getSortedList().get(1).getSpace();

        assertTrue(result);
    }

    @Test
    public void getFirst(){
        EventUser eventUser = new EventUser(mock(ConfluenceUser.class));

        when(eventUser.getName()).thenReturn("someName");
        when(eventUser.getUser().getEmail()).thenReturn("someEmail");

        objectToTest.addUser(eventUser);

        assertEquals(1, objectToTest.getList().size());
    }

    @BeforeEach
    public void setUp(){
        entityManager = mock(EntityManager.class);
        assertNotNull(entityManager);

        ao = new TestActiveObjects(entityManager);
        confluencerPersistence = new ConfluencerPersistenceImpl(ao);
        //confluencerPersistence = mock(ConfluencerPersistenceImpl.class);

        EventSeekerManager eventSeekerManager = mock(EventSeekerManager.class);
        objectToTest = new ConfluencerManager(eventSeekerManager);
        ConfluencerPersistence persistence = mock(ConfluencerPersistenceImpl.class);
        objectToTest.setPersistence(persistence);

    }

    public static final class ConfluencerManagerTestDatabaseUpdater implements DatabaseUpdater
    {
        @Override
        public void update(EntityManager entityManager) throws Exception
        {
            entityManager.migrate(EventUser.class);
        }
    }
}