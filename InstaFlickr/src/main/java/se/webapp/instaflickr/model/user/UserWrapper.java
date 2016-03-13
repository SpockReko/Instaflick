package se.webapp.instaflickr.model.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "InstaFlickUser", propOrder = {
    "email",
    "password"
})

/**
 * Supposed to be a wrapper to send users easily via different kinds of
 * datatypes (xml/json) Not used right now.
 */
public class UserWrapper {

    private InstaFlickUser user;

    protected UserWrapper() {
    }

    public UserWrapper(InstaFlickUser user) {
        this.user = user;
    }

    @XmlElement //If serving XML we should use @XmlAttribute 
    public String getEmail() {
        return user.getEmail();
    }

    @XmlElement
    public String getPassword() {
        return user.getPassword();
    }
}
