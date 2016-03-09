package se.webapp.instaflickr.model.user;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.media.Album;
import se.webapp.instaflickr.model.media.Picture;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@Entity
public class InstaFlickUser implements Serializable {
    
    static Long staticId = 0L;
    
    @Id
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email = "";
    @Getter
    @Setter
    private String fname;
    @Getter
    @Setter
    private String lname;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    @OneToOne
    private Picture profilePicture;
    @Getter
    @Setter
    @OneToMany
    private List<Picture> pictures;
    @Getter
    @Setter
    @OneToMany
    private List<Album> albums;    
    
    public InstaFlickUser() {
        this.username = "User " + (++staticId).toString();
        this.pictures = new LinkedList<Picture>();
        this.albums = new LinkedList<Album>();
    }
    
    public InstaFlickUser(String username){
        this.username = username;
        this.pictures = new LinkedList<Picture>();
        this.albums = new LinkedList<Album>();
    }
    
    public InstaFlickUser(String username, String password){
        this.username = username;
        this.password = password;
        this.pictures = new LinkedList<Picture>();
        this.albums = new LinkedList<Album>();
   }
    
    public InstaFlickUser(String username, String password, Picture picture){
        this.username = username;
        this.password = password;
        this.profilePicture = picture;
        this.pictures = new LinkedList<Picture>();
        this.albums = new LinkedList<Album>();
    }
    
    public void addPicture(Picture pic){
        pictures.add(pic);
    }
    
    public void addAlbum(Album album){
        albums.add(album);
    }
}
