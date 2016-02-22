/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.media;

import java.util.List;
import se.webapp.instaflick.IUser;
import se.webapp.reaction.Comment;
import se.webapp.reaction.IComment;
import se.webapp.reaction.ILikes;

/**
 *
 * @author Pontus
 */
public class AbstractMedia {
    
    ILikes likes;
    List<IComment> comments;
    
    public boolean postComment(IUser user, String mgs) {
        IComment comment = new Comment(user, mgs);
        comments.add(comment);
        return true;
    }
    
    public boolean removeComment(IUser user, IComment comment) {
    
        if (comment.getUser(user) == user) {
            comments.remove(comment);
            return true;
        }
        return false;
    }
    
    public boolean likeIt(IUser user) {
        return likes.addLike(user);
    }
    
    public boolean UnlikeIt(IUser user) {
        return likes.removeLike(user);
    }
}
