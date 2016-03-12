/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import se.webapp.instaflickr.model.media.TestAlbum;
import se.webapp.instaflickr.model.media.TestPicture;
import se.webapp.instaflickr.model.persistence.TestDatabase;
import se.webapp.instaflickr.model.reaction.TestComment;
import se.webapp.instaflickr.model.user.TestUser;

/**
 *
 * @author Spock
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestDatabase.class,
    TestUser.class,
    TestPicture.class,
    TestComment.class,
    TestAlbum.class
})

public class TestSuite {

}
