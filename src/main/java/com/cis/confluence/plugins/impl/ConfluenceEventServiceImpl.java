package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.api.model.people.Person;
import com.atlassian.confluence.api.model.people.User;
import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.confluence.api.service.people.PersonService;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.event.events.content.blogpost.BlogPostCreateEvent;
import com.atlassian.confluence.event.events.content.comment.CommentEvent;
import com.atlassian.confluence.event.events.content.page.PageCreateEvent;
import com.atlassian.confluence.event.events.like.LikeCreatedEvent;
import com.atlassian.confluence.macro.profile.ProfileMacro;
import com.atlassian.confluence.macro.xhtml.MacroManager;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.pages.PageUpdateTrigger;
import com.atlassian.confluence.pages.actions.EditPageAction;
import com.atlassian.confluence.plugin.descriptor.ComponentModuleDescriptor;
import com.atlassian.confluence.renderer.PageContext;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.confluence.user.UserDetailsManager;
import com.atlassian.confluence.user.actions.AbstractUserProfileAction;
import com.atlassian.confluence.user.actions.ProfilePictureInfo;
import com.atlassian.confluence.util.TemplateSupport;
import com.atlassian.crowd.embedded.atlassianuser.EmbeddedCrowdUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ConfluenceImport;
import com.atlassian.sal.api.user.UserKey;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.api.ConfluenceEventService;
import com.atlassian.confluence.event.events.space.SpaceCreateEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@ConfluenceComponent
@ExportAsService({ConfluenceEventServiceImpl.class})
@Named("ConfluenceEventServiceImpl")
public class ConfluenceEventServiceImpl implements ConfluenceEventService, EventListener, DisposableBean {

    private final Logger logger = org.apache.log4j.Logger.getLogger(ConfluenceEventServiceImpl.class);
    private final ConfluencerManager confluencerManager = new ConfluencerManager();
    private final UserAccessor userAccessor;

    public ConfluenceEventServiceImpl() {
        userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
    }

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
        String key = event.getSpace().getCreator().getKey().getStringValue();

        if (!ConfluencerManager.containsUser(correo)) {
            return;
            //Icon icon = User.fromUserkey(event.getSpace().getCreator().getKey()).getProfilePicture();
            //ConfluencerManager.addUser(correo, fullName, key, new Icon(icon.getPath(), 40, 40, false));
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
        String key = event.getPage().getCreator().getKey().getStringValue();

        if (!ConfluencerManager.containsUser(correo)) {
            return;
            //ProfilePictureInfo icon = userAccessor.getUserProfilePicture(event.getContent().getCreator());
            //ConfluencerManager.addUser(correo, fullName, key, new Icon(icon.getUriReference(), 40, 40, false));
        }

        ConfluencerManager.addPage(correo);
        logger.debug("---=== Page add to " + fullName + " ===---");
        System.out.println("---=== Page add to " + fullName + " ===---");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventBlogCreate(BlogPostCreateEvent event) {
        String correo = event.getBlogPost().getCreator().getEmail();
        String fullName = event.getBlogPost().getCreator().getFullName();
        String key = event.getBlogPost().getCreator().getKey().getStringValue();

        if (!ConfluencerManager.containsUser(correo)) {
            return;
            //ProfilePictureInfo icon = userAccessor.getUserProfilePicture(event.getContent().getCreator());
            //ConfluencerManager.addUser(correo, fullName, key,  new Icon(icon.getUriReference(), 40, 40, false));
        }

        ConfluencerManager.addBlog(correo);
        logger.debug("---=== Blog add to " + fullName + " ===---");
        System.out.println("---=== Blog add to " + fullName + " ===---");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventCommentCreate(CommentEvent event) {
        String correo = event.getComment().getCreator().getEmail();
        String fullName = event.getComment().getCreator().getFullName();
        String key = event.getComment().getCreator().getKey().getStringValue();

        if (!ConfluencerManager.containsUser(correo)) {
            return;
            //ProfilePictureInfo icon = userAccessor.getUserProfilePicture(event.getContent().getCreator());
            //ConfluencerManager.addUser(correo, fullName, key, new Icon(icon.getUriReference(), 40, 40, false));
        }

        ConfluencerManager.addComment(correo);
        logger.debug("---=== Comment add to " + fullName + " ===---");
        System.out.println("---=== Comment add to " + fullName + " ===---");
        ConfluencerManager.printResults();
    }

    @EventListener
    public void handleEventLikeCreate(LikeCreatedEvent event) {

        String correoLiked = event.getContent().getCreator().getEmail();
        String fullNameLiked = event.getContent().getCreator().getFullName();
        String keyLiked = event.getContent().getCreator().getKey().getStringValue();

        String correoLiker = Objects.requireNonNull(event.getOriginatingUser()).getEmail();
        String fullNameLiker = event.getOriginatingUser().getFullName();
        String keyLiker = userAccessor.getUserByName(event.getOriginatingUser().getName()).getKey().getStringValue();


        if (!ConfluencerManager.containsUser(correoLiked)){
            return;
            //ProfilePictureInfo icon = userAccessor.getUserProfilePicture(event.getContent().getCreator());
           // ConfluencerManager.addUser(correoLiked, fullNameLiked, keyLiked, new Icon(icon.getUriReference(), 40, 40, false));
        }

        if (!ConfluencerManager.containsUser(correoLiker)){
            return;
            //ProfilePictureInfo icon = userAccessor.getUserProfilePicture(event.getOriginatingUser());
            //ConfluencerManager.addUser(correoLiker, fullNameLiker, keyLiker, new Icon(icon.getUriReference(), 40, 40, false));
        }

        ConfluencerManager.addLike(correoLiked);
        ConfluencerManager.addLike(correoLiker);

        logger.debug("---=== Like add to " + fullNameLiked + " ===---");
        logger.debug("---=== Like add to " + fullNameLiker + " ===---");
        System.out.println("---=== Like add to " + fullNameLiked + " ===---");
        System.out.println("---=== Like add to " + fullNameLiker + " ===---");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("_-----------------------------------> DESTROY");
    }
}
