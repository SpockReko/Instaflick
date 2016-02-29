/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

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
    





    // ######## Like tests #################################
    /*
    public void test_SG_UserList() throws Exception{
        
    }
    /*
    public void test_Add_Like() throws Exception{
        
    }
    /*
    public void test_Remove_Like() throws Exception{
        
    }
    
    /*
    public void test_nrOfLikes() throws Exception{
        
    }
    
   */
}
