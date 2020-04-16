package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.SpaceManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


class EventSeekerManagerTest {

    @Mock
    private SpaceManager spaceManager;
    @Mock
    private PageManager pageManager;
    @Mock
    private LikeManager likeManager;

    @Test
    void setConfluencerManager() {
    }

    @Test
    void addNumSpacesForUser() {
    }

    @Test
    void addNumPagesForUser() {
    }

    @Test
    void addNumBlogsForUser() {
    }

    @Test
    void addNumCommentsForUser() {
    }

    @Test
    void addNumLikesForUser() {
    }



    @BeforeClass
    void setUp() {
        spaceManager = Mockito.mock(SpaceManager.class);
        pageManager = Mockito.mock(PageManager.class);
        likeManager = Mockito.mock(LikeManager.class);
    }

}