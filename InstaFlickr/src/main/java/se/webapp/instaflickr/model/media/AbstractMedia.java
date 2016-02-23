/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.util.List;
import javax.persistence.EntityManager;
import se.webapp.instaflickr.model.user.IUser;
import se.webapp.instaflickr.model.reaction.Comment;
import se.webapp.instaflickr.model.reaction.IComment;
import se.webapp.instaflickr.model.reaction.ILikes;

/**
 *
 * @author Pontus
 */
public abstract class AbstractMedia {
    
    ILikes likes;
    List<IComment> comments;

    protected abstract EntityManager getEntityManager();
    
    public boolean postComment(IUser user, String mgs) {
        IComment comment = new Comment(user, mgs);
        comments.add(comment);
        getEntityManager().persist(comment);
        return true;
    }
    
    public boolean removeComment(IUser user, IComment comment) {
    
        if (comment.getUser(user) == user) {
            comments.remove(comment);
            getEntityManager().remove(comment);
            return true;
        }
        return false;
    }
    
    public boolean likeIt(IUser user) {
        likes.addLike(user);
        getEntityManager().merge(likes);
        return true;
    }
    
    public boolean unLikeIt(IUser user) {
        likes.removeLike(user);
        getEntityManager().merge(likes);
        return true;
    }
}
