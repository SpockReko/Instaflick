/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Pontus
 */
@Entity
public class Likes implements Serializable {

    static Long staticId = 4L;
    
    @Id
    private String id;
    @Setter
    @Getter
    public List<InstaFlickUser> userList;

    public Likes(){}
    
    /**
     * Returns the number of likes, which is the same as the number of users
     * in userList.
     * 
     * @return number of likes
     */
    public int nrOfLikes() {
        return userList.size();
    }
    
    /**
     * Adds the user to the list of 'liking' users. If the user is already
     * in the list, it cannot be added again.
     * 
     * @param user the user to be added to the list
     * @return true if user is added to list, false if not
     */
    public boolean addLike(InstaFlickUser user) {
        if (userList.contains(user)) {
            return true;
        }
        return userList.add(user);
    }

    /**
     * Removes the specified user from the list of 'liking' users.
     * 
     * @param user the user to be removed
     * @return true if user was removed, false if user was not in the list
     */
    public boolean removeLike(InstaFlickUser user) {
        return userList.remove(user);
    }

    public List<InstaFlickUser> getUserList() {
        return userList;
    }

    public void setUserList(List<InstaFlickUser> userList) {
        this.userList = userList;
    }
    
}
