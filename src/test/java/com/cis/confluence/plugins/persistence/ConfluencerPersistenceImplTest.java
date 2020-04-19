package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.test.TestActiveObjects;
import net.java.ao.EntityManager;
import net.java.ao.test.converters.NameConverters;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.DatabaseUpdater;
import net.java.ao.test.jdbc.DerbyEmbedded;
import net.java.ao.test.jdbc.Jdbc;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(ActiveObjectsJUnitRunner.class)
@Data(ConfluencerPersistenceImplTest.ConfluencerPersistenceImplTestDatabaseUpdater.class)
@Jdbc(DerbyEmbedded.class)
@NameConverters
@Ignore
class ConfluencerPersistenceImplTest {

    private ConfluencerPersistenceImpl objectToTest;

    private EntityManager entityManager;

    @Test
    void getAll() {
    }

    @Test
    void save() {
    }

    @Test
    void remove() {
    }

    @BeforeEach
    public void setUp() {
        //assertNotNull(entityManager);
        //objectToTest = new ConfluencerPersistenceImpl(new TestActiveObjects(entityManager));

    }

    public static final class ConfluencerPersistenceImplTestDatabaseUpdater implements DatabaseUpdater {
        @Override
        public void update(EntityManager entityManager) throws Exception {
            entityManager.migrate(EventUserServ.class);
        }
    }
}