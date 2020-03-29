package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.like.Like;
import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.*;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.dto.EventUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;
import java.util.List;

public class EventSeekerManager {

    private final Logger logger = LoggerFactory.getLogger(EventSeekerManager.class);

    private SpaceManager spaceManager;
    private PageManager pageManager;
    private CommentManager commentManager;
    private LikeManager likeManager;

    private ConfluencerManager confluencerManager;

    public void setConfluencerManager(ConfluencerManager confluencerManager) {
        this.confluencerManager = confluencerManager;
    }

    public EventSeekerManager() {
        spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");
        pageManager = (PageManager) ContainerManager.getComponent("pageManager");
        commentManager = (CommentManager) ContainerManager.getComponent("commentManager");
        likeManager = (LikeManager) ContainerManager.getComponent("likeManager");
    }

    public int addNumSpacesForUser(EventUser user) {
        int cont = 0;
        for (Space space : spaceManager.getAllSpaces()) {
            if (null != space.getCreator() && space.getCreator().getEmail().equals(user.getEmail())) {
                cont++;
            }
        }
        return cont;
    }

    public int addNumPagesForUser(EventUser user) {
        int cont = 0;
        for (Page page: getPagesForUser(user)) {
            cont++;
        }
        return cont;
    }

    public int addNumBlogsForUser(EventUser user) {
        int cont = 0;
        for (BlogPost blog: getBlogs(user)) {
            cont++;
        }
        return cont;
    }

    public int addNumCommentsForUser(EventUser user){
        int cont = 0;
        for (Comment comment: getComments(user)) {
            cont++;
        }
        return cont;
    }

    public int addNumLikesForUser(EventUser user){
        int cont = 0;
        for (Like like: getLikes(user)) {
            cont++;
        }
        return cont;
    }

    private List<Space> getSpacesForUser(EventUser user){
        List<Space> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach(s ->{
            if (null != s && null != s.getKey() && null != s.getCreator() && s.getCreator().getEmail().equals(user.getEmail())){
                list.add(s);
            }
        });
        return list;
    }


    private List<Page> getPagesForUser(EventUser user) {
        List<Page> lista = new LinkedList<>();
        for (Space space : spaceManager.getAllSpaces()) {
            pageManager.getPages(space, true).forEach( page -> {
                if (null != page && null != page.getCreator() && page.getCreator().getEmail().equals(user.getEmail())){
                    lista.add(page);
                }
            });
        }
        return lista;
    }

    private List<BlogPost> getBlogs(EventUser user){
        List<BlogPost> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach( s -> {
            pageManager.getBlogPosts(s, true).forEach(blogPost -> {
                if (null != blogPost && blogPost.getCreator().getEmail().equals(user.getEmail())){
                    list.add(blogPost);
                }
            });
        });
        return list;
    }

    private List<Comment> getComments(EventUser user){
        List<Comment> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach( space -> {
            pageManager.getPages(space, true).forEach( page -> {
                page.getComments().forEach(comment -> {
                    if (null != comment && comment.getCreator().getEmail().equals(user.getEmail())){
                        list.add(comment);
                    }
                });
            });
        });
        return list;
    }

    private List<Like> getLikes(EventUser user) {
        List<Like> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach(space -> {

            pageManager.getPages(space, true).forEach( page -> {

                likeManager.getLikes(page.getEntity()).forEach( like -> {
                    if (null != like && like.getUsername().equals(user.getName())){
                        list.add(like);
                    }
                });

                page.getComments().forEach(comment -> {
                    likeManager.getLikes(comment.getContentEntityObject()).forEach( like -> {
                        if (null != like && like.getUsername().equals(user.getName())){
                            list.add(like);
                        }
                    });
                });
            });

            pageManager.getBlogPosts(space, true).forEach( blogPost -> {
                if (null != blogPost && blogPost.getCreator().getEmail().equals(user.getEmail())) {
                    list.addAll(likeManager.getLikes(blogPost.getEntity()));
                }

                blogPost.getComments().forEach(comment -> {
                    likeManager.getLikes(comment.getContentEntityObject()).forEach( like -> {
                        if (null != like && like.getUsername().equals(user.getName())){
                            list.add(like);
                        }
                    });
                });
            });
        });

        return list;
    }

}
