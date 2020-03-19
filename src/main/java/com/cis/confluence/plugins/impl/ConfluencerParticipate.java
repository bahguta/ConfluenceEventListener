package com.cis.confluence.plugins.impl;


import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.labels.Label;
import com.atlassian.confluence.labels.LabelManager;
import com.atlassian.confluence.labels.Labelable;
import com.atlassian.confluence.pages.AbstractPage;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.pages.actions.PageAware;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.security.DefaultPermissionManager;
import com.atlassian.confluence.security.Permission;
import com.atlassian.confluence.security.PermissionHelper;
import com.atlassian.confluence.security.PermissionManager;
import com.atlassian.confluence.security.administrators.DefaultPermissionsAdministrator;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.confluence.user.*;
import com.atlassian.confluence.user.actions.AbstractUserProfileAction;
import com.atlassian.confluence.user.actions.ProfilePictureInfo;
import com.atlassian.confluence.user.actions.ViewUserActivityAction;
import com.atlassian.confluence.user.actions.ViewUserHistoryAction;
import com.atlassian.confluence.user.crowd.CachedCrowdUser;
import com.atlassian.confluence.user.crowd.CrowdUserDirectoryHelper;
import com.atlassian.confluence.userstatus.StatusTextRenderer;
import com.atlassian.confluence.util.GeneralUtil;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.crowd.embedded.atlassianuser.EmbeddedCrowdUser;
import com.atlassian.crowd.integration.seraph.CrowdAuthenticator;
import com.atlassian.crowd.service.client.ClientPropertiesImpl;
import com.atlassian.crowd.service.client.CrowdClient;
import com.atlassian.crowd.service.factory.CrowdClientFactory;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;
import com.atlassian.seraph.auth.AuthenticationContext;
import com.atlassian.spring.container.ContainerManager;
import com.atlassian.user.EntityException;
import com.atlassian.user.User;
import java.util.Arrays;
import java.util.EventListener;
import java.util.Map;
import java.util.Optional;

import com.atlassian.user.UserManager;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import com.cis.confluence.plugins.utils.EventSeekerManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.security.access.method.P;

import javax.inject.Inject;
import javax.inject.Named;

public class ConfluencerParticipate extends ConfluenceActionSupport implements PageAware, DisposableBean {

    private static Logger logger = Logger.getLogger(ConfluencerParticipate.class);

    private EventUser user;
    private AbstractPage page;
    private boolean participate;
    private String text;
    private EventSeekerManager eventSeekerManager;
    private Map<String,Object> map;
    private ConfluencerMacroPage confluencerMacroPage;


