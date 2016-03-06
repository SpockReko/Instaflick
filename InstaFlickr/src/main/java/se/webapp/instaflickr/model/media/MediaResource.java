/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
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
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import se.webapp.instaflickr.model.InstaFlick;
import se.webapp.instaflickr.model.PictureCatalogue;
import se.webapp.instaflickr.model.UserRegistry;
import se.webapp.instaflickr.model.UserResource;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Henry
 */
@Path("media")
public class MediaResource {

    @Context
    private UriInfo uriInfo;

    @Context
    private ServletContext context;

    @Inject
    private InstaFlick instaFlick;

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getImagePath(@QueryParam(value = "username") String email) {
        PictureCatalogue pc = instaFlick.getPictureCatalogue();
        UserRegistry ur = instaFlick.getUserRegistry();
        
        InstaFlickUser user = ur.find(email);
        List<Picture> pictures = user.getPictures(); // Doesn't work
        pictures = pc.findPicturesByUser(user);
        
        /*
        if(pictures.size() == 0) {
            LOG.log(Level.INFO, "HERE");
            return Response.status(Response.Status.NO_CONTENT).build();
        }
*/      
        JsonObjectBuilder builder = Json.createObjectBuilder();
        
        for(Picture p : pictures) {
        }

        return Response.ok(builder.build()).build();
    }

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadImage(@FormDataParam("email") String email,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {

        // Get the picture catalogue
        PictureCatalogue pc = instaFlick.getPictureCatalogue();

        // Generate paths
        String fileName = fileMetaData.getFileName().replace(' ', '_');
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String cleanEmail = email.replace("@", "_at_");
        java.nio.file.Path localPath = generateLocalPath(cleanEmail);
        java.nio.file.Path relativePath = generateRelativePath(cleanEmail);

        // Find the user
        InstaFlickUser user = instaFlick.getUserRegistry().find(email);
        
        // Add new picture to the database
        Picture picture = new Picture(user, relativePath.toString());
        pc.create(picture);
        user.addPicture(picture); // Doesn't work
        
        // Save the pictures as: pictureId
        fileName = String.valueOf(picture.getId());

        // Write the file to disk
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            File file = new File(localPath + "/" + fileName + "/" + fileName + "-original" + fileExtension);
            file.getParentFile().mkdirs();

            OutputStream out = new FileOutputStream(file);
            while ((read = fileInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            
            // Generate thumbnail
            Thumbnails.of(file)
                    .size(200, 200)
                    .toFile(new File(file.getParent() + "/" + fileName + "-thumbnail" + fileExtension));
            
        } catch (IOException e) {
            throw new WebApplicationException("Error while uploading file. Please try again!!");
        }

        return Response.ok(relativePath + "/" + fileName + "/" + fileName + fileExtension).build();
    }

    public java.nio.file.Path generateRelativePath() {
        java.nio.file.Path contextPath, localPath, relativePath;

        contextPath = Paths.get(context.getRealPath("/"));
        localPath = Paths.get(contextPath.getParent().getParent().toString(), "src/main/webapp/instaflickr/app/media");
        relativePath = contextPath.getParent().getParent().relativize(localPath);
        relativePath = relativePath.subpath(5, relativePath.getNameCount());

        return relativePath;
    }

    public java.nio.file.Path generateRelativePath(String userId) {
        java.nio.file.Path contextPath, localPath, relativePath;

        contextPath = Paths.get(context.getRealPath("/"));
        localPath = Paths.get(contextPath.getParent().getParent().toString(), "src/main/webapp/instaflickr/app/media");
        relativePath = contextPath.getParent().getParent().relativize(localPath);
        relativePath = relativePath.subpath(5, relativePath.getNameCount());

        return Paths.get(relativePath.toString(), userId);
    }

    public java.nio.file.Path generateLocalPath(String userId) {
        java.nio.file.Path contextPath, localPath;

        contextPath = Paths.get(context.getRealPath("/"));
        localPath = Paths.get(contextPath.getParent().getParent().toString(), "src/main/webapp/instaflickr/app/media");

        return Paths.get(localPath.toString(), userId);
    }
}
