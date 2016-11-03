package com.maltseva.dao.contract;

import com.maltseva.entity.Contact;

import java.util.List;

public interface ContactDao {
    Contact findById(int id);

    void save(Contact contact);

    void update(Contact contact);

    List<Contact> findAll(int idUser);

    void delete(Contact contact);

    List<Contact> findBySearch(String searchRequest);
}
