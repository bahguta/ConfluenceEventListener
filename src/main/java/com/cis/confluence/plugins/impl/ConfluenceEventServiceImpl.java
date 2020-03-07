package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.api.model.people.User;
import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentEvent;
import com.atlassian.confluence.event.events.content.page.PageCreateEvent;
import com.atlassian.confluence.event.events.like.LikeCreatedEvent;
import com.atlassian.confluence.user.ConfluenceUserImpl;
import com.atlassian.confluence.user.avatar.ConfluenceAvatarOwner;
import com.cis.confluence.plugins.api.ConfluenceEventService;
import com.atlassian.confluence.event.events.space.SpaceCreateEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.apache.log4j.Logger;
import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.util.List;


@ConfluenceComponent
@ExportAsService({ConfluenceEventServiceImpl.class})
@Named("ConfluenceEventServiceImpl")
public class ConfluenceEventServiceImpl implements ConfluenceEventService, EventListener {

    private final Logger logger = org.apache.log4j.Logger.getLogger(ConfluenceEventServiceImpl.class);
    private final ConfluencerManager confluencerManager = new ConfluencerManager();

    @Override
    public List<EventUser> getList() {
        return ConfluencerManager.getList();
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
        Icon icon = User.fromUserkey(event.getSpace().getCreator().getKey()).getProfilePicture();

        if (!ConfluencerManager.containsUser(correo)) {
            ConfluencerManager.addUser(correo, fullName, icon);
        }

        ConfluencerManager.addSpace(correo);
        logger.debug("---=== Space add to " + fullName + " ===---");
        System.out.println("---=== Space add to " + fullName + " ===---");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventPageCreate(PageCreateEvent event) {
        String correo = event.getPage().getCreator().getEmail();
        String fullName = event.getPage().getCreator().getFullName();
        Icon icon = User.fromUserkey(event.getPage().getCreator().getKey()).getProfilePicture();

        if (!ConfluencerManager.containsUser(correo)) {
           ConfluencerManager.addUser(correo, fullName, icon);
        }

        ConfluencerManager.addPage(correo);
        logger.debug("---=== Page add to " + fullName + " ===---");
        System.out.println("---=== Page add to " + fullName + " ===---");
        System.out.println("====================================================================================");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventBlogCreate(BlogPostCreateEvent event) {
        String correo = event.getBlogPost().getCreator().getEmail();
        String fullName = event.getBlogPost().getCreator().getFullName();
        Icon icon = User.fromUserkey(event.getBlogPost().getCreator().getKey()).getProfilePicture();

        if (!ConfluencerManager.containsUser(correo)) {
            ConfluencerManager.addUser(correo, fullName, icon);
        }

        ConfluencerManager.addBlog(correo);
        logger.debug("---=== Blog add to " + fullName + " ===---");
        System.out.println("---=== Blog add to " + fullName + " ===---");
        System.out.println("====================================================================================");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventCommentCreate(CommentEvent event) {
        String correo = event.getComment().getCreator().getEmail();
        String fullName = event.getComment().getCreator().getFullName();
        Icon icon = User.fromUserkey(event.getComment().getCreator().getKey()).getProfilePicture();

        if (!ConfluencerManager.containsUser(correo)) {
            ConfluencerManager.addUser(correo, fullName, icon);
        }

        ConfluencerManager.addComment(correo);
        logger.debug("---=== Comment add to " + fullName + " ===---");
        System.out.println("---=== Comment add to " + fullName + " ===---");
        System.out.println("====================================================================================");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventLikeCreate(LikeCreatedEvent event) {
        System.out.println("-------" +  User.fromUserkey(event.getContent().getCreator().getKey()).toString());
        System.out.println("-------" + User.fromUsername(event.getOriginatingUser().getName()).getProfilePicture());
        String correoLiked = event.getContent().getCreator().getEmail();
        String fullNameLiked = event.getContent().getCreator().getFullName();
        Icon iconLiked = User.fromUserkey(event.getContent().getCreator().getKey()).getProfilePicture();

        String correoLiker = event.getOriginatingUser().getEmail();
        String fullNameLiker = event.getOriginatingUser().getFullName();
        Icon iconLiker = User.fromUsername(event.getOriginatingUser().getName()).getProfilePicture();

        if (!ConfluencerManager.containsUser(correoLiked)){
            ConfluencerManager.addUser(correoLiked, fullNameLiked, iconLiked);
        }

        if (!ConfluencerManager.containsUser(correoLiker)){
            ConfluencerManager.addUser(correoLiker, fullNameLiker, iconLiker);
        }

        ConfluencerManager.addLike(correoLiked);
        ConfluencerManager.addLike(correoLiker);

        logger.debug("---=== Like add to " + fullNameLiked + " ===---");
        logger.debug("---=== Like add to " + fullNameLiker + " ===---");
        System.out.println("---=== Like add to " + fullNameLiked + " ===---");
        System.out.println("---=== Like add to " + fullNameLiker + " ===---");

        System.out.println("====================================================================================");
        ConfluencerManager.printResults();
    }

}
