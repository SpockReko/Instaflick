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
public class InstaFlickUser implements IUser, Serializable {
    
    static Long staticId = 0L;
    
    @Id
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private IPicture picture;
    @Getter
    @Setter
    private String description;

    public InstaFlickUser() {
        this.userName = "User " + (++staticId).toString();
    }
    
    public InstaFlickUser(String username){
        this.userName = username;
        this.picture = new Picture();
    }
    
    public InstaFlickUser(String username, IPicture picture){
        this.userName = username;
        this.picture = picture;
    }
    
    @Override
    public boolean removeUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*
        TODO: Implement database connection to remove user.
        */
    }

    @Override
    public String getUserName() {
        return userName;
    }

}
