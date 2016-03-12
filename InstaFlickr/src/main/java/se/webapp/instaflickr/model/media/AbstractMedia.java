package se.webapp.instaflickr.model.media;

import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.InstaFlickUser;

@MappedSuperclass
public abstract class AbstractMedia {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Setter
    @OneToMany
    protected List<Comment> comments;
    @Getter
    @Setter
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Calendar uploaded;
    @Getter
    @Setter
    @OneToOne
    protected InstaFlickUser owner;

    protected abstract EntityManager getEntityManager();

    public boolean postComment(InstaFlickUser user, String mgs) {
        Comment comment = new Comment(user, mgs);
        comments.add(comment);
        getEntityManager().persist(comment);
        return true;
    }

    public boolean removeComment(InstaFlickUser user, Comment comment) {

        if (comment.getUser().getEmail().equals(user.getEmail())) {
            comments.remove(comment);
            getEntityManager().remove(comment);
            return true;
        }
        return false;
    }
}
