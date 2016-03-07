/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.webapp.instaflickr.model.media.AbstractMedia;
import se.webapp.instaflickr.model.persistence.AbstractDAO;

/**
 *
 * @author TH
 */
@Stateless
public class MediaHandler extends AbstractDAO<AbstractMedia,Long> {
    @PersistenceContext  // Container managed EM
    private EntityManager em;

    public MediaHandler() {
        super(AbstractMedia.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public boolean comment(Long mediaId, String comment){
        return false;
    }

}


