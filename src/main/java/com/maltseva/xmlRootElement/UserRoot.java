package com.maltseva.xmlRootElement;

import com.maltseva.entity.User;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class UserRoot {
    private List<User> users;

    private int maxId;

    public int getMaxId() {
        return maxId;
    }

    @XmlElement
    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public UserRoot() {
        users = new ArrayList<User>();
    }

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user", type = User.class)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getNewId() {
        return ++maxId;
    }
}
