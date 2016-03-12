package se.webapp.instaflickr.model.media;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import se.webapp.instaflickr.model.user.UserResource;
import se.webapp.instaflickr.model.user.InstaFlickUser;

@Entity
public class Album implements Serializable {

    @Id
    @Getter
    @Setter
    private String name;
    @Id
    @Getter
    @Setter
    @OneToOne
    private InstaFlickUser owner;
    @Getter
    @Setter
    @OneToMany
    private List<InstaFlickUser> followers;
    @Getter
    @Setter
    @OneToMany
    private List<Picture> pictures;

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    public Album() {
        LOG.warning("*******************************************************");
        LOG.warning("DO NOT USE THIS CONSTRUCTOR! Album(),");
        LOG.warning("Use Album(string,InstaflickUser) Instead,");
        LOG.warning("*******************************************************");

    } // Används ej

    public Album(String albumName, InstaFlickUser owner) {
        name = albumName;
        this.owner = owner;
        followers = new LinkedList<>();
        pictures = new LinkedList<>();
    }

    public void addFollower(InstaFlickUser user) {
        followers.add(user);
    }

    public boolean removeFollower(InstaFlickUser user) {
        return followers.remove(user);
    }

    public int nrOfFollowers() {
        return followers.size();
    }

    public void addPicture(Picture pic) {
        pictures.add(pic);
    }

    public int nrOfPictures() {
        return pictures.size();
    }

    public boolean removePicture(Picture pic) {
        return pictures.remove(pic);
    }
}