    public ConfluencerParticipate() throws EntityException {
        eventSeekerManager = new EventSeekerManager();
        this.text = "some Text";
        this.participate = false;
        map = MacroUtils.defaultVelocityContext();
        confluencerMacroPage = new ConfluencerMacroPage();
        //AuthenticatedUserThreadLocal.get()

        //AuthenticationContext authenticationContext = (AuthenticationContext) ContainerManager.getComponent("AuthenticationContext");
        //ConfluenceUser confluenceUser = AuthenticatedUserThreadLocal.get();
        //System.out.println("-------------------------------- " + authenticationContext.getUser().getName());
        //if (AuthenticatedUserThreadLocal.isAnonymousUser()){
        ConfluenceUser confluenceUser = getCurrentUser();
        this.user = new EventUser(confluenceUser.getEmail(), confluenceUser.getFullName(), confluenceUser.getKey().getStringValue(), getIcon());
        System.out.println("--------------------------11111111111 "  + user.toString());



//            this.userManager = (UserManager)ContainerManager.getComponent("userManager");
//            //ConfluenceUserImpl confluenceUser = userManager.getUser(userManager.getUsers().getCurrentPage().get(0));
//            System.out.println("===========================11111  2222222 " + userManager.getUsers().getCurrentPage().get(0).toString());
//            EmbeddedCrowdUser crowdUser = (EmbeddedCrowdUser) userManager.getUsers().getCurrentPage().get(0);
//            UserAccessor userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
//            ConfluenceUser user = userAccessor.getUserByName(userManager.getUser(crowdUser.getName()).getName());
//            System.out.println(" ---------------------------------- user :: " + user.toString());
//
//            System.out.println("---------------------------- " + ViewUserActivityAction.getTextStatic(user.getKey().getStringValue()));
//            System.out.println("---------------------------- " + ViewUserActivityAction.getTextStatic(ViewUserActivityAction.HOMEPAGE_PROFILE));
//            //ProfilePictureInfo icon = userAccessor.getUserProfilePicture(userManager.getUser(crowdUser.getName()));
//
//            //EventUser eventUser = new EventUser(user.getEmail(), user.getFullName(), new Icon(icon.getUriReference(), 40, 40, false));
//            participa();//crowdUser.getEmail(), crowdUser.getFullName(), new Icon(icon.getUriReference(), 40, 40, false));
//            System.out.println("+_+_+_++_+_++_+_++++_+");
//            ConfluencerManager.getSortedList().forEach(u -> System.out.println(u.toString()));




        //} else {
        //    this.user = AuthenticatedUserThreadLocal.get();
        //}

        //
        //this.user = (EventUser) AuthenticatedUserThreadLocal.get();
        //System.out.println("----------------------USERNAME : " + user);
        //System.out.println("----------------------USERNAME : " + user);
        //System.out.println("----------------------USERNAME : " + user);

        // this.user = (EventUser) userManager.getUser(user.getName());
       // AuthenticatedUserThreadLocal.asUser(userAccessor.getUserByKey(user.getKey()));
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public String getConfluencerMacroPage() {
        return confluencerMacroPage.getPage();
    }

    private ConfluenceUser getCurrentUser(){
        ConfluenceUser currentUser = null;
        if (AuthenticatedUserThreadLocal.isAnonymousUser()){
            try {
                UserManager userManager = (UserManager) ContainerManager.getComponent("userManager");
                UserAccessor userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");

                EmbeddedCrowdUser crowdUser = (EmbeddedCrowdUser) userManager.getUsers().getCurrentPage().get(0);
                currentUser = userAccessor.getUserByName(userManager.getUser(crowdUser.getName()).getName());

                return currentUser;

            } catch (EntityException e){
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            currentUser = AuthenticatedUserThreadLocal.get();
            return currentUser;
        }

    }




//    public PermissionHelper getPermissionHelper() {
//        PermissionHelper permissionHelper = null;
//        if (getPermissionHelper() == null) {
//            PageManager pageManager = (PageManager) ContainerManager.getComponent("pageManager");
//            PersonalInformationManager personalInformationManager = (PersonalInformationManager)  ContainerManager.getComponent("personalInformationManager");
//            permissionHelper = new PermissionHelper(permissionManager, personalInformationManager, pageManager);
//        }
//        return permissionHelper;
//    }


    public EventSeekerManager getEventSeekerManager() {
        return eventSeekerManager;
    }

    //public UserManager getUserManager() {
    //    return userManager;
    //}

    private Icon getIcon(){
        UserAccessor userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
        ProfilePictureInfo icon = userAccessor.getUserProfilePicture(getCurrentUser());

        return new Icon(icon.getUriReference(), 40, 40, false);
    }

    public void participa(){
        setParticipate(true);

        ConfluenceUser currentUser = getCurrentUser();

        assert currentUser != null;

        System.out.println(currentUser.getEmail() + " " + currentUser.getFullName() + " " + getIcon());
        EventUser eventUser = new EventUser(currentUser.getEmail(), currentUser.getFullName(), currentUser.getKey().getStringValue(), getIcon());
        ConfluencerManager.addUser(currentUser.getEmail(), currentUser.getFullName(), currentUser.getKey().getStringValue(),  getIcon());

        //eventUser.setSpaces(EventSeekerManager.getNumSpacesForUser(eventUser));
        //eventUser.setPages(EventSeekerManager.getNumPagesForUser(eventUser));

        //eventUser.setBlogs(EventSeekerManager.getNumBlogsForUser(eventUser));
        //currentUser.setComments(EventSeekerManager.getNumCommentsForUser(currentUser));
        //currentUser.setLikes(EventSeekerManager.getNumLikesForUser(currentUser));
        ConfluencerManager.getSortedList().forEach(u -> System.out.println(u.toString()));
    }

    @Override
    public String doDefault() throws Exception {
        return "ERROR";
    }

    @Override
    public String execute() throws Exception {
        System.out.println("=================================== > execute " + participate);
        return "SUCCESS";
    }

    public EventUser getUser() {
        return user;
    }

    public boolean isParticipate() {
        System.out.println("=================================== > GET " + participate);
        System.out.println("=================================== > GET user " + this.user.isParticipate());
        return this.user.isParticipate();
    }

    public void setParticipate(boolean participate) {
        System.out.println("=================================== > SET " + participate);
        System.out.println("=================================== > SET user " + this.user.isParticipate());
        this.participate = participate;
        this.user.setParticipate(this.participate);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public AbstractPage getPage() {
        return page;
    }

    @Override
    public void setPage(AbstractPage abstractPage) {
        this.page = abstractPage;
    }

    @Override
    public boolean isPageRequired() {
        return false;
    }

    @Override
    public boolean isLatestVersionRequired() {
        return false;
    }

    @Override
    public boolean isViewPermissionRequired() {
        return false;
    }


    @Override
    public void destroy() throws Exception {
        if (participate){
            System.out.println("------------------------------------------------>>> IF : " + participate);
        } else {
            System.out.println("------------------------------------------------>>> ELSE : " + participate);

        }
    }
}
