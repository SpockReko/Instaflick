/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.reaction;

import se.webapp.instaflick.IUser;

/**
 *
 * @author Pontus
 */
public class Comment implements IComment {

    private IUser user;
    String comment;
    private ILikes like;
    
    public Comment(IUser user, String comment){
        this.user = user;
        this.comment = comment;
        this.like = new Likes();
    }
    
    @Override
    public void editComment(String comment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IUser getUser(IUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
