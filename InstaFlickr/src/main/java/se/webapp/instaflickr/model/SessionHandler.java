/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.webapp.instaflickr.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Pontus
 */
@SessionScoped
public class SessionHandler implements Serializable {
    
    private Boolean session = false;
    private String sessionID;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Boolean getSession() {
        return session;
    }

    public void setSession(Boolean session) {
        this.session = session;
    }
}
