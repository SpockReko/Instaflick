package se.webapp.instaflickr.model.user;

import se.webapp.instaflickr.model.persistence.InstaFlick;
import se.webapp.instaflickr.model.persistence.SessionHandler;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    @Path("userprofile")
    public Response getUserProfile(@QueryParam("username") String username) {
        LOG.log(Level.INFO, "getUserProfile(): " + username);

        InstaFlickUser user = instaFlick.getUserRegistry().find(username);
        LOG.log(Level.INFO, "got user: " + user.getUsername());
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
        LOG.warning("Getting the session");
        if (!session.getSession()) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

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
    public Response login(@QueryParam(value = "username") String username,
            @QueryParam(value = "password") String password) {
        LOG.warning("User trying to log in: " + username + " " + password);
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

    @POST
    public Response create(@QueryParam(value = "username") String username,
            @QueryParam(value = "password") String password,
            @QueryParam(value = "repeatPassword") String repeatPassword) {
        LOG.log(Level.INFO, "Insert {0} {1}", new Object[]{username, password, repeatPassword});
        LOG.warning("Creating new user " + username + " " + password + " " + repeatPassword);
        InstaFlickUser exists = instaFlick.getUserRegistry().find(username);
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
        LOG.warning("Setting up profile: " + email + " " + fname + " " + lname + " " + description);

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

    @GET
    @Path(value = "update")
    public Response updateProfile(@QueryParam(value = "email") String email,
            @QueryParam(value = "fname") String fname,
            @QueryParam(value = "lname") String lname,
            @QueryParam(value = "description") String description) {

        LOG.log(Level.INFO, "Update called" + lname);

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
