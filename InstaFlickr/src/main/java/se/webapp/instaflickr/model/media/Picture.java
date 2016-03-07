/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
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
    EntityManager em;
    
    @Getter
    @Setter
    private String imagePath;

    public Picture() {
    } // Används ej.

    public Picture(InstaFlickUser owner, Likes likes) {
        this.owner = owner;
        this.likes = likes;

        this.uploaded = getNow();
        this.comments = new LinkedList<>();
    }

    public Picture(InstaFlickUser owner, Likes likes, String path) {
        this.owner = owner;
        this.likes = likes;
        this.imagePath = path;

        this.uploaded = getNow();
        this.comments = new LinkedList<>();
    }

    public Picture(InstaFlickUser owner, String path) {
        this.owner = owner;
        //this.likes = new Likes();
        this.imagePath = path;
        this.uploaded = getNow();
        this.comments = new LinkedList<>();
    }

    public void postComment(InstaFlickUser user, String comment) {
        Comment newComment = new Comment(user, comment, new Likes());
        comments.add(newComment);
    }


    private Calendar getNow() {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);
        return newCalendar;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
