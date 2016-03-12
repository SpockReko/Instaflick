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
public class TestAlbum extends AbstractTest {
    
        

    @Inject
    Album album;

    final int ONE = 1;
    
    @PersistenceContext(unitName = "jpa_InstaBase_test_PU")
    @Produces
    @Default
    EntityManager em;

    @Before
    public void preparePersistenceTest() throws Exception {
        clearAll();
    }

    
    @Test
    public void test_Methods_For_followers(){
        InstaFlickUser user = new InstaFlickUser("Frank");
        InstaFlickUser user2 = new InstaFlickUser("James");
        album = new Album("Photo On Cats <3", user);
        album.addFollower(user);
        album.addFollower(user2);
        assertTrue( album.removeFollower(user) );        
        assertTrue( album.nrOfFollowers() == ONE);
    }
    
    @Test
    public void test_Metods_For_Pictures(){
        InstaFlickUser user = new InstaFlickUser("James");
        InstaFlickUser user2 = new InstaFlickUser("Frank");
        album = new Album("Photo On Dogs <3", user);
        Picture pic = new Picture(user );
        Picture pic2 = new Picture(user2 );
        album.addPicture(pic);
        album.addPicture(pic2);
        assertTrue(album.removePicture(pic) );
        assertTrue(album.nrOfPictures() == ONE);
    }
    
}
