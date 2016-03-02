/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import java.net.URI;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Henry Ottervad
 */
@Path("reg")
public class UserResource {

    @Context
    private UriInfo uriInfo;
    
    @Inject
    private InstaFlick instaFlick;
    
    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());
    
    @POST
    public Response create(@QueryParam(value = "username") String username, @QueryParam(value = "password") String password) {
        LOG.log(Level.INFO, "Insert {0} {1}", new Object[]{username, password});
        LOG.warning("Creating new user " + username + " " + password);
        InstaFlickUser user = new InstaFlickUser(username);
        try {
            instaFlick.getUserRegistry().create(user);
            // Tell client where new resource is (URI to)
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserName())).build();
            return Response.created(uri).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
