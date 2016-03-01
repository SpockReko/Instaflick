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
public class TestPicture extends AbstractTest {
    
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
    
    // ######## Pictures tests #################################
    /*
    @Test
    public void test_SetandGet_ImagePath_Of_Picture() throws Exception{
        
    }
    /*
    @Test
    public void test_SetandGet_Comments_On_Picture() throws Exception{
        
    }
    /*
    @Test
    public void test_SetandGet_Uploaded_Picture() throws Exception{
        
    }
    /*
    @Test
    public void test_SetandGet_Uploader_Of_Picture() throws Exception{
        
    }
    /*
    @Test
    public void test_SetandGet_Like_On_Picture() throws Exception{
        
    }
    /*
    @Test
    public void test_Post_Comment_On_Picture() throws Exception{
        
    }
   */
}
