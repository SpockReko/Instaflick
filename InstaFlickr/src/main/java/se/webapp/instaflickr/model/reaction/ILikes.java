/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

import se.webapp.instaflickr.model.user.IUser;

/**
 *
 * @author Pontus
 */
public interface ILikes {
    public int nrOfLikes();
    public boolean addLike(IUser user);
    public boolean removeLike(IUser user);
}
