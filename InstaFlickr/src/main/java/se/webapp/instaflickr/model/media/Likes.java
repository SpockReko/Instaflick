/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Entity
public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @Getter
    public List<String> userList;

    public Likes() {
        userList = new LinkedList<String>();
    }

    /**
     * Returns the number of likes, which is the same as the number of users in
     * userList.
     *
     * @return number of likes
     */
    public int nrOfLikes() {
        return userList.size();
    }

    /**
     * Adds the user to the list of 'liking' users. If the user is already in
     * the list, it cannot be added again.
     *
     * @param user the user to be added to the list
     * @return true if user is added to list, false if not
     */
    
    // Lägger till användare till listan med hjälp av användrnamnet
    public boolean addLike(String user) {
        return userList.add(user);
    }

    /**
     * Removes the specified user from the list of 'liking' users.
     *
     * @param user the user to be removed
     * @return true if user was removed, false if user was not in the list
     */
    
    // Tar bort till användare till listan med hjälp av användrnamnet
    public boolean removeLike(String user) {
        return userList.remove(user);
        
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

}
