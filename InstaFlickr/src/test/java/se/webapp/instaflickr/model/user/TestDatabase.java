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
import se.webapp.instaflickr.model.UserRegistry;

/**
 * Testing the persistence layer
 *
 * @author Pontus
 */

@RunWith(Arquillian.class)
public class TestDatabase {

    @Inject
    InstaFlick instaFlick;
   
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage("se.webapp.instaflickr.model")
                .addPackage("se.webapp.instaflickr.model.user")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
 
    // Need a standalone em to remove testdata between tests
    // No em accessible from interfaces
    @PersistenceContext(unitName = "jpa_InstaBase_test_PU")
    @Produces
    @Default
    EntityManager em;

    @Inject
    UserTransaction utx;
    
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
    public void test_Presist_A_User_Twice() throws Exception {
        InstaFlickUser newUser = createUser("James");
        instaFlick.getUserRegistry().create(newUser);
        Exception saveException = null;
        try{
        instaFlick.getUserRegistry().create(newUser);
        }catch(Exception e){
            saveException = e;
        }
        assertFalse(saveException == null);
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
        instaFlick.getUserRegistry().create(newUser);
        assertNull(newUser.getPassword());
        newUser.setPassword("weak_password");
        instaFlick.getUserRegistry().delete("James");
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("James");
        assertTrue(givenUser == null);
    }

    /*
    @Test
    public void test_Find_Range_Of_Users() throws Exception {
        
    }
    
    /*
    @Test
    public void test_Count_Users() throws Exception {}
    
    // ######## InstaFlickUser tests #################################
  */
    @Test
    public void test_SetAndGet_Username() throws Exception {
        InstaFlickUser newUser = createUser("Jane");
        newUser.setUserName("James");
        instaFlick.getUserRegistry().create(newUser);
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("James");
        assertTrue(givenUser.getUserName().equals(newUser.getUserName()));
    }
  /*  
    @Test
    public void test_SetAndGet_Email() throws Exception {
        InstaFlickUser newUser = createUser("James");
        newUser.setEmail("james.email@domain.se");
        instaFlick.getUserRegistry().create(newUser);
        InstaFlickUser givenUser = instaFlick.getUserRegistry().find("James");
        assertTrue(givenUser.getEmail().equals(newUser.getEmail()));
    }
/*
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
        
    // ######## Comment tests ################################# 
    
    // Create comment instans
    /*
    public void test_SetandGet_User_InComment() throws Exception{
        // 
    }
    /*
    public void test_SetandGet_Text_Comment() throws Exception{
        
    }
    /*
    public void test_SetandGet_Created_Comment() throws Exception{
        
    }
    /*
    public void test_SetandGet_Like_Comment() throws Exception{
        
    }
    /*
    public void test_EditComment() throws Exception{
        
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
    
    // ######## Pictures tests #################################
    /*
    public void test_SetandGet_ImagePath_Of_Picture() throws Exception{
        
    }
    /*
    public void test_SetandGet_Comments_On_Picture() throws Exception{
        
    }
    /*
    public void test_SetandGet_Uploaded_Picture() throws Exception{
        
    }
    /*
    public void test_SetandGet_Uploader_Of_Picture() throws Exception{
        
    }
    /*
    public void test_SetandGet_Like_On_Picture() throws Exception{
        
    }
    /*
    public void test_Post_Comment_On_Picture() throws Exception{
        
    }
   */
    // ######## Private help functions ##########################

    // Order matters
    private void clearAll() throws Exception {  
        utx.begin();  
        em.joinTransaction();
        em.createQuery("delete from Likes").executeUpdate();
        em.createQuery("delete from Comment").executeUpdate();
        em.createQuery("delete from Picture").executeUpdate();
        em.createQuery("delete from InstaFlickUser").executeUpdate();
        utx.commit();
    }
    
    private InstaFlickUser createUser(String name){
        return new InstaFlickUser(name);
    }

}