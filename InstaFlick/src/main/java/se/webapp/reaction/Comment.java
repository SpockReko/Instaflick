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

    IUser user;
    String comment;
    ILikes like;
    
    
    @Override
    public boolean removeComment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
