/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.persistence;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Spock
 */
public class AbstractTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage("se.webapp.instaflickr.model")
                .addPackage("se.webapp.instaflickr.model.media")
                .addPackage("se.webapp.instaflickr.model.persistence")
                .addPackage("se.webapp.instaflickr.model.reaction")
                .addPackage("se.webapp.instaflickr.model.user")
                .addPackage("se.webapp.instaflickr.model.view")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext(unitName = "jpa_InstaBase_test_PU")
    @Produces
    @Default
    EntityManager em;

    @Inject
    UserTransaction utx;

    // ######## Private help functions ##########################
    // Order matters
    public void clearAll() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("delete from Likes").executeUpdate();
        em.createQuery("delete from Comment").executeUpdate();
        em.createQuery("delete from Picture").executeUpdate();
        em.createQuery("delete from InstaFlickUser").executeUpdate();
        utx.commit();
    }

    public InstaFlickUser createUser(String name) {
        return new InstaFlickUser(name);
    }
}
