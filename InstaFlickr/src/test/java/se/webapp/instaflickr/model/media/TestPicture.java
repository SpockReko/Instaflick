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
import org.jboss.arquillian.junit.Arquillian;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.webapp.instaflickr.model.persistence.InstaFlick;
import se.webapp.instaflickr.model.persistence.AbstractTest;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Spock
 */
@RunWith(Arquillian.class)
public class TestPicture {

    @Test
    public void test_SetandGet_ImagePath_Of_Picture() throws Exception {
        String ImagePath = "some Path";
        InstaFlickUser user = new InstaFlickUser("James");
        Picture pic = new Picture(user, new Likes());
        pic.setImagePath(ImagePath);
        String givenPath = pic.getImagePath();
        assertTrue(givenPath.equals(ImagePath));
    }

    @Test
    public void test_Post_Comments_On_Picture() throws Exception {
        String email = "James";
        InstaFlickUser user = new InstaFlickUser(email);
        Picture pic = new Picture(user, new Likes());
        String text = "added comment";
        pic.comment(user, text);
        boolean test = false;
        for (int i = 0; i < pic.getComment().size(); i++) {
            Comment index = pic.getComment().get(i);
            if (index.getCommentText().equals(text)
                    && index.getUser().getUsername().equals(email)) {
                test = true;
            }
        }
        assertTrue(test);
    }

    @Test
    public void test_SetandGet_Uploaded_Picture() throws Exception {
        InstaFlickUser user = new InstaFlickUser("James");
        Picture pic = new Picture(user, new Likes());
        Calendar nowCal = Calendar.getInstance();
        Calendar cal = pic.getUploaded();
        assertTrue(cal.compareTo(nowCal) <= 0);
    }

    @Test
    public void test_Get_Uploader_Of_Picture() throws Exception {
        InstaFlickUser user = new InstaFlickUser("James", "1");
        Picture pic = new Picture(user, new Likes());
        InstaFlickUser givenUser = pic.getOwner();
        assertTrue(givenUser.equals(user));
    }

}
