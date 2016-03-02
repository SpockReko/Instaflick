/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Stefan
 */
@Entity
public class Comment implements Serializable {
    
    @Id @Getter @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Setter @Getter @OneToOne
    private InstaFlickUser user;
    
    @Setter @Getter 
    String commentText;
    
    @Getter
    @Temporal(javax.persistence.TemporalType.DATE)
    Calendar created;
    
    @Setter @Getter @OneToOne
    private Likes like;
    
    public Comment(){ 
    
    }
    
    public Comment(String commentText){
        this.commentText = commentText;
    }
    
    public Comment(InstaFlickUser user, String commentText, Likes like){
        
        this.user = user;
        this.commentText = commentText;
        this.like = like;
        this.created = getNow();    
    }
    
    // Denna metoden känns som setComment() metoden.
    
    public void editComment(String commentText) {
        this.commentText = commentText;
    }
    
    private Calendar getNow(){
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);
        return newCalendar;
    }
    
}
