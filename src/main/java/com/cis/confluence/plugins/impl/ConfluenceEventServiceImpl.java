package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.event.events.content.blogpost.BlogPostCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentEvent;
import com.atlassian.confluence.event.events.content.page.PageCreateEvent;
import com.atlassian.confluence.event.events.like.LikeCreatedEvent;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.spring.container.ContainerManager;
import com.atlassian.confluence.event.events.space.SpaceCreateEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import com.cis.confluence.plugins.utils.EventSeekerManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.util.Objects;


@ConfluenceComponent
@ExportAsService({ConfluenceEventServiceImpl.class})
@Named("ConfluenceEventServiceImpl")
public class ConfluenceEventServiceImpl implements EventListener, DisposableBean {

    private final Logger logger = org.apache.log4j.Logger.getLogger(ConfluenceEventServiceImpl.class);
    private final ConfluencerManager confluencerManager = new ConfluencerManager();
    private final EventSeekerManager eventSeekerManager = new EventSeekerManager();
    private final UserAccessor userAccessor;

    public ConfluenceEventServiceImpl() {
        userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
    }


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

        if (!ConfluencerManager.containsUser(correo)) {
            return;
        }

        ConfluencerManager.addSpace(correo);
        logger.debug("---=== Space add to " + fullName + " ===---");
    }

    @EventListener
    public void handleEventPageCreate(PageCreateEvent event) {
        String correo = event.getPage().getCreator().getEmail();
        String fullName = event.getPage().getCreator().getFullName();

        if (!ConfluencerManager.containsUser(correo)) {
            return;
        }

        ConfluencerManager.addPage(correo);
        logger.debug("---=== Page add to " + fullName + " ===---");
    }

    @EventListener
    public void handleEventBlogCreate(BlogPostCreateEvent event) {
        String correo = event.getBlogPost().getCreator().getEmail();
        String fullName = event.getBlogPost().getCreator().getFullName();

        if (!ConfluencerManager.containsUser(correo)) {
            return;
        }

        ConfluencerManager.addBlog(correo);
        logger.debug("---=== Blog add to " + fullName + " ===---");
    }

    @EventListener
    public void handleEventCommentCreate(CommentEvent event) {
        String correo = event.getComment().getCreator().getEmail();
        String fullName = event.getComment().getCreator().getFullName();

        if (!ConfluencerManager.containsUser(correo)) {
            return;
        }

        ConfluencerManager.addComment(correo);
        logger.debug("---=== Comment add to " + fullName + " ===---");
    }

    @EventListener
    public void handleEventLikeCreate(LikeCreatedEvent event) {

        String correoLiked = event.getContent().getCreator().getEmail();
        String fullNameLiked = event.getContent().getCreator().getFullName();

        String correoLiker = Objects.requireNonNull(event.getOriginatingUser()).getEmail();
        String fullNameLiker = event.getOriginatingUser().getFullName();


        if (ConfluencerManager.containsUser(correoLiked)){
            ConfluencerManager.addLike(correoLiked);
            logger.debug("---=== Like add to " + fullNameLiked + " ===---");
        }

        if (ConfluencerManager.containsUser(correoLiker)){
            ConfluencerManager.addLike(correoLiker);
            logger.debug("---=== Like add to " + fullNameLiker + " ===---");
        }
    }

    @Override
    public void destroy() throws Exception {}
}
