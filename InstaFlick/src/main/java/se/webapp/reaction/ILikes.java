/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.reaction;

import se.webapp.instaflick.User;

/**
 *
 * @author Pontus
 */
public interface ILikes {
    public int nrOfLikes();
    public boolean removeLike(User user);
}
