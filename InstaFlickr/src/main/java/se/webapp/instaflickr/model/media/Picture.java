/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    private static final long serialVersionUID = 1L;
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

    public Picture(InstaFlickUser uploader, Likes likes) {
        this.uploader = uploader;
        this.likes = likes;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<Comment>();
    }

    public Picture(InstaFlickUser uploader, Likes likes, String path) {
        this.uploader = uploader;
        this.likes = likes;
        this.imagePath = path;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<Comment>();
    }

    public Picture(InstaFlickUser uploader, String path) {
        this.uploader = uploader;
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
