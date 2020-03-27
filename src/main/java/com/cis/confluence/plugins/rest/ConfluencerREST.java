package com.cis.confluence.plugins.rest;

import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.confluence.user.actions.ProfilePictureInfo;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ConfluencerREST {

    private final Logger logger = LoggerFactory.getLogger(ConfluencerREST.class);

    @GET
    @Path("participa")
    public Response getUser(@QueryParam("name") String name) {
        return Response.ok(ConfluencerManager.participa(name)).build();
    }

    @PUT
    @Path("/{name}/participa")
    public Response setParticipate(@QueryParam("name") String name) {
        ConfluenceUser user = AuthenticatedUserThreadLocal.get();
        if (null != user) {
            ConfluencerManager.addUser(user.getEmail(), user.getName(), user.getFullName(), user.getKey().getStringValue(), getIcon());
        }
        return Response.ok(ConfluencerManager.setParticipa(name)).build();
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
