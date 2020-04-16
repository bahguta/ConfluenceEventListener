package com.cis.confluence.plugins.dto;

import com.atlassian.confluence.user.ConfluenceUser;
import com.cis.confluence.plugins.utils.ConfluencerManager;
//import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

class EventUserTest {

    EventUser objectToTest;
    @Mock
    ConfluenceUser user;
    @Autowired
    ConfluencerManager confluencerManager;



    @Test
    void restSpace() {
        user = mock(ConfluenceUser.class);
        objectToTest = new EventUser(user);
        confluencerManager = mock(ConfluencerManager.class);

        when(objectToTest.getUser().getEmail()).thenReturn("some@email");
        when(confluencerManager.containsUser("some@email")).thenReturn(true);

        //confluencerManager.addUser("some@email");
        System.out.println(confluencerManager.getFirst());
        confluencerManager.getList().forEach(u -> System.out.println(u.toString()));
        //verify(confluencerManager).containsUser("some@email");
        System.out.println("before :: " + objectToTest.toString());

        System.out.println("after :: " + objectToTest.getSpace());
        objectToTest.addSpace();
        confluencerManager.addSpace("some@email");
        //confluencerManager.restSpace("some@email");
        //verify(confluencerManager).restSpace("some@email");
        confluencerManager.restSpace("some@email");
        assert objectToTest.getSpace() == 1;

        System.out.println("before :: " + objectToTest.toString());
    }

    @BeforeClass
    void setUp() {
        user = mock(ConfluenceUser.class);
        objectToTest = new EventUser(user);
        confluencerManager = mock(ConfluencerManager.class);
    }

//    @BeforeEach
//    void setUp() {
//    }

//    @org.junit.jupiter.api.Test
//    void getSpace() {
//    }
}