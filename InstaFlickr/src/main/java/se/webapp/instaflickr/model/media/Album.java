/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private InstaFlickUser owner;
    private List<InstaFlickUser> followers;
    private List<Picture> pictures;

    public Album() {} // Används ej
    
    public Album(String albumName, InstaFlickUser owner) {
        name = albumName;
        this.owner = owner;
    }
}
