/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.webapp.instaflickr.model.media.Picture;
import se.webapp.instaflickr.model.persistence.AbstractDAO;

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
    
}
