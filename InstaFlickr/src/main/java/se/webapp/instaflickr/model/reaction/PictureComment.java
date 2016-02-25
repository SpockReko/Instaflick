/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import se.webapp.instaflickr.model.media.Picture;

/**
 *
 * @author Pontus
 */
@Entity
public class PictureComment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @OneToOne(fetch=FetchType.LAZY)
    Picture picture;
    @Id
    @OneToOne(fetch=FetchType.LAZY)
    Comment comment;
   
    public PictureComment() {
        
    }
}
