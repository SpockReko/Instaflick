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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.reaction.Comment;
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
    private List<Comment> comments;
    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar uploaded;
    @Getter
    @Setter
    @OneToOne
    private InstaFlickUser uploader;
    @Getter
    @Setter
    private Likes likes;


    public Picture() {
    } // Används ej.

    public Picture(InstaFlickUser owner, Likes likes) {
        this.owner = owner;
        this.likes = likes;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<Comment>();


    }

    public Picture(InstaFlickUser owner, Likes likes, String path) {
        this.owner = owner;
        this.likes = likes;
        this.imagePath = path;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<Comment>();

    }

    public Picture(InstaFlickUser owner, String path) {
        this.owner = owner;
        //this.likes = new Likes();
        this.imagePath = path;
        this.uploaded = Calendar.getInstance();

        this.comments = new LinkedList<Comment>();
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
