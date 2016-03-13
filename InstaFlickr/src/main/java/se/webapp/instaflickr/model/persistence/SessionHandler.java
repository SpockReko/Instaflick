package se.webapp.instaflickr.model.persistence;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 * This is representing the current session the logged in used is in. For
 * identifying the user while commenting for example.
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
