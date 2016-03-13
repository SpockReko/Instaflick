/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.user;

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
import se.webapp.instaflickr.model.media.Likes;
import se.webapp.instaflickr.model.persistence.InstaFlick;
import se.webapp.instaflickr.model.media.Picture;
import se.webapp.instaflickr.model.persistence.AbstractTest;

/**
 *
 * @author Spock
 */
@RunWith(Arquillian.class)
public class TestUser extends AbstractTest {

    @PersistenceContext(unitName = "jpa_InstaBase_test_PU")
    @Produces
    @Default
    EntityManager em;

    @Before
    public void preparePersistenceTest() throws Exception {
        clearAll();
    }

    @Test
    public void alwaysTrue() {
        assertTrue(true);
    }

    @Inject
    InstaFlick instaFlick;

    @Test
    public void test_SetAndGet_Username() throws Exception {
        InstaFlickUser newUser = createUser("Jane");
        newUser.setFname("James");
        instaFlick.getUserRegistry().create(newUser);
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("Jane");
        assertTrue(givenUser.getFname().equals(newUser.getFname()));
    }

    @Test
    public void test_Update_User() throws Exception {
        InstaFlickUser newUser = createUser("james.email@domain.se");
        instaFlick.getUserRegistry().create(newUser);
        newUser.setEmail("the jamester");
        instaFlick.getUserRegistry().update(newUser);
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("james.email@domain.se");
        assertTrue(givenUser.getUsername().equals(newUser.getUsername()));
    }

    @Test
    public void test_SetAndGet_Password() throws Exception {
        InstaFlickUser newUser = createUser("James");
        newUser.setPassword("StrongPassword");
        instaFlick.getUserRegistry().create(newUser);
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("James");
        assertTrue(givenUser.getPassword().equals(newUser.getPassword()));
    }

    @Test
    public void test_SetAndGet_ProfilePicture() throws Exception {
        InstaFlickUser newUser = createUser("James");
        Picture pic = new Picture(newUser, new Likes());
        newUser.setProfilePicture(pic);
        Picture givenPic = newUser.getProfilePicture();
        assertTrue(givenPic.equals(pic));
    }

    @Test
    public void test_SetAndGet_Picture() throws Exception {
        InstaFlickUser newUser = createUser("James");
        Picture pic = new Picture(newUser, new Likes());
        newUser.addPicture(pic);
        List<Picture> picList = newUser.getPictures();
        boolean test = false;
        for (int i = 0; i < picList.size(); i++) {
            if (picList.get(i).equals(pic)) {
                test = true;
            }
        }
        assertTrue(test);
    }
}
