package com.cis.confluence.plugins.impl;


import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.pages.AbstractPage;
import com.atlassian.confluence.pages.actions.PageAware;
import com.atlassian.confluence.user.*;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;

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
