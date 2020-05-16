package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.event.events.content.blogpost.BlogPostCreateEvent;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostRemoveEvent;
import com.atlassian.confluence.event.events.content.comment.CommentCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentRemoveEvent;
import com.atlassian.confluence.event.events.content.page.PageCreateEvent;
import com.atlassian.confluence.event.events.content.page.PageRemoveEvent;
import com.atlassian.confluence.event.events.like.LikeCreatedEvent;
import com.atlassian.confluence.event.events.like.LikeRemovedEvent;
import com.atlassian.confluence.event.events.space.SpaceRemoveEvent;
import com.atlassian.confluence.event.events.space.SpaceCreateEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.util.Objects;


/**
 * Clase para manejar los eventos producidos por Confluence y
 * capturar solo los que se producen de los participantes de Confluencer
 */
@ConfluenceComponent
@ExportAsService({ConfluenceEventServiceImpl.class})
@Named("ConfluenceEventServiceImpl")
public class ConfluenceEventServiceImpl implements EventListener, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(ConfluenceEventServiceImpl.class);

    private ConfluencerManager confluencerManager;


    public void setConfluencerManager(ConfluencerManager confluencerManager) {
        this.confluencerManager = confluencerManager;
    }

    @Override
    public String scope() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }


    /**
     * Metodos para capturar los eventos creados solo por usuarios
     * que estan participando
     */

    @EventListener
    public void handleEventSpaceCreate(SpaceCreateEvent event) {
        String correo = event.getSpace().getCreator().getEmail();
        String fullName = event.getSpace().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.addSpace(correo);
        }
    }

    @EventListener
    public void handleEventSpaceRemove(SpaceRemoveEvent event) {
        String correo = event.getSpace().getCreator().getEmail();
        String fullName = event.getSpace().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.restSpace(correo);
        }
    }

    @EventListener
    public void handleEventPageCreate(PageCreateEvent event) {
        String correo = event.getPage().getCreator().getEmail();
        String fullName = event.getPage().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.addPage(correo);
        }
    }

    @EventListener
    public void handleEventPageRemove(PageRemoveEvent event) {
        String correo = event.getPage().getCreator().getEmail();
        String fullName = event.getPage().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.restPage(correo);
        }
    }

    @EventListener
    public void handleEventBlogCreate(BlogPostCreateEvent event) {
        String correo = event.getBlogPost().getCreator().getEmail();
        String fullName = event.getBlogPost().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.addBlog(correo);
        }
    }

    @EventListener
    public void handleEventBlogRemove(BlogPostRemoveEvent event) {
        String correo = event.getBlogPost().getCreator().getEmail();
        String fullName = event.getBlogPost().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.restBlog(correo);
        }
    }

    @EventListener
    public void handleEventCommentCreate(CommentCreateEvent event) {
        String correo = event.getComment().getCreator().getEmail();
        String fullName = event.getComment().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.addComment(correo);
        }
    }

    @EventListener
    public void handleEventCommentRemove(CommentRemoveEvent event) {
        String correo = event.getComment().getCreator().getEmail();
        String fullName = event.getComment().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.restComment(correo);
        }
    }

    @EventListener
    public void handleEventLikeCreate(LikeCreatedEvent event) {

        String correoLiked = event.getContent().getCreator().getEmail();
        String fullNameLiked = event.getContent().getCreator().getFullName();

        String correoLiker = Objects.requireNonNull(event.getOriginatingUser()).getEmail();
        String fullNameLiker = event.getOriginatingUser().getFullName();

        if (correoLiked.equals(correoLiker)){
            if (confluencerManager.containsUser(correoLiked)){
                confluencerManager.addLike(correoLiked);
            }
        } else {
            if (confluencerManager.containsUser(correoLiked)) {
                confluencerManager.addLike(correoLiked);
            }

            if (confluencerManager.containsUser(correoLiker)) {
                confluencerManager.addLike(correoLiker);
            }
        }
    }

    @EventListener
    public void handleEventLikeRemove(LikeRemovedEvent event) {

        String correoLiked = event.getContent().getCreator().getEmail();
        String fullNameLiked = event.getContent().getCreator().getFullName();

        String correoLiker = Objects.requireNonNull(event.getOriginatingUser()).getEmail();
        String fullNameLiker = event.getOriginatingUser().getFullName();

        if (correoLiked.equals(correoLiker)){
            if (confluencerManager.containsUser(correoLiked)){
                confluencerManager.restLike(correoLiked);
            }
        } else {
            if (confluencerManager.containsUser(correoLiked)) {
                confluencerManager.restLike(correoLiked);
            }

            if (confluencerManager.containsUser(correoLiker)) {
                confluencerManager.restLike(correoLiker);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
    }
}
