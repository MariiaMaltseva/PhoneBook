package com.maltseva.service.contract;

import com.maltseva.entity.Contact;

import java.util.List;

public interface ContactService {
    void delete(int id);

    void save(Contact contact);

    List<Contact> findAll(int idUser);

    Contact findById(int id);

    List<Contact> findBySearch(String searchRequest);
}
