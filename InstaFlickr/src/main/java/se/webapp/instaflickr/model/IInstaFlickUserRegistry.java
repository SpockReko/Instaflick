/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import javax.ejb.Local;
import se.webapp.instaflickr.model.persistence.IDAO;
import se.webapp.instaflickr.model.user.InstaFlickUser;


/**
 *
 * @author Pontus
 */
//@Local
interface IInstaFlickUserRegistry extends IDAO<InstaFlickUser, String> {
    
}
