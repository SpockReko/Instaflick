/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.util.List;
import javax.persistence.EntityManager;
import se.webapp.instaflickr.model.reaction.Comment;
import se.webapp.instaflickr.model.reaction.Likes;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Pontus
 */
public abstract class AbstractMedia {
    
    Likes likes;
    List<Comment> comments;

    protected abstract EntityManager getEntityManager();
    
    public boolean postComment(InstaFlickUser user, String mgs, Likes like) {
        Comment comment = new Comment(user, mgs, like);
        comments.add(comment);
        getEntityManager().persist(comment);
        return true;
    }
    
    public boolean removeComment(InstaFlickUser user, Comment comment) {
    
        if (comment.getUser().getEmail() == user.getEmail()) {
            comments.remove(comment);
            getEntityManager().remove(comment);
            return true;
        }
        return false;
    }
    
    public boolean likeIt(String user) {
        likes.addLike(user);
        getEntityManager().merge(likes);
        return true;
    }
    
    public boolean unLikeIt(String user) {
        likes.removeLike(user);
        getEntityManager().merge(likes);
        return true;
    }
}