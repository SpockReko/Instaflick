/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.UserResource;
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
    
    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    
    public Comment(){ 
        LOG.warning("*******************************************************");
        LOG.warning("DO NOT USE THIS CONSTRUCTOR! Comment(),");
        LOG.warning("Use Comment(InstaflickUser, String, Likes)");
        LOG.warning("*******************************************************");

    }
    
    public Comment(String commentText){
        this.commentText = commentText;
    }
    
    public Comment(InstaFlickUser user, String commentText){
        
        this.user = user;
        this.commentText = commentText;
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
