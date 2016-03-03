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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Pontus
 */
@Entity
public class Album implements Serializable {

    @Id
    @Getter
    @Setter
    private String name;
    @Id
    @Getter
    @Setter
    @OneToOne
    private InstaFlickUser owner;
    @OneToMany
    private List<InstaFlickUser> followers;
    @OneToMany
    private List<Picture> pictures;

    public Album() {} // Används ej
    
    public Album(String albumName, InstaFlickUser owner) {
        name = albumName;
        this.owner = owner;
        followers = new LinkedList<>();
        pictures = new LinkedList<>();
    }
    
    public void addFollower(InstaFlickUser user){
        followers.add(user);
    }
    
    public boolean removeFollower(InstaFlickUser user){
        return followers.remove( user);
        }
    
    public int nrOfFollowers(){
        return followers.size();
    }
    
    public void addPicture(Picture pic){
        pictures.add(pic);
    }
    
    public int nrOfPictures(){
        return pictures.size();
    }
    public boolean removePicture(Picture pic){
        return pictures.remove(pic);
    }
}
