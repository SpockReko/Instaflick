/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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

    @Inject
    private InstaFlick instaFlick;

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());
    
    @GET
    public Response getImagePath() {
        PictureCatalogue pc = instaFlick.getPictureCatalogue();
        
        pc.find(Long.MIN_VALUE);
        
        return Response.ok("media/image1.png").build();
    }
}
