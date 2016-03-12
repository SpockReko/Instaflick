/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.UserResource;
import se.webapp.instaflickr.model.reaction.Likes;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Pontus
 */
@Entity
public class Picture extends AbstractMedia implements Serializable {

    @PersistenceContext
    static EntityManager em;

    @Getter
    @Setter
    private String imagePath;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private List<Comment> comments;
    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar uploaded;
    @Getter
    @Setter
    private Likes likes;

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    // Används ej.
    public Picture() {
        LOG.warning("*******************************************************");
        LOG.warning("DO NOT USE THIS CONSTRUCTOR! Picture(),");
        LOG.warning("Use Picture(InstaflickUser, Likes) or ");
        LOG.warning("Use Picture(InstaflickUser, Likes, String) or ");
        LOG.warning("*******************************************************");
    } // Används ej.

    public Picture(InstaFlickUser owner, Likes likes) {
        this.owner = owner;
        this.imagePath = null;
        this.likes = likes;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<Comment>();
        this.description = "";
    }

    public Picture(InstaFlickUser owner, Likes likes, String path, String description) {
        this.owner = owner;
        this.imagePath = path;
        this.likes = likes;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<Comment>();

        if (description == null) {
            this.description = "";
        } else {
            this.description = description;
        }
    }

    //Skickar LikesID. Kortar koden för den som kallar på denna.
    public long getLikesId() {
        return likes.getId();
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public void postComment(InstaFlickUser user, String comment) {
        Comment newComment = new Comment(user, comment, new Likes());
        comments.add(newComment);
    }

}
