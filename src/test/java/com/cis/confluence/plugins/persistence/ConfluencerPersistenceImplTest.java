package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import com.atlassian.confluence.user.ConfluenceUserImpl;
import com.cis.confluence.plugins.dto.EventUser;
import com.google.common.collect.ImmutableMap;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.DatabaseUpdater;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(ActiveObjectsJUnitRunner.class)
@Data(ConfluencerPersistenceImplTest.ConfluencerPersistenceImplTestDataBaseUpdater.class)
public class ConfluencerPersistenceImplTest {

    private ConfluencerPersistenceImpl objectToTest;

    private EntityManager entityManager;

    private ActiveObjects ao;

    @Test
    public void getAll() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));
        EventUser eventUser2 = new EventUser(new ConfluenceUserImpl("someUsername2", "someFullname2", "someEmail2"));

        EventUserServ eventUserServ = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("NAME", eventUser.getName()));
        eventUserServ.save();
        EventUserServ eventUserServ2 = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("NAME", eventUser2.getName()));
        eventUserServ2.save();

        assertEquals(2, ao.find(EventUserServ.class).length);
    }

    @Test
    public void save() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        EventUserServ eventUserServ = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("NAME", eventUser.getName()));
        eventUserServ.save();

        assertEquals(1, ao.find(EventUserServ.class).length);


    }

    @Test
    public void remove() {
        EventUser eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        EventUserServ eventUserServ = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("NAME", eventUser.getName()));
        eventUserServ.save();

        assertEquals(1, ao.find(EventUserServ.class).length);

        ao.deleteWithSQL(EventUserServ.class, "NAME = ?", eventUser.getName());

        assertEquals(0, ao.find(EventUserServ.class, Query.select().where("NAME = ?", eventUser.getName())).length);
    }

    @Before
    public void setUp() {
        assertNotNull(entityManager);

        ao = new TestActiveObjects(entityManager);
        objectToTest = new ConfluencerPersistenceImpl(ao);
    }

    @After
    public void tearDown() {

    }

    public static final class ConfluencerPersistenceImplTestDataBaseUpdater implements DatabaseUpdater{
        @Override
        public void update(EntityManager entityManager) throws Exception {
            entityManager.migrate(EventUserServ.class);


        }
    }
}