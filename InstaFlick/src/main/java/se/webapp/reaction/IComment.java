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
public interface IComment {
    
        public void editComment(String comment);
        public IUser getUser(IUser user);

}
