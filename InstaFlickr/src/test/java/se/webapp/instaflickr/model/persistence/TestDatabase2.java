
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.webapp.instaflickr.model.persistence;

import java.util.List;
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
import se.webapp.instaflickr.model.InstaFlick;
import se.webapp.instaflickr.model.media.Album;
import se.webapp.instaflickr.model.media.Picture;
import se.webapp.instaflickr.model.reaction.Likes;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 * Testing the persistence layer
 *
 * @author Pontus
 */

@RunWith(Arquillian.class)
public class TestDatabase2 extends AbstractTest {

    @PersistenceContext(unitName = "jpa_InstaBase_test_PU")
    @Produces
    @Default
    EntityManager em;
    
    @Inject
    InstaFlick instaFlick;

    @Before
    public void preparePersistenceTest() throws Exception {
        //clearAll();
    }
    
    @Test
    public void test1_Add_Picture() throws Exception {
        InstaFlickUser user = new InstaFlickUser("user1");
        Picture pic = new Picture(user, new Likes(), "path1");
        user.addPicture(pic);
        instaFlick.getUserRegistry().create(user);
        
        user = instaFlick.getUserRegistry().find("user1"); // Get the user from the database
        pic = user.getPictures().get(0); // Get the only picture added
        assertTrue(pic.getImagePath().equals("path1"));        
    }
 
    @Test
    public void test2_Add_Album() throws Exception {
        InstaFlickUser user = new InstaFlickUser("user2");
        Album album = new Album("Album2", user);
        user.addAlbum(album);
        instaFlick.getUserRegistry().create(user);
        
        user = instaFlick.getUserRegistry().find("user2"); // Get the user from the database
        album = user.getAlbums().get(0); // Get the only album added
        assertTrue(album.getName().equals("Album2"));        
    }
/*    
    @Test
    public void test3_Like_A_Picture() throws Exception {
        InstaFlickUser user1 = new InstaFlickUser("user31");
        Likes likes = new Likes();
        Picture pic = new Picture(user1, likes, "path3");
        user1.addPicture(pic);
        InstaFlickUser user2 = new InstaFlickUser("user32");
        pic.likeIt(user1.getUsername());
        instaFlick.getUserRegistry().create(user2);
        instaFlick.getUserRegistry().create(user1);
        
        user1 = instaFlick.getUserRegistry().find("user31");
        pic = user1.getPictures().get(0);
        likes = pic.getLikes();
        String username2 = likes.getUserList().get(0);
        assertTrue(username2.equals("user32"));        
    }
*/
}