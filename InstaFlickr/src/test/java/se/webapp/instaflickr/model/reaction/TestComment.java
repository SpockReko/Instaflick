/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model.reaction;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
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
public class TestComment extends AbstractTest {
    
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

    
    // Create comment instans
    @Test
    public void test_SetandGet_User_InComment() throws Exception{
        InstaFlickUser newUser = new InstaFlickUser("James");
        Comment comment = new Comment(null,null,null);
        comment.setUser(newUser);
        InstaFlickUser givenUser = comment.getUser();
        assertTrue(givenUser.getEmail().equals(newUser.getEmail()));
    }
    
    @Test
    public void test_SetandGet_Text_Comment() throws Exception{
        Comment comment = new Comment();
        String newComment = "Hej, Här är texten!";
        comment.setCommentText(newComment);
        String givenComment = comment.getCommentText();
        assertTrue(newComment.equals(givenComment));
    }

    @Test
    public void test_Get_Created_Comment() throws Exception{
        InstaFlickUser user = new InstaFlickUser("James");
        String text = "Hello World";
        Comment comment = new Comment(user, text, new Likes());
        Calendar nowCal = Calendar.getInstance();
        nowCal.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);
        Calendar cal = comment.getCreated( );
        System.out.println("CompareTo: " + cal.compareTo(nowCal) );
        assertTrue(cal.compareTo(nowCal) <= 0);
    }
    
    @Test
    public void test_SetandGet_Like_Comment() throws Exception{
        Comment comment = new Comment();
        Likes newLikes = new Likes();
        comment.setLike(newLikes);
        Likes givenLikes = comment.getLike();
        assertTrue(givenLikes.equals(newLikes));
    }

    @Test
    public void test_EditComment() throws Exception{
        Comment comment = new Comment(createUser("James"), "First text", new Likes());
        String editText = "editText";
        comment.editComment(editText);
        assertTrue(editText.equals(comment.getCommentText()));
    }

   
}
