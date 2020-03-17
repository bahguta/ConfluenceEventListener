package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.core.ContentEntityObject;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostCreateEvent;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostInfoViewEvent;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostMovedEvent;
import com.atlassian.confluence.follow.DefaultFollowManager;
import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.BlogPost;
import com.atlassian.confluence.pages.CommentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.DefaultSpaceManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceDescriptionManager;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.confluence.user.actions.ViewUserHistoryAction;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.dto.EventUser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EventSeekerManager {

    private static SpaceManager spaceManager;
    private static PageManager pageManager;
    private static CommentManager commentManager;
    private static LikeManager likeManager;


    public EventSeekerManager() {
        EventSeekerManager.spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");
        EventSeekerManager.pageManager = (PageManager) ContainerManager.getComponent("pageManager");
        EventSeekerManager.commentManager = (CommentManager) ContainerManager.getComponent("commentManager");
        EventSeekerManager.likeManager = (LikeManager) ContainerManager.getComponent("likeManager");
        // this.userAccessor.getClass()
    }

    public void userParticipate(EventUser user) {
        user.setParticipate(user.isParticipate());
        System.out.println("-------- participate :: " + user.toString());

        ConfluencerManager.addUser(user.getEmail(), user.getFullName(), user.getKey().getStringValue(), user.getIcon());
        EventSeekerManager.getNumSpacesForUser(user);
    }

    public static int getNumSpacesForUser(EventUser user) {
        if (null == user) {
            return 0;
        }
        int cont = 0;

        if (null == EventSeekerManager.spaceManager.getAllSpaces() || EventSeekerManager.spaceManager.getAllSpaces().size() <= 0) {
            System.out.println(" ------------------------------ SPACE NULL");
        } else {
            EventSeekerManager.spaceManager.getAllSpaces().forEach(space -> System.out.println(space.getName()));
        }

//        if (null == user.getKey()) {
//            System.out.println("--------------- KEY NBULL");
//        } else {
            System.out.println("--------------------- USER TO STRING  " + user.getKey().toString() + " " + user.toString());
//        }

        for (Space space : EventSeekerManager.spaceManager.getAllSpaces()) {
            if (null != space.getCreator() && null != space.getCreator().getKey() ) {
                System.out.println("=-============================== space Keuy   " + space.getCreator().getKey());
                if (null != user.getKey()){
                    System.out.println("------------------ userkey NE E NULL " + user.getKey());
                    if (space.getCreator().getKey().equals(user.getKey())) {
                        cont++;
                        ConfluencerManager.addSpace(user.getEmail());
                        System.out.println("-0----------------- add spaces " + cont );
                    }
                }else {
                    System.out.println("------------------USER  KEY NULL");

                }
            } else {
                System.out.println("------------------SPACE KEY NULL");
            }
//            if (null == space.getCreator()) {
//                System.out.println("------------------SPACE CREATOR NULL");
//            } else {
//                System.out.println("------------------SPACE CREATOR NE E NULL");
//            }
        }
        return cont;
    }

    public static Map<String, List<Page>> getSpacesForUser(ConfluenceUser user) {
        if (null == user) {
            return new LinkedHashMap<>();
        }
        Map<String, List<Page>> lista = new LinkedHashMap<>();
        for (Space space : EventSeekerManager.spaceManager.getAllSpaces()) {
            if (space.getCreator().getKey().equals(user.getKey())) {
                lista.put(space.getKey(), EventSeekerManager.pageManager.getPages(space, true));
                System.out.println("--------------------- LISTA !!!!");
            }
        }
        return lista;
    }

    public static int getNumPagesForUser(EventUser user) {
        if (null == user) {
            return 0;
        }
        int[] cont = new int[]{1};
        cont[0] = 0;
        getSpacesForUser(user).forEach((key, pages) -> {
            for (Page page : pages) {
                cont[0]++;
            }
        });

        return cont[0];
    }

    public static int getNumBlogsForUser(EventUser currentUser) {
        return 0;

    }

//    public int getNumCommentsForUser(Page page){
//        int cont = 0;
//        commentManager.get
//
//        return cont;
//    }


}
