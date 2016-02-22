/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.media;

import se.webapp.reaction.Likes;

/**
 *
 * @author Pontus
 */
public class Picture extends AbstractMedia implements IPicture {

    public Picture() {
        likes = new Likes();
    }

    //Image picture;
}
