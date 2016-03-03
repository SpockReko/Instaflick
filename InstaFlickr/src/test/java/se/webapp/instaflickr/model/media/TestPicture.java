/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.media;

import java.util.Calendar;
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
import se.webapp.instaflickr.model.reaction.Comment;
import se.webapp.instaflickr.model.reaction.Likes;
import se.webapp.instaflickr.model.user.InstaFlickUser;

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

        
    @Inject
    InstaFlickUser user;
    
    @Inject
    Picture pic;
    
    @Test
    public void test_SetandGet_ImagePath_Of_Picture() throws Exception{
        String ImagePath = "some Path";
        user = createUser("James");
        pic = new Picture(user, new Likes());
        pic.setImagePath(ImagePath);
        String givenPath = pic.getImagePath();
        assertTrue(givenPath.equals(ImagePath));
    }
    
    @Test
    public void test_Post_Comments_On_Picture() throws Exception{
        String name = "James";
        user = createUser(name);
        pic = new Picture(user, new Likes());
        String text = "added comment";
        pic.postComment(user, text);
        boolean test = false;
        for (int i = 0; i < pic.getComments().size(); i++) {
            Comment index = pic.getComments().get(i);
            if(index.getCommentText().equals(text) 
                    && index.getUser().getUserName().equals(name)){
                test = true;
            }
        }
        assertTrue(test);
    }

    @Test
    public void test_SetandGet_Uploaded_Picture() throws Exception{
        user = new InstaFlickUser("James");
        pic = new Picture(user, new Likes());
        Calendar nowCal = Calendar.getInstance();
        nowCal.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);
        Calendar cal = pic.getUploaded();
        assertTrue(cal.compareTo(nowCal) <= 0);
    }

    @Test
    public void test_Get_Uploader_Of_Picture() throws Exception{
        user = new InstaFlickUser("James");
        pic = new Picture(user, new Likes());
        InstaFlickUser givenUser = pic.getUploader();
        assertTrue(givenUser.equals(user));
    }
   
}
