package com.maltseva.dao.implementation;

import com.maltseva.dao.contract.ContactDao;
import com.maltseva.entity.Contact;
import com.maltseva.xmlRootElement.ContactRoot;
import org.springframework.stereotype.Repository;

import javax.xml.bind.*;
import java.io.File;
import java.util.*;

@Repository("xmlContact")
public class ContactDaoXmlImpl implements ContactDao {

    @Override
    public Contact findById(int id) {
        ContactRoot contactRoot = readFromFile();

        for (Contact contact : contactRoot.getContacts()) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public void save(Contact contact) {
        ContactRoot contactRoot = readFromFile();

        contact.setId(contactRoot.getNewId());
        contactRoot.getContacts().add(contact);
        writeToFile(contactRoot);
    }

    @Override
    public void update(Contact updatedContact) {
        ContactRoot contactRoot = readFromFile();

        List<Contact> contactsList = contactRoot.getContacts();

        for (int index = 0; index < contactsList.size(); index++) {
            if (contactsList.get(index).getId() == updatedContact.getId()) {
                contactsList.remove(index);
                contactsList.add(index, updatedContact);
                writeToFile(contactRoot);
                return;
            }
        }
    }

    @Override
    public List<Contact> findAll(int idUser) {
        ContactRoot contactRoot = readFromFile();
        List<Contact> listFoundByUserId = new ArrayList<>();

        for (Contact contact : contactRoot.getContacts()) {
            if (contact.getIdUser() == idUser) {
                listFoundByUserId.add(contact);
            }
        }
        return (listFoundByUserId.size() == 0)
                ? null
                : listFoundByUserId;
    }

    @Override
    public void delete(Contact deletedContact) {
        ContactRoot contactRoot = readFromFile();

        List<Contact> contactsList = contactRoot.getContacts();

        for (int index = 0; index < contactsList.size(); index++) {
            if (contactsList.get(index).getId() == deletedContact.getId()) {
                contactsList.remove(index);
                writeToFile(contactRoot);
                return;
            }
        }
    }

    @Override
    public List<Contact> findBySearch(String searchRequest) {
        ContactRoot contactRoot = readFromFile();

        List<Contact> contactsList = contactRoot.getContacts();
        List<Contact> resultSearch = new ArrayList<>();
        for (Contact contact : contactsList) {
            if (contact.getLastName().contains(searchRequest)
                    || contact.getFirstName().contains(searchRequest)
                    || contact.getCellPhone().contains(searchRequest)) {

                resultSearch.add(contact);
            }
        }
        return resultSearch;
    }

    private ContactRoot readFromFile() {
        File file = new File("files/contacts.xml");
        if (file.exists()) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(ContactRoot.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                return (ContactRoot) jaxbUnmarshaller.unmarshal(file);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        return new ContactRoot();
    }

    private void writeToFile(ContactRoot contactRoot) {
        File file = new File("files/contacts.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ContactRoot.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(contactRoot, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
