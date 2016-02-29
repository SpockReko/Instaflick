/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Stefan
 */
@Entity
public class Comment implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Setter @Getter @OneToOne
    private InstaFlickUser user;
    
    @Getter @Setter
    String commentText;
    
    @Setter @Getter
    Date created;
    
    @Setter @Getter @OneToOne
    private Likes like;
    
    public Comment(){
    
    }
    
    public Comment(InstaFlickUser user, String commentText){
        
        this.user = user;
        this.commentText = commentText;
        this.like = new Likes();
    
    }
    
    public void editComment(String commentText) {
        
        this.commentText = commentText;
    
    }
    
}
