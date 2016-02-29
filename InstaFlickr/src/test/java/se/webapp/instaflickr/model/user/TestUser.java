/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.user;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.webapp.instaflickr.model.InstaFlick;
import se.webapp.instaflickr.model.persistence.AbstractTest;

/**
 *
 * @author Spock
 */
@RunWith(Arquillian.class)
public class TestUser extends AbstractTest {
    
    @Inject
    InstaFlick instaFlick;
 
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
    
      // ######## InstaFlickUser tests #################################
  
    @Test
    public void test_SetAndGet_Username() throws Exception {
        InstaFlickUser newUser = createUser("Jane");
        newUser.setUserName("James");
        instaFlick.getUserRegistry().create(newUser);
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("James");
        assertTrue(givenUser.getUserName().equals(newUser.getUserName()));
    }
 
    @Test
    public void test_SetAndGet_Email() throws Exception {
        InstaFlickUser newUser = createUser("James");
        newUser.setEmail("james.email@domain.se");
        instaFlick.getUserRegistry().create(newUser);
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("James");
        assertTrue(givenUser.getEmail().equals(newUser.getEmail()));
    }

    @Test
    public void test_SetAndGet_Password() throws Exception {
        InstaFlickUser newUser = createUser("James");
        newUser.setPassword("StrongPassword");
        instaFlick.getUserRegistry().create(newUser);
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("James");
        assertTrue(givenUser.getPassword().equals(newUser.getPassword()));
    }
/*
    @Test
    public void test_SetAndGet_ProfilePicture() throws Exception {
        // TODO: We must add picture fuction first
    }
    /*
    @Test
    public void test_SetAndGet_Picture() throws Exception {
        // TODO: We must add picture functions first
    }
  
*/



    
}
