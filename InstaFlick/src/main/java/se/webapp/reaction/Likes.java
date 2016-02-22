/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.reaction;

import java.util.ArrayList;
import java.util.List;
import se.webapp.instaflick.IUser;

/**
 *
 * @author Pontus
 */
public class Likes implements ILikes {

    public List<IUser> userList = new ArrayList<>();
    
    @Override
    public int nrOfLikes() {
        return userList.size();
    }

    @Override
    public boolean removeLike(IUser user) {
        return userList.remove(user);
    }
    
}
