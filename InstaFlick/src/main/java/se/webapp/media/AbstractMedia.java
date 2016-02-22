/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.media;

import java.util.List;
import se.webapp.instaflick.IUser;
import se.webapp.reaction.IComment;
import se.webapp.reaction.ILikes;

/**
 *
 * @author Pontus
 */
public class AbstractMedia {
    
    ILikes likes;
    List<IComment> comments;
    
    public boolean postComment(IUser user, String comment) {
        //IComment comment = new IComment(user, comment);
        return true;
    }
    
    public boolean likeIt(IUser user) {
        
        
        return true;
    }
    
}
