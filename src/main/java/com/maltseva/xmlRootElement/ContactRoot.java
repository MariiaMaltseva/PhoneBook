package com.maltseva.xmlRootElement;

import com.maltseva.entity.Contact;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ContactRoot {
    private List<Contact> contacts;

    private int maxId;

    public int getMaxId() {
        return maxId;
    }

    @XmlElement
    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public ContactRoot() {
        contacts = new ArrayList<Contact>();
    }

    @XmlElementWrapper(name = "contacts")
    @XmlElement(name = "contact", type = Contact.class)
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> merchants) {
        this.contacts = contacts;
    }

    public int getNewId() {
        return ++maxId;
    }
}
