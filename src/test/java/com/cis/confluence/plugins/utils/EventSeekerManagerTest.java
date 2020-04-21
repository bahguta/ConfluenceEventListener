package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.like.Like;
import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.*;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.ConfluenceUserImpl;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class EventSeekerManagerTest {

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
    void addNumSpacesForUser() {
        Space space = mock(Space.class);

        List<Space> list = new LinkedList<>();
        list.add(space);

        when(objectToTest.getSpaceManager().getAllSpaces()).thenReturn(list);
        when(space.getCreator()).thenReturn(eventUser.getUser());

        confluencerManager.addUser(eventUser);

        assertEquals(1, eventUser.getSpace());
    }

    @Test
    void addNumPagesForUser() {
        Space space = mock(Space.class);

        List<Space> list = new LinkedList<>();
        list.add(space);

        Page page = new Page();
        page.setCreator(eventUser.getUser());

        List<Page> listPages = new LinkedList<>();
        listPages.add(page);

        when(objectToTest.getSpaceManager().getAllSpaces()).thenReturn(list);
        when(objectToTest.getPageManager().getPages(space, true)).thenReturn(listPages);

        confluencerManager.addUser(eventUser);

        assertEquals(1, eventUser.getPage());
    }

    @Test
    void addNumBlogsForUser() {
        Space space = mock(Space.class);
        List<Space> list = new LinkedList<>();
        list.add(space);

        BlogPost blog = new BlogPost();
        blog.setCreator(eventUser.getUser());

        List<BlogPost> listBlogs = new LinkedList<>();
        listBlogs.add(blog);

        when(objectToTest.getSpaceManager().getAllSpaces()).thenReturn(list);
        when(objectToTest.getPageManager().getBlogPosts(space, true)).thenReturn(listBlogs);

        System.out.println(eventUser.toString());
        confluencerManager.addUser(eventUser);
        System.out.println(eventUser.toString());

        assertEquals(1, eventUser.getBlog());

    }

    @Test
    void addNumCommentsForUser() {
        Space space = mock(Space.class);

        List<Space> list = new LinkedList<>();
        list.add(space);

        Comment comment = new Comment();
        comment.setCreator(eventUser.getUser());

        BlogPost blog = new BlogPost();
        blog.setCreator(eventUser.getUser());

        List<BlogPost> listBlogs = new LinkedList<>();
        listBlogs.add(blog);

        when(objectToTest.getSpaceManager().getAllSpaces()).thenReturn(list);
        when(objectToTest.getPageManager().getBlogPosts(space, true)).thenReturn(listBlogs);

        confluencerManager.addUser(eventUser);

        assertEquals(1, eventUser.getBlog());
    }

    @Test
    void addNumLikesForUser() {
        Space space = mock(Space.class);

        List<Space> list = new LinkedList<>();
        list.add(space);

        Like like = new Like(1, eventUser.getName(), new Date());
        List<Like> listLikes = new LinkedList<>();
        listLikes.add(like);

        BlogPost blog = new BlogPost();
        blog.setCreator(eventUser.getUser());

        List<BlogPost> listBlogs = new LinkedList<>();
        listBlogs.add(blog);

        when(objectToTest.getSpaceManager().getAllSpaces()).thenReturn(list);
        when(objectToTest.getPageManager().getBlogPosts(space, true)).thenReturn(listBlogs);
        when(objectToTest.getLikeManager().getLikes(blog.getEntity())).thenReturn(listLikes);

        confluencerManager.addUser(eventUser);

        assertEquals(1, eventUser.getLike());
    }



    @BeforeEach
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