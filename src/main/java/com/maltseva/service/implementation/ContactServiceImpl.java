package com.maltseva.service.implementation;

import com.maltseva.dao.contract.ContactDao;
import com.maltseva.entity.Contact;
import com.maltseva.service.contract.ContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {
    @Resource(name = "${dao.implementation.contact}")
    private ContactDao contactDao;

    public void delete(int id) {
        Contact contactToDelete = contactDao.findById(id);

        if (contactToDelete != null) {
            contactDao.delete(contactToDelete);
        }
    }

    public void save(Contact contact) {
        if (contact.getId() == 0)
            contactDao.save(contact);
        else
            contactDao.update(contact);
    }

    public List<Contact> findAll(int idUser) {

        return contactDao.findAll(idUser);
    }

    public Contact findById(int id) {
        return contactDao.findById(id);
    }

    public List<Contact> findBySearch(String searchRequest) {
        return contactDao.findBySearch(searchRequest);
    }
}
