
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
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.webapp.instaflickr.model.InstaFlick;
import se.webapp.instaflickr.model.user.InstaFlickUser;
import se.webapp.instaflickr.model.UserRegistry;

/**
 * Testing the persistence layer
 *
 * @author Pontus
 */

@RunWith(Arquillian.class)
public class TestDatabase extends AbstractTest{

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
  
    // ######## AbstractADO method tests InstaFlickUser #################################

    @Test
    public void test_Persist_A_User() throws Exception {
        InstaFlickUser u = createUser("James");
        instaFlick.getUserRegistry().create(u);
        List<InstaFlickUser> users = instaFlick.getUserRegistry().findAll();
        assertTrue(users.size() > 0);
    }
    
    @Test
    public void testDeleteUser() throws Exception {
        InstaFlickUser newUser = createUser("James");
        instaFlick.getUserRegistry().create(newUser);
        instaFlick.getUserRegistry().delete("James");
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("James");
        assertTrue(givenUser == null);
    }

    @Test 
    public void testUpdate_User() throws Exception {
        InstaFlickUser newUser = createUser("James");
        UserRegistry reg = instaFlick.getUserRegistry();
        reg.create(newUser);
        assertNull(newUser.getPassword());
        newUser.setPassword("weak_password");
        reg.update(newUser);
        InstaFlickUser givenUser = reg.find("James");
        assertTrue( givenUser.getPassword().equals(newUser.getPassword() ) );
    }

    
    @Test 
    public void test_Find_Range_Of_Users() throws Exception {
        InstaFlickUser newUser = createUser("James");
        InstaFlickUser newUser2 = createUser("John");
        InstaFlickUser newUser3 = createUser("Alice");
        InstaFlickUser newUser4 = createUser("Lisa");
        UserRegistry reg = instaFlick.getUserRegistry();
        reg.create(newUser);
        reg.create(newUser2);
        reg.create(newUser3);
        reg.create(newUser4);
        List<InstaFlickUser> range = reg.findRange(1, 2);
        System.out.println("range: " + range.toString());
    }
    
    
    @Test 
    public void test_Count_Users() throws Exception {
        InstaFlickUser newUser = createUser("James");
        InstaFlickUser newUser2 = createUser("John");
        InstaFlickUser newUser3 = createUser("Alice");
        InstaFlickUser newUser4 = createUser("Lisa");
        UserRegistry reg = instaFlick.getUserRegistry();
        reg.create(newUser);
        reg.create(newUser2);
        reg.create(newUser3);
        reg.create(newUser4);
        int number = reg.count();
        int four = 4;
        assertTrue(number == four);
    }

}