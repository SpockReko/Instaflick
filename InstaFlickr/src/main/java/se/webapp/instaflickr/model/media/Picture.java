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
    @Getter
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

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    // Används ej.
    public Picture() {
        LOG.warning("*******************************************************");
        LOG.warning("DO NOT USE THIS CONSTRUCTOR! Picture(),");
        LOG.warning("Use Picture(InstaflickUser, Likes) or ");
        LOG.warning("Use Picture(InstaflickUser, Likes, String) or ");
        LOG.warning("*******************************************************");
    } // Används ej.

    public Picture(InstaFlickUser owner) {
        this.owner = owner;
        this.imagePath = null;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<>();
        this.description = "";
    }

    public Picture(InstaFlickUser owner, String path, String description) {
        this.owner = owner;
        this.imagePath = path;
        this.uploaded = Calendar.getInstance();
        this.comments = new LinkedList<>();

        if (description == null) {
            this.description = "";
        } else {
            this.description = description;
        }
    }

    public void comment(InstaFlickUser usr, String comment) {
        this.comments.add(new Comment(usr, comment));
    }

}
