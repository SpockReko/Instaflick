/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.persistence;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.webapp.instaflickr.model.media.Likes;
import se.webapp.instaflickr.model.user.UserResource;

/**
 *
 * @author Pontus
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

    /*      // Försökte mig på att updatera med hjälp av Querys till databasen.
    public int updateLikes(String user, Likes likes){
        int max = em.createQuery("INSERT INTO BASE.LIKES_INSTAFLICKUSER VALUES (value1,value2)")
                            .setParameter("value1", likes)
                            .setParameter("value2", user)
                            .getMaxResults();
        LOG.warning("Insert Query give this value: " + max);
        return 1;
    }
     */
    // Nummer av likes från databasen 
    public int nrOfLike(Long likesId) {
        Likes likes = getLikes(likesId);
        return likes.nrOfLikes();
    }

    // Listan av likes från databasen    
    public List getLikeList(Long likeId) {
        Likes likes = getLikes(likeId);
        List userlist = likes.getUserList();
        return userlist;
    }

    //help methods
    private Likes getLikes(Long likeId) {
        Likes likes = em.find(Likes.class, likeId);
        return likes;
    }
}
    
