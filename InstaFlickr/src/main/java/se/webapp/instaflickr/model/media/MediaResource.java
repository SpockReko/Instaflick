package se.webapp.instaflickr.model.media;

import com.sun.messaging.jmq.io.Status;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import se.webapp.instaflickr.model.persistence.AlbumCatalogue;
import se.webapp.instaflickr.model.persistence.InstaFlick;
import se.webapp.instaflickr.model.persistence.PictureCatalogue;
import se.webapp.instaflickr.model.persistence.SessionHandler;
import se.webapp.instaflickr.model.persistence.UserRegistry;
import se.webapp.instaflickr.model.user.UserResource;
import se.webapp.instaflickr.model.user.InstaFlickUser;

@Path("media")
public class MediaResource {

    @Context
    private UriInfo uriInfo;

    @Context
    private ServletContext context;

    @Inject
    private InstaFlick instaFlick;

    @Inject
    private SessionHandler sessionHandler;

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    @GET
    @Path("picture")
    public Response getPicture(
            @QueryParam("pictureId") Long pictureId) {

        PictureCatalogue pc = instaFlick.getPictureCatalogue();
        Picture picture = pc.find(pictureId);

        Calendar calendar = picture.getUploaded();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String TextMonth;

        if (month < 10) {
            TextMonth = "0" + month;
        } else {
            TextMonth = "" + month;
        }

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + TextMonth + "-" + day;

        JsonObject pictureData = Json.createObjectBuilder()
                .add("path", picture.getImagePath() + "/" + picture.getId() + "/big.jpg")
                .add("date", date)
                .add("description", picture.getDescription())
                .build();

        return Response.ok(pictureData).build();
    }

