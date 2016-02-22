/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.reaction;

import se.webapp.instaflick.IUser;

/**
 *
 * @author Stefan
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
        this.comment = comment;
    }

    @Override
    public IUser getUser(IUser user) {
        return this.user;
    }
    
}
