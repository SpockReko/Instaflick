package se.webapp.instaflickr.model.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.webapp.instaflickr.model.user.InstaFlickUser;

@Stateless
public class UserRegistry extends AbstractDAO<InstaFlickUser, String> {

    @PersistenceContext  // Container managed EM
    EntityManager em;

    public UserRegistry() {
        super(InstaFlickUser.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
