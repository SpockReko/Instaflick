/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import se.webapp.instaflickr.model.media.Picture;
import se.webapp.instaflickr.model.persistence.AbstractDAO;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Pontus
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
        Query query = em.createQuery( "SELECT p FROM Picture p WHERE p.uploader = ?1" );
        query.setParameter( 1, user );
        List<Picture> pictures = new ArrayList<>( query.getResultList());
        return pictures;
    }

    public Picture findPictureByPath(String path) {
        Picture picture = em.createQuery("SELECT p FROM Picture p WHERE p.path = '" + path + "'", Picture.class).getSingleResult();
        return picture;
    }
}
