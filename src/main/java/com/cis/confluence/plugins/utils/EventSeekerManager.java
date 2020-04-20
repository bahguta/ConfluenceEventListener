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


/**
 * Clase para recorrer todos los espacios / paginas / blogs / comentarios / likes
 * para buscar los numeros de eventos que se han creado para un usuario
 */
public class EventSeekerManager {

    private final Logger logger = LoggerFactory.getLogger(EventSeekerManager.class);

    private SpaceManager spaceManager ;
    private PageManager pageManager ;
    private LikeManager likeManager ;

    private ConfluencerManager confluencerManager;

    public void setConfluencerManager(ConfluencerManager confluencerManager) {
        this.confluencerManager = confluencerManager;
    }

    public EventSeekerManager() {
//        spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");
//        pageManager = (PageManager) ContainerManager.getComponent("pageManager");
//        likeManager = (LikeManager) ContainerManager.getComponent("likeManager");
    }

    public void initializeManagers(){
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
     * Metodo para buscar cuantos espacios ha creado un usuario
     * @param user el usuario al que pertenecen los espacios
     * @return el numero de espacios
     */
    public int addNumSpacesForUser(EventUser user) {
        if (null == spaceManager){
            initializeManagers();
        }
        int cont = 0;
        for (Space space : spaceManager.getAllSpaces()) {
            if (null != space.getCreator() && space.getCreator().getEmail().equals(user.getUser().getEmail())) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Metodo para buscar cuantas paginas ha creado un usuario
     * @param user el usuario al que pertenecen las paginas
     * @return el numero de paginas
     */
    public int addNumPagesForUser(EventUser user) {
        int cont = 0;
        for (Page page: getPagesForUser(user)) {
            cont++;
        }
        return cont;
    }

    /**
     * Metodo para buscar cuantos blogs ha creado un usuario
     * @param user el usuario al que pertenecen los blogs
     * @return el numero de blogs
     */
    public int addNumBlogsForUser(EventUser user) {
        int cont = 0;
        for (BlogPost blog: getBlogs(user)) {
            cont++;
        }
        return cont;
    }

    /**
     * Metodo para buscar cuantos comentarios ha creado un usuario
     * @param user el usuario al que pertenecen los comentarios
     * @return el numero de comentarios
     */
    public int addNumCommentsForUser(EventUser user){
        int cont = 0;
        for (Comment comment: getComments(user)) {
            cont++;
        }
        return cont;
    }

    /**
     * Metodo para buscar cuantos likes ha creado un usuario
     * @param user el usuario al que pertenecen los likes
     * @return el numero de likes
     */
    public int addNumLikesForUser(EventUser user){
        int cont = 0;
        for (Like like: getLikes(user)) {
            cont++;
        }
        return cont;
    }

    /**
     * Metodo para buscar espacios que pertenecen a un usuario
     * @param user el usuario para hacer la busqueda
     * @return una lista de espacios
     */
    private List<Space> getSpacesForUser(EventUser user){
        if (null == spaceManager){
            initializeManagers();
        }
        List<Space> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach(s ->{
            if (null != s && null != s.getKey() && null != s.getCreator() && s.getCreator().getEmail().equals(user.getUser().getEmail())){
                list.add(s);
            }
        });
        return list;
    }

    /**
     * Metodo para buscar paginas que pertenecen a un usuario
     * @param user el usuario para hacer la busqueda
     * @return una lista de paginas
     */
    private List<Page> getPagesForUser(EventUser user) {
        if (null == spaceManager || null == pageManager){
            initializeManagers();
        }
        List<Page> lista = new LinkedList<>();
        for (Space space : spaceManager.getAllSpaces()) {
            pageManager.getPages(space, true).forEach( page -> {
                if (null != page && null != page.getCreator() && page.getCreator().getEmail().equals(user.getUser().getEmail())){
                    lista.add(page);
                }
            });
        }
        return lista;
    }

    /**
     * Metodo para buscar blogs que pertenecen a un usuario
     * @param user el usuario para hacer la busqueda
     * @return una lista de blogs
     */
    private List<BlogPost> getBlogs(EventUser user){
        if (null == spaceManager || null == pageManager){
            initializeManagers();
        }
        List<BlogPost> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach( s -> {
            pageManager.getBlogPosts(s, true).forEach(blogPost -> {
                if (null != blogPost && blogPost.getCreator().getEmail().equals(user.getUser().getEmail())){
                    list.add(blogPost);
                }
            });
        });
        return list;
    }

    /**
     * Metodo para buscar comentarios que pertenecen a un usuario
     * @param user el usuario para hacer la busqueda
     * @return una lista de comentarios
     */
    private List<Comment> getComments(EventUser user){
        if (null == spaceManager || null == pageManager){
            initializeManagers();
        }
        List<Comment> list = new LinkedList<>();
        spaceManager.getAllSpaces().forEach( space -> {
            pageManager.getPages(space, true).forEach( page -> {
                page.getComments().forEach(comment -> {
                    if (null != comment && comment.getCreator().getEmail().equals(user.getUser().getEmail())){
                        list.add(comment);
                    }
                });
            });

            pageManager.getBlogPosts(space, true).forEach( blog -> {
                blog.getComments().forEach(comment -> {
                    if (null != comment && comment.getCreator().getEmail().equals(user.getUser().getEmail())){
                        list.add(comment);
                    }
                });
            });
        });
        return list;
    }

    /**
     * Metodo para buscar likes que pertenecen a un usuario
     * @param user el usuario para hacer la busqueda
     * @return una lista de likes
     */
    private List<Like> getLikes(EventUser user) {
        if (null == spaceManager || null == pageManager || null == likeManager){
            initializeManagers();
        }
        List<Like> list = new LinkedList<>();

        //recorro todos los espacios
        spaceManager.getAllSpaces().forEach(space -> {
            //recorro todas las paginas de un espacio
            pageManager.getPages(space, true).forEach( page -> {
                //recorro todos los likes de una pagina
                likeManager.getLikes(page.getEntity()).forEach( like -> {
                    if (null != like && like.getUsername().equals(user.getName())){
                        list.add(like);
                    }
                });

                //recorro todos los comentarios de una pagina
                page.getComments().forEach(comment -> {
                    //recorro todos los likes de un comentario
                    likeManager.getLikes(comment.getContentEntityObject()).forEach( like -> {
                        if (null != like && like.getUsername().equals(user.getName())){
                            list.add(like);
                        }
                    });
                });
            });

            //recorro todos los blogs de un espacio
            pageManager.getBlogPosts(space, true).forEach( blogPost -> {
                if (null != blogPost && blogPost.getCreator().getEmail().equals(user.getUser().getEmail())) {
                    list.addAll(likeManager.getLikes(blogPost.getEntity()));
                }

                //recorro todos los comments de un blog
                blogPost.getComments().forEach(comment -> {
                    //recorro todos los likes de un comentario
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
