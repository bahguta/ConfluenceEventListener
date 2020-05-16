package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.like.Like;
import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.*;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.ConfluenceUserImpl;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistence;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class EventSeekerManagerTest {

    private EventSeekerManager objectToTest;

    private EventUser eventUser;

    @Mock
    private ConfluencerManager confluencerManager;

    @Mock
    private SpaceManager spaceManager;
    @Mock
    private PageManager pageManager;
    @Mock
    private LikeManager likeManager;


    @Test
    public void searchEventsTest(){
        Space space = new Space();
        space.setCreator(eventUser.getUser());
        List<Space> listSpaces = new LinkedList<>();
        listSpaces.add(space);

        Page page = new Page();
        page.setCreator(eventUser.getUser());
        List<Page> listPages = new LinkedList<>();
        listPages.add(page);

        BlogPost blog = new BlogPost();
        blog.setCreator(eventUser.getUser());
        List<BlogPost> listBlogs = new LinkedList<>();
        listBlogs.add(blog);

        Comment comment = new Comment();
        comment.setCreator(eventUser.getUser());
        List<Comment> listComments = new LinkedList<>();
        listComments.add(comment);
        page.setComments(listComments);

        Like like = new Like(1, eventUser.getName(), new Date());
        List<Like> listLikes = new LinkedList<>();
        listLikes.add(like);

        when(objectToTest.getSpaceManager().getAllSpaces()).thenReturn(listSpaces);
        when(objectToTest.getPageManager().getPages(space, true)).thenReturn(listPages);
        when(objectToTest.getPageManager().getBlogPosts(space, true)).thenReturn(listBlogs);
        when(objectToTest.getLikeManager().getLikes(blog.getEntity())).thenReturn(listLikes);

        confluencerManager.addUser(eventUser);

        assertEquals(1, eventUser.getSpace());
        assertEquals(1, eventUser.getPage());
        assertEquals(1, eventUser.getBlog());
        assertEquals(1, eventUser.getComment());
        assertEquals(1, eventUser.getLike());
    }


    @Before
    public void setUp() {
        objectToTest = new EventSeekerManager();

        confluencerManager = new ConfluencerManager();
        confluencerManager.setPersistence(mock(ConfluencerPersistence.class));
        confluencerManager.setEventSeekerManager(objectToTest);

        eventUser = new EventUser(new ConfluenceUserImpl("someUsername", "someFullname", "someEmail"));

        spaceManager = Mockito.mock(SpaceManager.class);
        pageManager = Mockito.mock(PageManager.class);
        likeManager = Mockito.mock(LikeManager.class);

        objectToTest.setSpaceManager(spaceManager);
        objectToTest.setPageManager(pageManager);
        objectToTest.setLikeManager(likeManager);
    }

}