    @POST
    @Path("album")
    public Response createAlbum(@QueryParam(value = "albumName") String albumName) {
        String username = sessionHandler.getSessionID();
        UserRegistry ur = instaFlick.getUserRegistry();
        InstaFlickUser user = ur.find(username);
        LOG.warning("Got user: " + user.getUsername());
        AlbumCatalogue ac = instaFlick.getAlbumCatalogue();
        Album album = ac.getAlbum(user, albumName);
        if (album != null) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            album = new Album(albumName, user);
            ac.create(album);
            user.addAlbum(album);
            ur.update(user);
            return Response.ok().build();
        }
    }

    @GET
    @Path("albums")
    public Response getAlbums() {
        String username = sessionHandler.getSessionID();
        UserRegistry ur = instaFlick.getUserRegistry();
        InstaFlickUser user = ur.find(username);
        List<Album> albums = user.getAlbums();

        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Album a : albums) {
            builder.add(Json.createObjectBuilder().add("albumName", a.getName()));
        }

        return Response.ok(builder.build()).build();
    }

    @GET
    @Path("album-pictures")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAlbumPictures(@QueryParam(value = "username") String username,
            @QueryParam(value = "albumName") String albumName) {

        UserRegistry ur = instaFlick.getUserRegistry();
        AlbumCatalogue ac = instaFlick.getAlbumCatalogue();
        InstaFlickUser user = ur.find(username);

        Album album = ac.getAlbum(user, albumName);

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (Picture p : album.getPictures()) {
            builder.add(Json.createObjectBuilder()
                    .add("path", p.getImagePath() + "/" + p.getId() + "/thumbnail.jpg").add("id", p.getId()));

        }

        return Response.ok(builder.build()).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getProfileImages(@QueryParam(value = "username") String username) {
        UserRegistry ur = instaFlick.getUserRegistry();
        InstaFlickUser user = ur.find(username);
        LOG.warning(user.getUsername());

        List<Picture> pictures = user.getPictures();
        List<Album> albums = user.getAlbums();

        List<List<Picture>> albumPictures = listAlbumPictures(albums); // Turns the albums into a list
        // of their pictures

        pictures = removeDuplicates(pictures, albumPictures); // Removing pictures that exists in albums

        JsonArrayBuilder builder = createPictureArray(pictures, albumPictures, albums);

        return Response.ok(builder.build()).build();
    }

    //********* GETALLMEDIA
    @GET
    @Path("media")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMedia() {
        LOG.log(Level.INFO, "Getting all media in MediaResource");
        PictureCatalogue pc = instaFlick.getPictureCatalogue();
        AlbumCatalogue ac = instaFlick.getAlbumCatalogue();

        List<Picture> allPictures = pc.findAll();
        List<Album> allAlbums = ac.findAll();

        List<List<Picture>> albumPictures = listAlbumPictures(allAlbums); // Turns the albums into a
        // list of their pictures

        allPictures = removeProfilePictures(allPictures); // Removing profile pictures

        allPictures = removeDuplicates(allPictures, albumPictures); // Removing pictures that exists in
        // 
        JsonArrayBuilder builder = createPictureArray(allPictures, albumPictures, allAlbums);

        return Response.ok(builder.build()).build();
    }

    // ************ GETFEED                                                          
    @GET
    @Path("feed")
    public Response getFeed(@QueryParam(value = "username") String username) {
        LOG.log(Level.INFO, "Getting feed for " + username);

        UserRegistry ur = instaFlick.getUserRegistry();
        PictureCatalogue pc = instaFlick.getPictureCatalogue();
        AlbumCatalogue ac = instaFlick.getAlbumCatalogue();

        InstaFlickUser user = ur.find(username);

        List<Picture> allPictures = pc.findAll();
        List<Album> allAlbums = ac.findAll();

        // Remove pictures that user has created
        List<Picture> filteredPictures = new ArrayList();
        for (Picture p : allPictures) {
            if (p.getOwner() != null && !p.getOwner().getUsername().equals(user.getUsername())) {
                filteredPictures.add(p);
            }
        }

        // Remove albums that user has created
        List<Album> filteredAlbums = new ArrayList();
        for (Album a : allAlbums) {
            if (!a.getOwner().getUsername().equals(user.getUsername())) {
                filteredAlbums.add(a);
            }
        }

        List<List<Picture>> albumPictures = listAlbumPictures(filteredAlbums);

        allPictures = removeProfilePictures(filteredPictures); // Removing profile pictures
        allPictures = removeDuplicates(filteredPictures, albumPictures); // Removing pictures that exists in albums

        JsonArrayBuilder builder = createPictureArray(allPictures, albumPictures, filteredAlbums);

        return Response.ok(builder.build()).build();
    }

    // GETPROFILEPICTURE
    @GET
    @Path("profile-image")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getProfilePicture(@QueryParam(value = "username") String username) {
        UserRegistry ur = instaFlick.getUserRegistry();
        InstaFlickUser user = ur.find(username);
        LOG.warning(user.getUsername());

        Picture profilePicture = user.getProfilePicture();
        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (profilePicture != null) {
            builder.add("image", profilePicture.getImagePath() + "/profile.jpg");
        } else {
            LOG.log(Level.INFO, username + " has not set a profile picture");
            LOG.log(Level.INFO, generateRelativePath().toString());
            builder.add("image", generateRelativePath().toString() + "/default.jpg");
        }

        return Response.ok(builder.build()).build();
    }

    // UPLOADIMAGE
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadImage(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("albumName") String albumName,
            @FormDataParam("description") String description) throws Exception {

        // Get session
        String username = sessionHandler.getSessionID();
        LOG.warning("Got session: " + username);

        // Get the picture catalogue
        PictureCatalogue pc = instaFlick.getPictureCatalogue();

        // Generate paths
        String imageId = fileMetaData.getFileName().replace(' ', '_');
        String fileExtension = imageId.substring(imageId.lastIndexOf("."));
        String cleanUsername = username.replace("@", "_at_");
        java.nio.file.Path localPath = generateLocalPath(cleanUsername);
        java.nio.file.Path relativePath = generateRelativePath(cleanUsername);

        // Find the user
        UserRegistry ur = instaFlick.getUserRegistry();
        InstaFlickUser user = ur.find(username);
        LOG.warning("Got user: " + user.getUsername());
        // Add new picture to the database
        Picture picture = new Picture(user, relativePath.toString(), description);
        pc.create(picture);
        user.addPicture(picture);
        ur.update(user);

        // Save the pictures as: pictureId
        imageId = String.valueOf(picture.getId());

        // Write the file to disk
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            File file = new File(localPath + "/" + imageId + "/" + "tmp" + fileExtension);
            file.getParentFile().mkdirs();

            OutputStream out = new FileOutputStream(file);
            while ((read = fileInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

            // Generate big image
            Thumbnails.of(file)
                    .size(1200, 800)
                    .outputFormat("jpg")
                    .toFile(new File(file.getParent() + "/" + "big"));

            // Generate thumbnail
            Thumbnails.of(file)
                    .crop(Positions.CENTER)
                    .size(200, 200)
                    .outputFormat("jpg")
                    .toFile(new File(file.getParent() + "/" + "thumbnail"));

            try {
                Files.delete(file.toPath());
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", file.toPath());
            } catch (IOException x) {
                System.err.println(x);
            }

            if (!albumName.isEmpty()) {
                LOG.warning("Adding picture to album: " + albumName);
                addPictureToAlbum(user, albumName, picture);
            }
        } catch (IOException e) {
            throw new WebApplicationException("Error while uploading file. Please try again!!");
        }

        return Response.ok(relativePath + "/" + imageId + "/" + "thumbnail.jpg").build();
    }

    @POST
    @Path("profile-image")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadProfileImage(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {
        LOG.warning("Uploading profile picture");

        // Get session
        String username = sessionHandler.getSessionID();
        LOG.warning("Got session: " + username);

        // Get the picture catalogue
        PictureCatalogue pc = instaFlick.getPictureCatalogue();

        // Generate paths
        String imageId = fileMetaData.getFileName().replace(' ', '_');
        String fileExtension = imageId.substring(imageId.lastIndexOf("."));
        String cleanUsername = username.replace("@", "_at_");
        java.nio.file.Path localPath = generateLocalPath(cleanUsername);
        java.nio.file.Path relativePath = generateRelativePath(cleanUsername);

        // Find the user
        UserRegistry ur = instaFlick.getUserRegistry();
        InstaFlickUser user = ur.find(username);
        LOG.warning("Got user: " + user.getUsername());
        // Add new picture to the database
        Picture picture = new Picture(null, relativePath.toString(), null);
        pc.create(picture);
        user.setProfilePicture(picture);
        ur.update(user);

        // Write the file to disk
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            File file = new File(localPath + "/" + "tmp" + fileExtension);
            file.getParentFile().mkdirs();

            OutputStream out = new FileOutputStream(file);
            while ((read = fileInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

            // Generate thumbnail
            Thumbnails.of(file).size(200, 200).outputFormat("jpg")
                    .toFile(new File(file.getParent() + "/" + "profile"));

        } catch (IOException e) {
            throw new WebApplicationException("Error while uploading file. Please try again!!");
        }

        return Response.ok(relativePath + "/" + "profile.jpg").build();

    }
    // Helper functions

    public java.nio.file.Path generateRelativePath() {
        java.nio.file.Path contextPath, localPath, relativePath;

        contextPath = Paths.get(context.getRealPath("/"));
        localPath = Paths.get(contextPath.getParent().getParent().toString(),
                "src/main/webapp/instaflickr/app/media");
        relativePath = contextPath.getParent().getParent().relativize(localPath);
        relativePath = relativePath.subpath(5, relativePath.getNameCount());

        return relativePath;
    }

    public java.nio.file.Path generateRelativePath(String userId) {
        java.nio.file.Path contextPath, localPath, relativePath;

        contextPath = Paths.get(context.getRealPath("/"));
        localPath = Paths.get(contextPath.getParent().getParent().toString(),
                "src/main/webapp/instaflickr/app/media");
        relativePath = contextPath.getParent().getParent().relativize(localPath);
        relativePath = relativePath.subpath(5, relativePath.getNameCount());

        return Paths.get(relativePath.toString(), userId);
    }

    public java.nio.file.Path generateLocalPath(String userId) {
        java.nio.file.Path contextPath, localPath;

        contextPath = Paths.get(context.getRealPath("/"));
        localPath = Paths.get(contextPath.getParent().getParent().toString(),
                "src/main/webapp/instaflickr/app/media");

        return Paths.get(localPath.toString(), userId);
    }

    public boolean addPictureToAlbum(InstaFlickUser user, String albumName, Picture picture) {
        AlbumCatalogue ac = instaFlick.getAlbumCatalogue();
        Album album = ac.getAlbum(user, albumName);
        LOG.warning("Got album: " + album.getName());
        album.addPicture(picture);
        ac.update(album);
        return true;
    }

    // Turns the albums in the list into lists of their picutres
    public List<List<Picture>> listAlbumPictures(List<Album> albums) {
        List<List<Picture>> albumPictures = new ArrayList();
        for (Album a : albums) {
            LOG.log(Level.INFO, "Album name: " + a.getName());
            LOG.log(Level.INFO, "Nr of pics in album: " + a.nrOfPictures());
            albumPictures.add(a.getPictures());
        }
        return albumPictures;
    }

    // Creates a list with the picutres that does not exist in albumPictures.
    public List<Picture> removeDuplicates(List<Picture> allPictures,
            List<List<Picture>> albumPictures) {
        List<Picture> pictures = new ArrayList<>();
        for (Picture p : allPictures) {
            Boolean duplicate = false;
            for (List<Picture> list : albumPictures) {
                for (Picture q : list) {
                    if (Objects.equals(q.getId(), p.getId())) {
                        LOG.log(Level.INFO, "Duplicate found. Id: " + p.getId());
                        duplicate = true;
                    }
                }
            }
            if (!duplicate) {
                pictures.add(p);
            }
        }
        return pictures;
    }

    // Removes profile pictures
    public List<Picture> removeProfilePictures(List<Picture> pictures) {
        List<Picture> result = new ArrayList<>();
        for (Picture p : pictures) {
            if (p.getOwner() != null) {
                result.add(p);
            }
        }
        return result;
    }

    public JsonArrayBuilder createPictureArray(List<Picture> pictures, List<List<Picture>> albumPictures, List<Album> albums) {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (Picture p : pictures) {
            builder.add(Json.createObjectBuilder()
                    .add("path", p.getImagePath() + "/" + p.getId() + "/thumbnail.jpg")
                    .add("id", p.getId())
                    .add("type", "image")
                    .add("time", p.getUploaded().getTimeInMillis())
                    .add("uploader", p.getOwner().getUsername()));

            p.getOwner();
        }

        int index = 0;
        for (List<Picture> pList : albumPictures) {
            JsonObjectBuilder albumBuilder = Json.createObjectBuilder();
            albumBuilder.add("albumName", albums.get(index).getName());
            albumBuilder.add("uploader", albums.get(index).getOwner().getUsername());
            albumBuilder.add("type", "album");
            albumBuilder.add("time", pList.get(pList.size() - 1).getUploaded().getTimeInMillis());

            JsonArrayBuilder innerBuilder = Json.createArrayBuilder();

            for (int i = 0; i < pList.size() && i < 4; i++) {
                innerBuilder.add(Json.createObjectBuilder()
                        .add("path", pList.get(i).getImagePath() + "/" + pList.get(i).getId() + "/thumbnail.jpg")
                        .add("id", pList.get(i).getId()));
            }

            albumBuilder.add("pictureList", innerBuilder);

            builder.add(albumBuilder);

            index++;
        }
        return builder;
    }

    @POST
    @Path("comment")
    public Response postComment(@QueryParam("picture") long pictureId,
            @QueryParam("comment") String comment) {
        InstaFlickUser usr = instaFlick.getUserRegistry().find(sessionHandler.getSessionID());
        if (usr == null) {
            return Response.notModified("Could not find user!").build();
        }
        Picture pic = instaFlick.getPictureCatalogue().findPictureById(pictureId);
        if (pic == null) {
            return Response.notModified("Could not find picture!").build();
        }

        pic.comment(usr, comment);
        return Response.accepted().build();

    }

    @GET
    @Path("comments")
    public Response getComments(@QueryParam("picture") long pictureId) {

        Picture pic = instaFlick.getPictureCatalogue().findPictureById(pictureId);
        if (pic == null) {
            return Response.status(Status.UNAVAILABLE).build();
        } else {
            return Response.ok(pic.getComments()).build();
        }

    }

}
