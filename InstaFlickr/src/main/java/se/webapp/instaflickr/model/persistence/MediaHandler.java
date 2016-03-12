package se.webapp.instaflickr.model.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.webapp.instaflickr.model.media.AbstractMedia;

@Stateless
public class MediaHandler extends AbstractDAO<AbstractMedia, Long> {

    @PersistenceContext  // Container managed EM
    private EntityManager em;

    public MediaHandler() {
        super(AbstractMedia.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public boolean comment(Long mediaId, String comment) {

        return false;
    }

}
