package com.cis.confluence.plugins.rest;

import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.user.ConfluenceUser;
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
    @Path("/name/participa")
    public Response setParticipate() {
        ConfluenceUser user = AuthenticatedUserThreadLocal.get();
        if (null != user) {
            confluencerManager.addUser(user.getEmail());
        }
        return Response.ok(true).build();
    }

    @PUT
    @Path("/name/cancelar")
    public Response cancelarParticipate() {
        ConfluenceUser user = AuthenticatedUserThreadLocal.get();
        if (null != user) {
            confluencerManager.cancelarParticipacion(user.getName());
        }
        return Response.ok(true).build();
    }
}
