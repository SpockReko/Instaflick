/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.IUser;

/**
 *
 * @author Stefan
 */
@Entity
public class Comment implements IComment, Serializable {
    
    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @Getter
    private String userName;
    @Getter
    @Setter
    String text;
    @Setter
    @Getter
    private ILikes like;
    
    public Comment(){
    }
    
    public Comment(String userName, String text){
        this.userName = userName;
        this.text = text;
        this.like = new Likes();
    }
    
    @Override
    public void editComment(String comment) {
        this.text = comment;
    }

    @Override
    public IUser getUser(IUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
