package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.like.Like;
import com.atlassian.confluence.like.LikeManager;
import com.atlassian.confluence.pages.*;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.plugin.osgi.util.OsgiPluginUtil;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.dto.EventUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.inject.Named;
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




    public void userParticipate(EventUser user) {
        addNumSpacesForUser(user);
        addNumPagesForUser(user);
        addNumBlogsForUser(user);
        addNumCommentsForUser(user);
        addNumLikesForUser(user);
    }


    public void addNumSpacesForUser(EventUser user) {
        if (null == confluencerManager){
            System.out.println("---=-==-==- :: confluencerManager null ");
        }
        if ( null == confluencerManager.getList()) {
            System.out.println("-=-=-=-=-=-== :: getList() nullll");
        }
        confluencerManager.getList().forEach( u -> System.out.println("-------u :: " + u.toString()));

        for (Space space : spaceManager.getAllSpaces()) {
            if (space.getCreator().getKey().equals(user.getKey())) {
                System.out.println("------------------user ::: " + user.toString());
                confluencerManager.addSpace(user.getEmail());
            }
        }
    }

    public void addNumPagesForUser(EventUser user) {
        for (Page page: getPagesForUser(user)) {
            confluencerManager.addPage(user.getEmail());
        }
    }

    public void addNumBlogsForUser(EventUser user) {
        for (BlogPost blog: getBlogs(user)) {
            confluencerManager.addBlog(user.getEmail());
        }
    }

    public void addNumCommentsForUser(EventUser user){
        for (Comment comment: getComments(user)) {
            confluencerManager.addComment(user.getEmail());
        }
    }

    public void addNumLikesForUser(EventUser user){
        for (Like like: getLikes(user)) {
            confluencerManager.addLike(user.getEmail());
        }
    }




    private List<Space> getSpacesForUser(EventUser user){
        List<Space> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach(s ->{
            if (null != s && null != s.getKey() && null != s.getCreator() && s.getCreator().getKey().equals(user.getKey())){
                list.add(s);
            }
        });
        return list;
    }


    private List<Page> getPagesForUser(EventUser user) {
        List<Page> lista = new LinkedList<>();
        for (Space space : spaceManager.getAllSpaces()) {
            pageManager.getPages(space, true).forEach( page -> {
                if (null != page && null != page.getCreator() && page.getCreator().getKey().equals(user.getKey())){
                    lista.add(page);
                }
            });
        }
        return lista;
    }

    private List<BlogPost> getBlogs(EventUser user){
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

    private List<Comment> getComments(EventUser user){
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
                if (null != blogPost && blogPost.getCreator().getKey().equals(user.getKey())) {
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
