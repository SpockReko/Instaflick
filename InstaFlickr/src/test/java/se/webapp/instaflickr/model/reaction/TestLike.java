/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

import java.util.List;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.webapp.instaflickr.model.InstaFlick;
import se.webapp.instaflickr.model.persistence.AbstractTest;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Spock
 */
@RunWith(Arquillian.class)
public class TestLike extends AbstractTest {
    
    @Inject
    InstaFlick instaFlick;
 
    // Need a standalone em to remove testdata between tests
    // No em accessible from interfaces
    @PersistenceContext(unitName = "jpa_InstaBase_test_PU")
    @Produces
    @Default
    EntityManager em;
    
    @Before
    public void preparePersistenceTest() throws Exception {
        clearAll();
    }

    //Test for to see if the test application works
    @Test
    public void alwaysTrue() {
        assertTrue(true);
    }
    

    @Inject
    Likes likes;

    @Inject
    InstaFlickUser user;
    
    @Inject
    InstaFlickUser user2;

    // ######## Like tests #################################
    
    @Test
    public void test_Add_and_nrOfLikes_Like() throws Exception{
        user = new InstaFlickUser("Frank");
        user2 = new InstaFlickUser("James");
        likes.addLike(user);
        likes.addLike(user2);
        int number_Of_Likes = likes.nrOfLikes();
        int two = 2;
        assertTrue(number_Of_Likes == two);
    }
    
    @Test
    public void test_Remove_Like() throws Exception{
        user = new InstaFlickUser("Frank");
        user2 = new InstaFlickUser("James");
        likes = new Likes();
        likes.addLike(user);
        likes.addLike(user2);
        likes.removeLike(user);
        assertTrue(likes.nrOfLikes() == 1 && likes.userList.contains(user2));
    }
    
}
