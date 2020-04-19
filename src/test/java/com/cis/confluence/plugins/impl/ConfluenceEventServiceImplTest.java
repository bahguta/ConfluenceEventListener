package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.core.ContentEntityObject;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostCreateEvent;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostRemoveEvent;
import com.atlassian.confluence.event.events.content.comment.CommentCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentRemoveEvent;
import com.atlassian.confluence.event.events.content.page.PageCreateEvent;
import com.atlassian.confluence.event.events.content.page.PageRemoveEvent;
import com.atlassian.confluence.event.events.like.LikeCreatedEvent;
import com.atlassian.confluence.event.events.like.LikeRemovedEvent;
import com.atlassian.confluence.event.events.space.SpaceCreateEvent;
import com.atlassian.confluence.event.events.space.SpaceRemoveEvent;
import com.atlassian.confluence.pages.BlogPost;
import com.atlassian.confluence.pages.Comment;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.user.ConfluenceUserImpl;
import com.atlassian.core.util.ProgressMeter;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistenceImpl;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import com.cis.confluence.plugins.utils.EventSeekerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ConfluenceEventServiceImplTest {

    private ConfluenceEventServiceImpl objectToTest;

    private ConfluencerManager confluencerManager;

    private EventUser eventUser;

    @Mock
    private EventSeekerManager eventSeekerManager;

    @Test
    void handleEventSpaceCreate() {
        Space space = new Space("someKey");
        space.setCreator(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventSpaceCreate(new SpaceCreateEvent("source", space));

        assertEquals(1, eventUser.getSpace());

    }

    @Test
    void handleEventSpaceRemove() {
        Space space = new Space("someKey");
        space.setCreator(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventSpaceCreate(new SpaceCreateEvent("source", space));

        assertEquals(1, eventUser.getSpace());

        objectToTest.handleEventSpaceRemove(new SpaceRemoveEvent("source", space, mock(ProgressMeter.class)));

        assertEquals(0, eventUser.getSpace());
    }

    @Test
    void handleEventPageCreate() {
        Page page = new Page();
        page.setCreator(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventPageCreate(new PageCreateEvent("source", page));

        assertEquals(1, eventUser.getPage());
    }

    @Test
    void handleEventPageRemove() {
        Page page = new Page();
        page.setCreator(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventPageCreate(new PageCreateEvent("source", page));

        assertEquals(1, eventUser.getPage());

        objectToTest.handleEventPageRemove(new PageRemoveEvent("source", page));

        assertEquals(0, eventUser.getPage());
    }

    @Test
    void handleEventBlogCreate() {
        BlogPost blog = new BlogPost();
        blog.setCreator(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventBlogCreate(new BlogPostCreateEvent("source", blog));

        assertEquals(1, eventUser.getBlog());
    }

    @Test
    void handleEventBlogRemove() {
        BlogPost blog = new BlogPost();
        blog.setCreator(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventBlogCreate(new BlogPostCreateEvent("source", blog));

        assertEquals(1, eventUser.getBlog());

        objectToTest.handleEventBlogRemove(new BlogPostRemoveEvent("source", blog));

        assertEquals(0, eventUser.getBlog());
    }

    @Test
    void handleEventCommentCreate() {
        Comment comment = new Comment();
        comment.setCreator(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventCommentCreate(new CommentCreateEvent("source", comment));

        assertEquals(1, eventUser.getComment());
    }

    @Test
    void handleEventCommentRemove() {
        Comment comment = new Comment();
        comment.setCreator(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventCommentCreate(new CommentCreateEvent("source", comment));

        assertEquals(1, eventUser.getComment());

        objectToTest.handleEventCommentRemove(new CommentRemoveEvent("source", comment, eventUser.getUser()));

        assertEquals(0, eventUser.getComment());
    }

    @Test
    void handleEventLikeCreate() {
        ContentEntityObject contentEntityObject = mock(ContentEntityObject.class);

        when(contentEntityObject.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventLikeCreate(new LikeCreatedEvent("source", eventUser.getUser(), contentEntityObject));

        //esperamos 2 porque añade un like para el liker y otro para liked
        assertEquals(2, eventUser.getLike());
    }

    @Test
    void handleEventLikeRemove() {
        ContentEntityObject contentEntityObject = mock(ContentEntityObject.class);

        when(contentEntityObject.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventLikeCreate(new LikeCreatedEvent("source", eventUser.getUser(), contentEntityObject));

        //esperamos 2 porque añade un like para el liker y otro para liked
        assertEquals(2, eventUser.getLike());

        objectToTest.handleEventLikeRemove(new LikeRemovedEvent("source", eventUser.getUser(), contentEntityObject));

        assertEquals(0, eventUser.getLike());

    }

    @BeforeEach
    public void setUp() {
        objectToTest = new ConfluenceEventServiceImpl();
        ConfluencerPersistenceImpl persistence = mock(ConfluencerPersistenceImpl.class);

        eventSeekerManager = mock(EventSeekerManager.class);

        confluencerManager = new ConfluencerManager();
        confluencerManager.setPersistence(persistence);
        confluencerManager.setEventSeekerManager(eventSeekerManager);

        objectToTest.setConfluencerManager(confluencerManager);

        eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

    }
}