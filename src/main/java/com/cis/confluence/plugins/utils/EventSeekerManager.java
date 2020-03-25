package com.cis.confluence.plugins.utils;


import com.atlassian.confluence.like.Like;
import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.*;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.dto.EventUser;
import org.apache.log4j.Logger;
import java.util.LinkedList;
import java.util.List;

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
        EventSeekerManager.addNumSpacesForUser(user);
        EventSeekerManager.addNumPagesForUser(user);
        EventSeekerManager.addNumBlogsForUser(user);
        EventSeekerManager.addNumCommentsForUser(user);
        EventSeekerManager.addNumLikesForUser(user);
    }


    public static void addNumSpacesForUser(EventUser user) {
        for (Space space : EventSeekerManager.spaceManager.getAllSpaces()) {
                ConfluencerManager.addSpace(user.getEmail());
        }
    }

    public static void addNumPagesForUser(EventUser user) {
        for (Page page: getPagesForUser(user)) {
            ConfluencerManager.addPage(user.getEmail());
        }
    }

    public static void addNumBlogsForUser(EventUser user) {
        for (BlogPost blog: getBlogs(user)) {
            ConfluencerManager.addBlog(user.getEmail());
        }
    }

    public static void addNumCommentsForUser(EventUser user){
        for (Comment comment: getComments(user)) {
            ConfluencerManager.addComment(user.getEmail());
        }
    }

    public static void addNumLikesForUser(EventUser user){
        for (Like like: getLikes(user)) {
            ConfluencerManager.addLike(user.getEmail());
        }
    }




    private static List<Space> getSpacesForUser(EventUser user){
        List<Space> list = new LinkedList<>();
        EventSeekerManager.spaceManager.getAllSpaces().forEach(s ->{
            if (null != s && null != s.getKey() && null != s.getCreator() && s.getCreator().getKey().equals(user.getKey())){
                list.add(s);
            }
        });
        return list;
    }


    private static List<Page> getPagesForUser(EventUser user) {
        List<Page> lista = new LinkedList<>();
        for (Space space : EventSeekerManager.spaceManager.getAllSpaces()) {
            EventSeekerManager.pageManager.getPages(space, true).forEach( page -> {
                if (null != page && null != page.getCreator() && page.getCreator().getKey().equals(user.getKey())){
                    lista.add(page);
                }
            });
        }
        return lista;
    }

    private static List<BlogPost> getBlogs(EventUser user){
        List<BlogPost> list = new LinkedList<>();
        getSpacesForUser(user).forEach( s -> {
            pageManager.getBlogPosts(s, true).forEach(blogPost -> {
                if (null != blogPost && blogPost.getCreator().getKey().equals(user.getKey())){
                    list.add(blogPost);
                }
            });
        });
        return list;
    }

    private static List<Comment> getComments(EventUser user){
        List<Comment> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach( space -> {
            pageManager.getPages(space, true).forEach( page -> {
                page.getComments().forEach(comment -> {
                    if (null != comment && comment.getCreator().getKey().equals(user.getKey())){
                        list.add(comment);
                    }
                });
            });
        });
        return list;
    }

    private static List<Like> getLikes(EventUser user) {
        List<Like> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach(space -> {
            pageManager.getPages(space, true).forEach( page -> {
                likeManager.getLikes(page.getEntity()).forEach( like -> {
                    if (like.getUsername().equals(user.getName())){
                        list.add(like);
                    }
                });

                page.getComments().forEach(comment -> {
                    likeManager.getLikes(comment.getContentEntityObject()).forEach( like -> {
                        if (like.getUsername().equals(user.getName())){
                            list.add(like);
                        }
                    });
                });
            });

            pageManager.getBlogPosts(space, true).forEach( blogPost -> {
                likeManager.getLikes(blogPost.getEntity()).forEach(list::add);
            });
        });

        return list;
    }

}
