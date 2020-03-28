package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.event.events.content.blogpost.BlogPostCreateEvent;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostRemoveEvent;
import com.atlassian.confluence.event.events.content.comment.CommentEvent;
import com.atlassian.confluence.event.events.content.comment.CommentRemoveEvent;
import com.atlassian.confluence.event.events.content.page.PageCreateEvent;
import com.atlassian.confluence.event.events.content.page.PageRemoveEvent;
import com.atlassian.confluence.event.events.like.LikeCreatedEvent;
import com.atlassian.confluence.event.events.like.LikeRemovedEvent;
import com.atlassian.confluence.event.events.space.SpaceRemoveEvent;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.spring.container.ContainerManager;
import com.atlassian.confluence.event.events.space.SpaceCreateEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.util.Objects;


@ConfluenceComponent
@ExportAsService({ConfluenceEventServiceImpl.class})
@Named("ConfluenceEventServiceImpl")
public class ConfluenceEventServiceImpl implements EventListener, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(ConfluenceEventServiceImpl.class);

    private ConfluencerManager confluencerManager;

    //private final UserAccessor userAccessor;

    public void setConfluencerManager(ConfluencerManager confluencerManager) {
        this.confluencerManager = confluencerManager;
    }


    //@Inject
//    public ConfluenceEventServiceImpl() {
//        userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
//    }


    @Override
    public String scope() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @EventListener
    public void handleEventSpaceCreate(SpaceCreateEvent event) {
        String correo = event.getSpace().getCreator().getEmail();
        String fullName = event.getSpace().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.addSpace(correo);
            logger.debug("---=== Space add to " + fullName + " ===---");
        }
    }

    @EventListener
    public void handleEventSpaceRemove(SpaceRemoveEvent event) {
        String correo = event.getSpace().getCreator().getEmail();
        String fullName = event.getSpace().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.restSpace(correo);
            logger.debug("---=== Space removed to " + fullName + " ===---");
        }
    }

    @EventListener
    public void handleEventPageCreate(PageCreateEvent event) {
        String correo = event.getPage().getCreator().getEmail();
        String fullName = event.getPage().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.addPage(correo);
            logger.debug("---=== Page add to " + fullName + " ===---");
        }
    }

    @EventListener
    public void handleEventPageRemove(PageRemoveEvent event) {
        String correo = event.getPage().getCreator().getEmail();
        String fullName = event.getPage().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.restPage(correo);
            logger.debug("---=== Page removed to " + fullName + " ===---");
        }
    }

    @EventListener
    public void handleEventBlogCreate(BlogPostCreateEvent event) {
        String correo = event.getBlogPost().getCreator().getEmail();
        String fullName = event.getBlogPost().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.addBlog(correo);
            logger.debug("---=== Blog add to " + fullName + " ===---");
        }
    }

    @EventListener
    public void handleEventBlogRemove(BlogPostRemoveEvent event) {
        String correo = event.getBlogPost().getCreator().getEmail();
        String fullName = event.getBlogPost().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.restBlog(correo);
            logger.debug("---=== Blog removed to " + fullName + " ===---");
        }
    }

    @EventListener
    public void handleEventCommentCreate(CommentEvent event) {
        String correo = event.getComment().getCreator().getEmail();
        String fullName = event.getComment().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.addComment(correo);
            logger.debug("---=== Comment add to " + fullName + " ===---");
        }
    }

    @EventListener
    public void handleEventCommentRemove(CommentRemoveEvent event) {
        String correo = event.getComment().getCreator().getEmail();
        String fullName = event.getComment().getCreator().getFullName();

        if (confluencerManager.containsUser(correo)) {
            confluencerManager.restComment(correo);
            logger.debug("---=== Comment removed to " + fullName + " ===---");
        }
    }

    @EventListener
    public void handleEventLikeCreate(LikeCreatedEvent event) {

        String correoLiked = event.getContent().getCreator().getEmail();
        String fullNameLiked = event.getContent().getCreator().getFullName();

        String correoLiker = Objects.requireNonNull(event.getOriginatingUser()).getEmail();
        String fullNameLiker = event.getOriginatingUser().getFullName();

        if (confluencerManager.containsUser(correoLiked)){
            confluencerManager.addLike(correoLiked);
            logger.debug("---=== Like add to " + fullNameLiked + " ===---");
        }

        if (confluencerManager.containsUser(correoLiker)){
            confluencerManager.addLike(correoLiker);
            logger.debug("---=== Like add to " + fullNameLiker + " ===---");
        }
    }

    @EventListener
    public void handleEventLikeRemove(LikeRemovedEvent event) {

        String correoLiked = event.getContent().getCreator().getEmail();
        String fullNameLiked = event.getContent().getCreator().getFullName();

        String correoLiker = Objects.requireNonNull(event.getOriginatingUser()).getEmail();
        String fullNameLiker = event.getOriginatingUser().getFullName();

        if (confluencerManager.containsUser(correoLiked)){
            confluencerManager.restLike(correoLiked);
            logger.debug("---=== Like removed to " + fullNameLiked + " ===---");
        }

        if (confluencerManager.containsUser(correoLiker)){
            confluencerManager.restLike(correoLiker);
            logger.debug("---=== Like removed to " + fullNameLiker + " ===---");
        }
    }

    @Override
    public void destroy() throws Exception {

    }
}
