/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.media;

import se.webapp.instaflick.IUser;

/**
 *
 * @author Pontus
 */
public interface IPicture {
    
    public boolean postComment(IUser user, String comment);
    
    public boolean likeIt(IUser user);
    
    public boolean unLikeIt(IUser user);
}
