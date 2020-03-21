package com.cis.confluence.plugins.utils;


import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.CommentManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.dto.EventUser;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EventSeekerManager {

    private static final Logger logger = Logger.getLogger(EventSeekerManager.class);

    private static SpaceManager spaceManager;
    private static PageManager pageManager;
    private static CommentManager commentManager;
    private static LikeManager likeManager;


    public EventSeekerManager() {
        EventSeekerManager.spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");
        EventSeekerManager.pageManager = (PageManager) ContainerManager.getComponent("pageManager");
        EventSeekerManager.commentManager = (CommentManager) ContainerManager.getComponent("commentManager");
        EventSeekerManager.likeManager = (LikeManager) ContainerManager.getComponent("likeManager");
    }

    public static void userParticipate(EventUser user) {
        EventSeekerManager.getNumSpacesForUser(user);
    }

    public static int getNumSpacesForUser(EventUser user) {
        if (null == user) {
            return 0;
        }
        int cont = 0;

        if (EventSeekerManager.spaceManager.getAllSpaces().size() > 0) {
            EventSeekerManager.spaceManager.getAllSpaces().forEach(space -> System.out.println(space.getName()));
        }

        for (Space space : EventSeekerManager.spaceManager.getAllSpaces()) {
            if (null != space.getCreator() && null != space.getCreator().getKey() ) {
                if (null != user.getKey()){
                    if (space.getCreator().getKey().equals(user.getKey())) {
                        cont++;
                        ConfluencerManager.addSpace(user.getEmail());
                    }
                }else {
                    System.out.println("------------------USER  KEY NULL");

                }
            } else {
                System.out.println("------------------SPACE KEY NULL");
            }
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
}
