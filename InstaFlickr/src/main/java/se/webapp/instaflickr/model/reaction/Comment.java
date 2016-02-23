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
    private IUser user;
    @Getter
    @Setter
    String comment;
    @Setter
    @Getter
    private ILikes like;
    
    public Comment(){}
    
    public Comment(IUser user, String comment){
        this.user = user;
        this.comment = comment;
        this.like = new Likes();
    }
    
    @Override
    public void editComment(String comment) {
        this.comment = comment;
    }

    @Override
    public IUser getUser(IUser user) {
        return this.user;
    }

    public IUser getUser() {
        return user;
    }
    
}
