package com.cis.confluence.plugins.rest;

import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.confluence.user.actions.ProfilePictureInfo;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ConfluencerREST {

    private final Logger logger = LoggerFactory.getLogger(ConfluencerREST.class);

    private ConfluencerManager confluencerManager;

    public void setConfluencerManager(ConfluencerManager confluencerManager) {
        this.confluencerManager = confluencerManager;
    }

    @GET
    @Path("participa")
    public Response getUser(@QueryParam("name") String name) {
        return Response.ok(confluencerManager.participa(name)).build();
    }

    @PUT
    @Path("/{name}/participa")
    public Response setParticipate(@QueryParam("name") String name) {
        if (null == confluencerManager) {
            System.out.println("--------------------------- confluencerManager NULLLL");
            //confluencerManager = new ConfluencerManager();
        } else {
            System.out.println("------------------ TO STRING CONf :: " + confluencerManager.toString());
        }
        ConfluenceUser user = AuthenticatedUserThreadLocal.get();
        if (null != user) {
            System.out.println("---------- user :: " + user.getName() + " :: " + user.getKey() + " :: " + user.getFullName() + " :: " + user.getEmail());
            confluencerManager.addUser(user.getEmail(), user.getName(), user.getFullName(), user.getKey().getStringValue(), getIcon());
        }
        return Response.ok(confluencerManager.setParticipa(name)).build();
    }


    private Icon getIcon() {
        if (null != AuthenticatedUserThreadLocal.get()) {
            UserAccessor userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
            ProfilePictureInfo icon = userAccessor.getUserProfilePicture(AuthenticatedUserThreadLocal.get());
            return new Icon(icon.getUriReference(), 40, 40, false);
        } else {
            return new Icon("", 40, 40, false);
        }
    }
}
