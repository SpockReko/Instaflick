/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.webapp.instaflickr.model.persistence.AbstractDAO;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Pontus
 */
//@Stateless
public class InstaFlickUserRegistry extends AbstractDAO<InstaFlickUser, String> implements IInstaFlickUserRegistry {

    @PersistenceContext  // Container managed EM
    private EntityManager em;

    public InstaFlickUserRegistry() {
        super(InstaFlickUser.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
