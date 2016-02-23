package se.webapp.instaflickr.model.user;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.media.IPicture;
import se.webapp.instaflickr.model.media.Picture;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@Entity
public class User implements IUser, Serializable {
    
    static Long staticId = 0L;
    
    @Id
    private final String id;
    @Getter
    @Setter
    private IPicture picture;
    @Getter
    @Setter
    private String description;

    public User() {
        this.id = "User " + (++staticId).toString();
    }
    
    public User(String username){
        this.id = username;
        this.picture = new Picture();
    }
    
    public User(String username, IPicture picture){
        this.id = username;
        this.picture = picture;
    }
    
    @Override
    public boolean removeUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*
        TODO: Implement database connection to remove user.
        */
    }

}
