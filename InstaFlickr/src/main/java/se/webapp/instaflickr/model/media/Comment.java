package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.util.Calendar;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.UserResource;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 * This represents a Comment. The comment has a commenter/user. It has the
 * comment text, a unique id and the time it was created.
 */
@Entity
public class Comment implements Serializable {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @Getter
    @OneToOne
    private InstaFlickUser user;

    @Setter
    @Getter
    String commentText;

    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created;

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    // Used internally
    public Comment() {
    }

    public Comment(String commentText) {
        this.commentText = commentText;
        this.created = Calendar.getInstance();
    }

    public Comment(InstaFlickUser user, String commentText) {
        this.user = user;
        this.commentText = commentText;
        this.created = Calendar.getInstance();
    }

    public void editComment(String commentText) {
        this.commentText = commentText;
    }
}
