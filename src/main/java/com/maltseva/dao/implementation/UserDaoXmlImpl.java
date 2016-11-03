package com.maltseva.dao.implementation;

import com.maltseva.dao.contract.UserDao;
import com.maltseva.entity.User;
import com.maltseva.xmlRootElement.UserRoot;
import org.springframework.stereotype.Repository;

import javax.xml.bind.*;
import java.io.File;

@Repository("xmlUser")
public class UserDaoXmlImpl implements UserDao {

    public User findById(int id) {
        UserRoot userRoot = readFromFile();

        for (User user : userRoot.getUsers()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public Integer findUserIdByLogin(String login) {
        UserRoot userRoot = readFromFile();

        for (User user : userRoot.getUsers()) {
            if (user.getLogin().equals(login)) {
                return user.getId();
            }
        }
        return null;
    }

    public void save(User newUser) {
        UserRoot userRoot = readFromFile();

        newUser.setId(userRoot.getNewId());
        userRoot.getUsers().add(newUser);

        writeToFile(userRoot);
    }

    private UserRoot readFromFile() {
        File file = new File("files/users.xml");
        if (file.exists()) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(UserRoot.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                return (UserRoot) jaxbUnmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        return new UserRoot();
    }

    private void writeToFile(UserRoot userRoot) {
        File file = new File("files/users.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserRoot.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(userRoot, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
