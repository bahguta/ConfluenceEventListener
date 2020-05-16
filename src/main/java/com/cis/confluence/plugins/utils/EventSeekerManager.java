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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;


/**
 * Clase para recorrer todos los espacios / paginas / blogs / comentarios / likes
 * para buscar los numeros de eventos que se han creado para un usuario
 */
public class EventSeekerManager {

    private final Logger logger = LoggerFactory.getLogger(EventSeekerManager.class);

    private SpaceManager spaceManager;
    private PageManager pageManager;
    private LikeManager likeManager;

    private ConfluencerManager confluencerManager;

    @Autowired
    public EventSeekerManager(ConfluencerManager confluencerManager) {
        this.confluencerManager = confluencerManager;
    }

    public EventSeekerManager() { }

    public void setConfluencerManager(ConfluencerManager confluencerManager) {
        this.confluencerManager = confluencerManager;
    }

    public void initializeManagers() {
        spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");
        pageManager = (PageManager) ContainerManager.getComponent("pageManager");
        likeManager = (LikeManager) ContainerManager.getComponent("likeManager");
    }

    public void setSpaceManager(SpaceManager spaceManager) {
        this.spaceManager = spaceManager;
    }

    public void setPageManager(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    public void setLikeManager(LikeManager likeManager) {
        this.likeManager = likeManager;
    }

    public SpaceManager getSpaceManager() {
        return spaceManager;
    }

    public PageManager getPageManager() {
        return pageManager;
    }

    public LikeManager getLikeManager() {
        return likeManager;
    }


    /**
     * Metodo para añadir 1 punto al usuario por:
     *  -- todos los espacios creados por el
     *  -- todas las paginas creadas por el
     *  -- todos los blogs creados por el
     *  -- todos los comentarios creados por el
     *  -- todos los likes creados por el
     *
     *  El metodo recore
     *  -- todos los espacios
     *  -- las paginas de cada espacio
     *  -- los blogs de cada espacio
     *  -- los comentarios y subcomentarios de cada espacio/pagina/blog
     *  -- los likes de cada espacio/pagina/blog/commentario
     * @param eventUser
     */
    public void searchEvents(EventUser eventUser) {

        int[] numSpaces = {0};
        int[] numPages = {0};
        int[] numBlogs = {0};
        int[] numComments = {0};
        int[] numLikes = {0};

        if (null == spaceManager || null == pageManager || null == likeManager) {
            initializeManagers();
        }


        //recorro todos los espacios
        spaceManager.getAllSpaces().forEach(space -> {
            if (null != space
                    && null != space.getCreator()
                    && null != space.getCreator().getEmail()
                    && space.getCreator().getEmail().equals(eventUser.getUser().getEmail())) {
                numSpaces[0]++;
            }

            //recorro todas las paginas de un espacio
            pageManager.getPages(space, true).forEach(page -> {
                if (null != page
                        && null != page.getCreator()
                        && null != page.getCreator().getEmail()
                        && page.getCreator().getEmail().equals(eventUser.getUser().getEmail())) {
                    numPages[0]++;
                }

                //recorro todos los likes de una pagina
                likeManager.getLikes(page.getEntity()).forEach(like -> {
                    if (null != like
                            && null != like.getUsername()
                            && like.getUsername().equals(eventUser.getName())) {
                        numLikes[0]++;
                    }
                });

                //recorro todos los comentarios de una pagina
                page.getComments().forEach(comment -> {
                    if (null != comment
                            && null != comment.getCreator()
                            && null != comment.getCreator().getEmail()
                            && comment.getCreator().getEmail().equals(eventUser.getUser().getEmail())) {
                        numComments[0]++;
                    }

                    //recorro todos los likes de un comentario
                    likeManager.getLikes(comment.getContentEntityObject()).forEach(like -> {
                        if (null != like
                                && null != like.getUsername()
                                && like.getUsername().equals(eventUser.getName())) {
                            numLikes[0]++;
                        }
                    });
                });
            });


            //recorro todos los blogs de un espacio
            pageManager.getBlogPosts(space, true).forEach(blogPost -> {
                if (null != blogPost
                        && null != blogPost.getCreator()
                        && null != blogPost.getCreator().getEmail()
                        && blogPost.getCreator().getEmail().equals(eventUser.getUser().getEmail())) {
                    numBlogs[0]++;
                }

                likeManager.getLikes(blogPost.getEntity()).forEach(like -> {
                    if (null != like
                            && null != like.getUsername()
                            && like.getUsername().equals(eventUser.getName())){
                        numLikes[0]++;
                    }
                });


                //recorro todos los comments de un blog
                blogPost.getComments().forEach(comment -> {
                    if (null != comment
                            && null != comment.getCreator()
                            && null != comment.getCreator().getEmail()
                            && comment.getCreator().getEmail().equals(eventUser.getUser().getEmail())) {
                        numComments[0]++;
                    }

                    //recorro todos los likes de un comentario
                    likeManager.getLikes(comment.getContentEntityObject()).forEach(like -> {
                        if (null != like
                                && null != like.getUsername()
                                && like.getUsername().equals(eventUser.getName())) {
                            numLikes[0]++;
                        }
                    });
                });
            });
        });


        eventUser.setSpace(eventUser.getSpace() + numSpaces[0]);
        eventUser.setPage(eventUser.getPage() + numPages[0]);
        eventUser.setBlog(eventUser.getBlog() + numBlogs[0]);
        eventUser.setComment(eventUser.getComment() + numComments[0]);
        eventUser.setLike(eventUser.getLike() + numLikes[0]);

    }

}
