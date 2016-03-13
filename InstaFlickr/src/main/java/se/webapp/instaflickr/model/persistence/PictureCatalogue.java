package se.webapp.instaflickr.model.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import se.webapp.instaflickr.model.media.Picture;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 * This is what handles the pictures connection to the database.
 */
@Stateless
public class PictureCatalogue extends AbstractDAO<Picture, Long> {

    @PersistenceContext  // Container managed EM
    private EntityManager em;

    public PictureCatalogue() {
        super(Picture.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Picture> findPicturesByUser(InstaFlickUser user) {
        Query query = em.createQuery("SELECT p FROM Picture p WHERE p.uploader = ?1");
        query.setParameter(1, user);
        List<Picture> pictures = new ArrayList<>(query.getResultList());
        return pictures;
    }

    public Picture findPictureById(Long id) {
        Picture picture = em.createQuery("SELECT p FROM Picture p WHERE p.id = ?1", Picture.class).setParameter(1, id).getSingleResult();
        return picture;
    }

    public Picture findPictureByPath(String path) {
        Picture picture = em.createQuery("SELECT p FROM Picture p WHERE p.path = '" + path + "'", Picture.class).getSingleResult();
        return picture;
    }

}
