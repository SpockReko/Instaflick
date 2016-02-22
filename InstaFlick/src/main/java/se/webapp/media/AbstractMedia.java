/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.media;

import java.util.List;
import se.webapp.reaction.IComment;
import se.webapp.reaction.ILikes;

/**
 *
 * @author Pontus
 */
public class AbstractMedia {
    
    ILikes like;
    List<IComment> comments;
    
    public boolean postComment(String comment) {
        // Todo
        return true;
    }
    
    public boolean likeIt() {
        // Todo
        return true;
    }
    
}
