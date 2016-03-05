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
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import se.webapp.instaflickr.model.InstaFlick;
import se.webapp.instaflickr.model.PictureCatalogue;
import se.webapp.instaflickr.model.UserResource;

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
    public Response getImagePath() {
        PictureCatalogue pc = instaFlick.getPictureCatalogue();

        pc.find(Long.MIN_VALUE);

        return Response.ok("media/wiifitcap.jpg").build();
    }

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadImage(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {
        PictureCatalogue pc = instaFlick.getPictureCatalogue();

        pc.find(Long.MIN_VALUE);

        LOG.log(Level.INFO, "uploadImage() called");

        java.nio.file.Path localFilePath = Paths.get(Paths.get(context.getRealPath("/")).getParent().getParent().toString() + "/src/main/webapp/instaflickr/app/media/");
        java.nio.file.Path relativeFilePath = Paths.get(context.getRealPath("/")).getParent().getParent().relativize(localFilePath);
        relativeFilePath = relativeFilePath.subpath(5, relativeFilePath.getNameCount());
        
        LOG.log(Level.INFO, relativeFilePath.toString());

        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            File file = new File(localFilePath + "/" + fileMetaData.getFileName());
            LOG.log(Level.INFO, "Upload File Path : " + localFilePath.toString());
            OutputStream out = new FileOutputStream(file);
            while ((read = fileInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new WebApplicationException("Error while uploading file. Please try again !!");
        }
        return Response.ok(relativeFilePath + "/" + fileMetaData.getFileName()).build();
    }
}
