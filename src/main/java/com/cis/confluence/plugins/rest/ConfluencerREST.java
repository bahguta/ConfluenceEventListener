package com.cis.confluence.plugins.rest;

import com.cis.confluence.plugins.utils.ConfluencerManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ConfluencerREST {

    @GET
    @Path("first")
    public Response getUser(@QueryParam("fullName") String fullName) {
        return Response.ok(ConfluencerManager.getList().get(0).getFullName()).build();
    }

    @PUT
    @Path("/{correo}")
    public Response setParticipate(@QueryParam("participate") boolean participate){
        return Response.ok(ConfluencerManager.getFirst().setParticipate(participate)).build();
    }
}
