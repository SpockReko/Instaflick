package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.UserResource;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 * This represents the Pictures uploaded to the server. A unique id for each
 * picture for it to be easily identified. It has an owner/uploader,
 * description, time of upload and a path where it's stored. It also has Likes
 * and Comments.
 */
@Entity
public class Picture implements Serializable {

    @PersistenceContext
    static EntityManager em;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Setter
    private String imagePath;
    @Getter
    @Setter
    private String description;
    @Setter
    @OneToMany
    private List<Comment> comments;
    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar uploaded;
    @Getter
    @Setter
    @OneToOne
    InstaFlickUser owner;
    @Getter
    @Setter
    Likes likes;

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    // Used internally
    public Picture() {
    }

    public Picture(InstaFlickUser owner, Likes likes) {
        this.owner = owner;
        this.imagePath = null;
        this.likes = likes;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<>();
        this.description = "";
    }

    public Picture(InstaFlickUser owner, Likes likes, String path, String description) {
        this.owner = owner;
        this.likes = likes;
        this.imagePath = path;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<>();

        if (description == null) {
            this.description = "";
        } else {
            this.description = description;
        }
    }

    public Picture comment(InstaFlickUser usr, String comment) {
        this.comments.add(new Comment(usr, comment));
        return this;
    }

    public List<Comment> getComment() {
        return comments;
    }

    //Skickar LikesID. Kortar koden för den som kallar på denna.
    public long getLikesId() {
        return likes.getId();
    }
}
