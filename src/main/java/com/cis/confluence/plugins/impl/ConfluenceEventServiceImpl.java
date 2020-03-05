package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.event.events.content.blogpost.BlogPostCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentEvent;
import com.atlassian.confluence.event.events.content.page.PageCreateEvent;
import com.atlassian.confluence.event.events.like.LikeCreatedEvent;
import com.cis.confluence.plugins.api.ConfluenceEventService;
import com.atlassian.confluence.event.events.space.SpaceCreateEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.apache.log4j.Logger;
import javax.inject.Named;
import java.lang.annotation.Annotation;


@ConfluenceComponent
@ExportAsService({ConfluenceEventServiceImpl.class})
@Named("ConfluenceEventServiceImpl")
public class ConfluenceEventServiceImpl implements ConfluenceEventService, EventListener {

    private final Logger logger = org.apache.log4j.Logger.getLogger(ConfluenceEventServiceImpl.class);
    private final ConfluencerManager confluencerManager = new ConfluencerManager();

    public String getName() {
        logger.error("--------------->>> ConfluenceEventService  ");
        return "ConfluenceEventService";
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
        if (!ConfluencerManager.containsUser(event.getSpace().getCreator().getEmail())) {
            ConfluencerManager.addUser(event.getSpace().getCreator().getEmail(), event.getSpace().getCreator().getFullName());
        }
        ConfluencerManager.addSpace(event.getSpace().getCreator().getEmail());
        logger.debug("---=== Space add to " + event.getSpace().getCreator().getFullName() + " ===---");
        System.out.println("---=== Space add to " + event.getSpace().getCreator().getFullName() + " ===---");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventPageCreate(PageCreateEvent event) {
        if (!ConfluencerManager.containsUser(event.getPage().getCreator().getEmail())) {
           ConfluencerManager.addUser(event.getPage().getCreator().getEmail(), event.getPage().getCreator().getFullName());
        }
        ConfluencerManager.addPage(event.getPage().getCreator().getEmail());
        logger.debug("---=== Page add to " + event.getPage().getCreator().getFullName() + " ===---");
        System.out.println("---=== Page add to " + event.getPage().getCreator().getFullName() + " ===---");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventCommentCreate(CommentEvent event) {
        if (!ConfluencerManager.containsUser(event.getComment().getCreator().getEmail())) {
            ConfluencerManager.addUser(event.getComment().getCreator().getEmail(), event.getComment().getCreator().getFullName());
        }
        ConfluencerManager.addComment(event.getComment().getCreator().getEmail());
        logger.debug("---=== Comment add to " + event.getComment().getCreator().getFullName() + " ===---");
        System.out.println("---=== Comment add to " + event.getComment().getCreator().getFullName() + " ===---");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventBlogCreate(BlogPostCreateEvent event) {
        if (!ConfluencerManager.containsUser(event.getBlogPost().getCreator().getEmail())) {
            ConfluencerManager.addUser(event.getBlogPost().getCreator().getEmail(), event.getBlogPost().getCreator().getFullName());
        }
        ConfluencerManager.addBlog(event.getBlogPost().getCreator().getEmail());
        logger.debug("---=== Blog add to " + event.getBlogPost().getCreator().getFullName() + " ===---");
        System.out.println("---=== Blog add to " + event.getBlogPost().getCreator().getFullName() + " ===---");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventLikeCreate(LikeCreatedEvent event) {
        if (!ConfluencerManager.containsUser(event.getContent().getCreator().getEmail())) {
            ConfluencerManager.addUser(event.getContent().getCreator().getEmail(), event.getContent().getCreator().getFullName());
        }
        ConfluencerManager.addLike(event.getContent().getCreator().getEmail());
        logger.debug("---=== Like add to " + event.getContent().getCreator().getFullName() + " ===---");
        System.out.println("---=== Like add to " + event.getContent().getCreator().getFullName() + " ===---");
        ConfluencerManager.printResults();
    }



//    @EventListener
//    public void handleEventUpdate(PageUpdateEvent event) {
//        for (int i = 0; i < 10; i++) {
//
//            logger.error("--------------------UPDATED>> {}", event.getPage().getCreator());
//            logger.debug("--------------------UPDATED-debug>> {}", event.getPage().getContentStatus());
//        }
//    }

//    @EventListener
//    public void handleEventDelete(PageRemoveEvent event) {
//        for (int i = 0; i < 10; i++) {
//
//            logger.error("--------------------REMOVED>> {}", event.getPage().getCreator());
//            logger.debug("--------------------REMOVED-debug>> {}", event.getPage().getLastModifier().getFullName());
//        }
//    }

//    @EventListener
//    public void onEvent(SpaceCreateEvent event) {
//        for (int i = 0; i < 10; i++) {
//            logger.error("-------------------------------- > [error] Handled an event: " + event.getSpace().getName());
//            logger.debug("-------------------------------- > [DEBUG] Handled an event: " + event.getSpace().getCreator());
//        }
//    }
}
