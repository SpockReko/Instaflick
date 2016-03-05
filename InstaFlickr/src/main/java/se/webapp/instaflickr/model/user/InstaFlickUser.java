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
    private String email;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String fname;
    @Getter
    @Setter
    private String lname;
    @Getter
    @Setter
    @OneToOne
    private Picture profilePicture;
    @Getter
    @Setter
    @OneToMany
    private List<Picture> pictures;
    
    
    public InstaFlickUser() {
        this.email = "User " + (++staticId).toString();
        this.pictures = new LinkedList<Picture>();
    }
    
    public InstaFlickUser(String email){
        this.email = email;
        this.pictures = new LinkedList<Picture>();
    }
    
    public InstaFlickUser(String email, String password){
        this.email = email;
        this.password = password;
        this.pictures = new LinkedList<Picture>();
    }
    
    public InstaFlickUser(String email, String password, Picture picture){
        this.email = email;
        this.password = password;
        this.profilePicture = picture;
        this.pictures = new LinkedList<Picture>();
    }
    
    public void addPicture(Picture pic){
        pictures.add(pic);
    }

}
