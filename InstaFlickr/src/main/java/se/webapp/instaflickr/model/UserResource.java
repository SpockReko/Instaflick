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
import javax.ejb.EJB;
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
import se.webapp.instaflickr.model.user.UserWrapper;

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

    @Inject
    private SessionHandler session;
    
    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    @GET
    @Path(value = "session")
    public Response getSession() {
        LOG.warning("Getting the session");
        if (!session.getSession())
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            
        String username = session.getSessionID();
        JsonObject value = Json.createObjectBuilder().add("username", username).build();
        return Response.ok(value).build();
    }
    
    @GET
    @Path(value = "logout")
    public Response logout() {
        LOG.warning("Logging out");
        session.setSession(false);
        session.setSessionID(null);
        return Response.ok().build();
    }

    @GET
    @Path(value = "loggedIn")
    public Response isLoggedIn() {
        LOG.warning("Checking session");
        JsonObject value = Json.createObjectBuilder().add("loggedIn", session.getSession()).build();        
        return Response.ok(value).build();
    }
    
    @GET
    public Response login(  @QueryParam(value = "username") String username, 
                            @QueryParam(value = "password") String password) {
        LOG.warning("User trying to log in: " + username + " " + password);
        InstaFlickUser user = instaFlick.getUserRegistry().find(username);
        if (user == null)
            return Response.status(Response.Status.CONFLICT).build();
        if (!user.getPassword().equals(password))
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        
        session.setSession(true);
        session.setSessionID(username);
        return Response.status(Response.Status.ACCEPTED).build();
    }
    
    @POST
    public Response create(@QueryParam(value = "username") String username, 
                           @QueryParam(value = "password") String password, 
                           @QueryParam(value = "repeatPassword") String repeatPassword) {
        LOG.log(Level.INFO, "Insert {0} {1}", new Object[]{username, password, repeatPassword});
        LOG.warning("Creating new user " + username + " " + password + " " + repeatPassword);
        InstaFlickUser exists = instaFlick.getUserRegistry().find(username);
        if (exists != null)
            return Response.status(Response.Status.CONFLICT).build();
        if (!repeatPassword.equals(password))
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        
        InstaFlickUser user = new InstaFlickUser(username, password);
        try {
            instaFlick.getUserRegistry().create(user);
            // Tell client where new resource is (URI to)
            URI uri = uriInfo.getAbsolutePathBuilder()
                             .path(String.valueOf(user.getUsername()))
                             .build(new UserWrapper(user));
            LOG.warning("URI " + uri.toString());
            session.setSession(true);
            session.setSessionID(username);
            return Response.created(uri).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GET
    @Path(value = "setup")
    public Response setupProfile(@QueryParam(value = "email") String email,
                                 @QueryParam(value = "fname") String fname,
                                 @QueryParam(value = "lname") String lname,
                                 @QueryParam(value = "description") String description) {
        LOG.warning("Setting up profile: " + email + " " + fname + " " + lname + " " + description );

        String username = session.getSessionID();
        InstaFlickUser user = instaFlick.getUserRegistry().find(username);
        LOG.warning("Setting up user: " + user.getUsername());
        
        user.setEmail(email);
        user.setFname(fname);
        user.setLname(lname);
        user.setDescription(description);
        instaFlick.getUserRegistry().update(user);
        
        return Response.ok().build();
    }
}
