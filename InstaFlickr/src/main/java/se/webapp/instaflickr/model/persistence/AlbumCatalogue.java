package se.webapp.instaflickr.model.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import se.webapp.instaflickr.model.media.Album;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 * This is the interface for albums to the database. It handles queries going to
 * the DB.
 */
@Stateless
public class AlbumCatalogue extends AbstractDAO<Album, String> {

    @PersistenceContext  // Container managed EM
    private EntityManager em;

    public AlbumCatalogue() {
        super(Album.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Album getAlbum(InstaFlickUser user, String albumName) {
        Query query = em.createQuery("SELECT a FROM Album a WHERE a.owner = ?1 AND a.name = ?2");
        query.setParameter(1, user);
        query.setParameter(2, albumName);
        List<Album> albums = new ArrayList<>(query.getResultList());
        Album album = null;
        if (!albums.isEmpty()) {
            album = albums.get(0);
        }
        return album;
    }

    public List<Album> getAlbums(InstaFlickUser user) {
        Query query = em.createQuery("SELECT a FROM Album a WHERE a.owner = ?1");
        query.setParameter(1, user);
        List<Album> albums = new ArrayList<>(query.getResultList());
        return albums;
    }

    public List<Long> getPictureIds(InstaFlickUser user, String albumName) {
        Query query = em.createNativeQuery("SELECT PICTURES_ID FROM ALBUM_PICTURE WHERE OWNER_USERNAME = ?1 AND NAME = ?2");
        query.setParameter(1, user.getUsername());
        query.setParameter(2, albumName);

        List<Long> pictureIds = query.getResultList();

        return pictureIds;
    }

}
