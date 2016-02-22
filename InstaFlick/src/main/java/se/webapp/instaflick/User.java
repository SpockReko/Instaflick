
import se.webapp.instaflick.IUser;
import se.webapp.media.IPicture;
import se.webapp.media.Picture;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pontus
 */
public class User implements IUser {

    private final String id;
    private IPicture picture;
    private String description;
    
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
