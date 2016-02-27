/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import se.webapp.instaflickr.model.user.InstaFlickUser;

/**
 *
 * @author Pontus
 */
@ApplicationScoped
public class InstaFlick{

    public InstaFlick() {
        Logger.getAnonymousLogger().log(Level.INFO, "InstaFlick is alive");
    }
 
    public static void main(String[] arg){
        InstaFlickUser user = new InstaFlickUser();
        InstaFlickUserRegistry reg = new InstaFlickUserRegistry();
        reg.create(user);
        
    }
    @EJB
    private IInstaFlickUserRegistry userRegistry;
    
    public IInstaFlickUserRegistry getUserRegistry() {
        return userRegistry;
    }

}
