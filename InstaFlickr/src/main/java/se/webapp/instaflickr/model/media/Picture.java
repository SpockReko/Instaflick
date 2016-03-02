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
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
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
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar uploaded;
    @Getter
    @Setter
    @OneToOne
    private InstaFlickUser uploader;
    @Getter
    @Setter
    private Likes likes;

    
    public Picture() {} // Används ej.

    public Picture(InstaFlickUser uploader, Likes likes) {
        this.uploader = uploader;
        this.likes = likes;
        this.uploaded = getNow();
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

    private Calendar getNow(){
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);
        return newCalendar;
    }
}
