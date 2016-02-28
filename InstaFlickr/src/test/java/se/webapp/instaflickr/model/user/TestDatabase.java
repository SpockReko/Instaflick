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

    @Test
    public void truE() {
        assertTrue(true);
    }
  

/*    
    @Test
    public void createUser(){
        user = new InstaFlickUser("stefan");
        user.setEmail("email@domain.com");
        user.setPassword("password");

        registry = new UserRegistry();
        em.persist(user);
        assertTrue(true);
    }
*/
    @Test
    public void testPersistAUser() throws Exception {
        InstaFlickUser u = new InstaFlickUser("James");
        instaFlick.getUserRegistry().create(u);
        List<InstaFlickUser> users = instaFlick.getUserRegistry().findAll();
        assertTrue(users.size() > 0);
        //assertTrue(users.get(0).getName().equals(u.getName()));
    }
    
    // Order matters
    private void clearAll() throws Exception {  
        utx.begin();  
        em.joinTransaction();
        em.createQuery("delete from Likes").executeUpdate();
        em.createQuery("delete from Comment").executeUpdate();
        em.createQuery("delete from Picture").executeUpdate();
        em.createQuery("delete from InstaFlickUser").executeUpdate();
        /*
        em.createQuery("delete from InstaFlickUser_Picture").executeUpdate();
        em.createQuery("delete from LIKES_INSTAFLICKUSER").executeUpdate();
        em.createQuery("delete from PICTURE_COMMENT").executeUpdate(); */
        utx.commit();
    }
}
