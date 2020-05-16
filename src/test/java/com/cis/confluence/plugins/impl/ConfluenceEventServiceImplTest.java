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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ConfluenceEventServiceImplTest {

    private ConfluenceEventServiceImpl objectToTest;

    private ConfluencerManager confluencerManager;

    private EventUser eventUser;

    @Mock
    private EventSeekerManager eventSeekerManager;

    @Test
    public void handleEventSpaceCreate() {
        Space space = mock(Space.class);

        when(space.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventSpaceCreate(new SpaceCreateEvent("source", space));

        assertEquals(1, eventUser.getSpace());

    }

    @Test
    public void handleEventSpaceRemove() {
        Space space = mock(Space.class);

        when(space.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventSpaceCreate(new SpaceCreateEvent("source", space));

        assertEquals(1, eventUser.getSpace());

        objectToTest.handleEventSpaceRemove(new SpaceRemoveEvent("source", space, mock(ProgressMeter.class)));

        assertEquals(0, eventUser.getSpace());
    }

    @Test
    public void handleEventPageCreate() {
        Page page = mock(Page.class);

        when(page.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventPageCreate(new PageCreateEvent("source", page));

        assertEquals(1, eventUser.getPage());
    }

    @Test
    public void handleEventPageRemove() {
        Page page = mock(Page.class);

        when(page.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventPageCreate(new PageCreateEvent("source", page));

        assertEquals(1, eventUser.getPage());

        objectToTest.handleEventPageRemove(new PageRemoveEvent("source", page));

        assertEquals(0, eventUser.getPage());
    }

    @Test
    public void handleEventBlogCreate() {
        BlogPost blog = mock(BlogPost.class);

        when(blog.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventBlogCreate(new BlogPostCreateEvent("source", blog));

        assertEquals(1, eventUser.getBlog());
    }

    @Test
    public void handleEventBlogRemove() {
        BlogPost blog = mock(BlogPost.class);

        when(blog.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventBlogCreate(new BlogPostCreateEvent("source", blog));

        assertEquals(1, eventUser.getBlog());

        objectToTest.handleEventBlogRemove(new BlogPostRemoveEvent("source", blog));

        assertEquals(0, eventUser.getBlog());
    }

    @Test
    public void handleEventCommentCreate() {
        Comment comment = mock(Comment.class);

        when(comment.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventCommentCreate(new CommentCreateEvent("source", comment));

        assertEquals(1, eventUser.getComment());
    }

    @Test
    public void handleEventCommentRemove() {
        Comment comment = mock(Comment.class);

        when(comment.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventCommentCreate(new CommentCreateEvent("source", comment));

        assertEquals(1, eventUser.getComment());

        objectToTest.handleEventCommentRemove(new CommentRemoveEvent("source", comment, eventUser.getUser()));

        assertEquals(0, eventUser.getComment());
    }

    @Test
    public void handleEventLikeCreate() {
        ContentEntityObject contentEntityObject = mock(ContentEntityObject.class);

        when(contentEntityObject.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventLikeCreate(new LikeCreatedEvent("source", eventUser.getUser(), contentEntityObject));

        assertEquals(1, eventUser.getLike());
    }

    @Test
    public void handleEventLikeRemove() {
        ContentEntityObject contentEntityObject = mock(ContentEntityObject.class);

        when(contentEntityObject.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        objectToTest.handleEventLikeCreate(new LikeCreatedEvent("source", eventUser.getUser(), contentEntityObject));

        assertEquals(1, eventUser.getLike());

        objectToTest.handleEventLikeRemove(new LikeRemovedEvent("source", eventUser.getUser(), contentEntityObject));

        assertEquals(0, eventUser.getLike());

    }

    @Before
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