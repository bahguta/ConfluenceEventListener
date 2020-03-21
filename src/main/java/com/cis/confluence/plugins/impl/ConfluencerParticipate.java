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

    private AbstractPage page;
    private boolean participate;

    public ConfluencerParticipate() {
        this.participate = false;
    }

    @Override
    public String doDefault() {
        return "ERROR";
    }

    @Override
    public String execute() {
        return "SUCCESS";
    }

    public boolean isParticipate() {
        if (ConfluencerManager.containsUser(AuthenticatedUserThreadLocal.get().getEmail())){
            return ConfluencerManager.participa(AuthenticatedUserThreadLocal.get().getName());
        } else {
            return false;
        }
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
    public void destroy() {}
}
