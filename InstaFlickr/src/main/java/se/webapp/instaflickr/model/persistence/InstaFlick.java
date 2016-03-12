package se.webapp.instaflickr.model.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InstaFlick {

    public InstaFlick() {
        Logger.getAnonymousLogger().log(Level.INFO, "InstaFlick is alive");
    }
    @EJB
    private UserRegistry userRegistry;

    public UserRegistry getUserRegistry() {
        return userRegistry;
    }

    @EJB
    private PictureCatalogue pictureCatalogue;

    public PictureCatalogue getPictureCatalogue() {
        return pictureCatalogue;
    }

    @EJB
    private AlbumCatalogue albumCatalogue;

    public AlbumCatalogue getAlbumCatalogue() {
        return albumCatalogue;
    }

}
