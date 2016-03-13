package se.webapp.instaflickr.model.user;

import se.webapp.instaflickr.model.persistence.InstaFlick;
import se.webapp.instaflickr.model.persistence.SessionHandler;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * This is the point where we handle http connections regarding the users.
 * Logging in, logging out, registering new users etc.
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

    @POST
    public Response create(@QueryParam(value = "username") String username,
            @QueryParam(value = "password") String password,
            @QueryParam(value = "repeatPassword") String repeatPassword) {
        LOG.log(Level.INFO, "\t Creating new user {0} {1} {2}", new Object[]{username, password, repeatPassword});

        InstaFlickUser exists;
        try {
            exists = instaFlick.getUserRegistry().find(username);
        } catch (EJBException e) {
            LOG.warning("EJBException: " + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (exists != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        if (!repeatPassword.equals(password)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        InstaFlickUser user = new InstaFlickUser(username, password);
        try {
            instaFlick.getUserRegistry().create(user);
            // Tell client where new resource is (URI to)
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(user.getUsername()))
                    .build(new UserWrapper(user));
            LOG.log(Level.INFO, "URI " + uri.toString());
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
        LOG.log(Level.WARNING, "Setting up profile: {0} {1} {2} {3}", new Object[]{email, fname, lname, description});

        String username = session.getSessionID();
        InstaFlickUser user = instaFlick.getUserRegistry().find(username);
        LOG.log(Level.INFO, "\t Setting up user: " + user.getUsername());

        user.setEmail(email);
        user.setFname(fname);
        user.setLname(lname);
        user.setDescription(description);
        instaFlick.getUserRegistry().update(user);

        return Response.ok().build();
    }

    @GET
    @Path(value = "update")
    public Response updateProfile(@QueryParam(value = "email") String email,
            @QueryParam(value = "fname") String fname,
            @QueryParam(value = "lname") String lname,
            @QueryParam(value = "description") String description) {
        LOG.log(Level.INFO, "Updating profile email: {0}, fname: {1}, lname: {2}, description: {3}", new Object[]{email, fname, lname, description});

        String username = session.getSessionID();
        InstaFlickUser user = instaFlick.getUserRegistry().find(username);

        LOG.log(Level.INFO, "\t Updating profile for user {0}", user.getUsername());

        user.setEmail(email);
        user.setFname(fname);
        user.setLname(lname);
        user.setDescription(description);
        instaFlick.getUserRegistry().update(user);

        return Response.ok().build();
    }

    @GET
    @Path("userprofile")
    public Response getUserProfile(@QueryParam("username") String username) {
        LOG.log(Level.INFO, "Getting profile info for {0}", username);

        InstaFlickUser user = instaFlick.getUserRegistry().find(username);
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("username", user.getUsername());
        builder.add("email", user.getEmail());
        builder.add("fname", user.getFname());
        builder.add("lname", user.getLname());
        builder.add("description", user.getDescription());

        return Response.ok(builder.build()).build();
    }

    @GET
    @Path(value = "session")
    public Response getSession() {
        LOG.log(Level.INFO, "Getting the client session");
        if (!session.getSession()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        String username = session.getSessionID();
        JsonObject value = Json.createObjectBuilder().add("username", username).build();
        return Response.ok(value).build();
    }

    @GET
    public Response login(@QueryParam(value = "username") String username,
            @QueryParam(value = "password") String password) {
        LOG.log(Level.INFO, "{0} trying to log in with password {1}", new Object[]{username, password});
        InstaFlickUser user = instaFlick.getUserRegistry().find(username);
        if (user == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        if (!user.getPassword().equals(password)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        session.setSession(true);
        session.setSessionID(username);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path(value = "logout")
    public Response logout() {
        LOG.log(Level.INFO, "Logging out");
        session.setSession(false);
        session.setSessionID(null);
        return Response.ok().build();
    }

    @GET
    @Path(value = "loggedIn")
    public Response isLoggedIn() {
        LOG.log(Level.INFO, "Checking session");
        JsonObject value = Json.createObjectBuilder().add("loggedIn", session.getSession()).build();
        return Response.ok(value).build();
    }

}
