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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.UserResource;
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

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    // Används ej.
    public Picture() {
        LOG.warning("*******************************************************");
        LOG.warning("DO NOT USE THIS CONSTRUCTOR! Picture(),");
        LOG.warning("Use Picture(InstaflickUser, Likes) or ");
        LOG.warning("Use Picture(InstaflickUser, Likes, String) or ");
        LOG.warning("*******************************************************");
    } // Används ej.

    public Picture(InstaFlickUser owner) {
        this.owner = owner;
        this.imagePath = null;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<Comment>();
        this.description = "";
    }

    public Picture(InstaFlickUser owner, String path, String description) {
        this.owner = owner;
        this.imagePath = path;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<Comment>();

        if (description == null) {
            this.description = "";
        } else {
            this.description = description;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

}
