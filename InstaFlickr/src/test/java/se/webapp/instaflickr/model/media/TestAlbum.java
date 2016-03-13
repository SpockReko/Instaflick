/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.junit.Arquillian;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.webapp.instaflickr.model.persistence.AbstractTest;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Spock
 */
@RunWith(Arquillian.class)
public class TestAlbum{

    final int ONE = 1;

    @Test
    public void test_Methods_For_followers() {
        InstaFlickUser user = new InstaFlickUser("Frank");
        InstaFlickUser user2 = new InstaFlickUser("James");
        Album album = new Album("Photo On Cats <3", user);
        album.addFollower(user);
        album.addFollower(user2);
        assertTrue(album.removeFollower(user));
        assertTrue(album.nrOfFollowers() == ONE);
    }

    @Test
    public void test_Metods_For_Pictures() {
        InstaFlickUser user = new InstaFlickUser("James");
        InstaFlickUser user2 = new InstaFlickUser("Frank");
        Album album = new Album("Photo On Dogs <3", user);
        Picture pic = new Picture(user, new Likes());
        Picture pic2 = new Picture(user2, new Likes());
        album.addPicture(pic);
        album.addPicture(pic2);
        assertTrue(album.removePicture(pic));
        assertTrue(album.nrOfPictures() == ONE);
    }

}
