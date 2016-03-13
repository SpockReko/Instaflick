package se.webapp.instaflickr.model.persistence;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.webapp.instaflickr.model.media.Likes;
import se.webapp.instaflickr.model.user.UserResource;

/**
 * This is what handles the adding of likes to the database.
 */
@Stateless
public class LikesHandler extends AbstractDAO<Likes, Long> {

    @PersistenceContext  // Container managed EM
    private EntityManager em;

    public LikesHandler() {
        super(Likes.class);
    }

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public int nrOfLike(Long likesId) {
        Likes likes = getLikes(likesId);
        return likes.nrOfLikes();
    }

    public List getLikeList(Long likeId) {
        Likes likes = getLikes(likeId);
        List userlist = likes.getUserList();
        return userlist;
    }

    private Likes getLikes(Long likeId) {
        Likes likes = em.find(Likes.class, likeId);
        return likes;
    }
}
