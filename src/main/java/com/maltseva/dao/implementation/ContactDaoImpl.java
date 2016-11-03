package com.maltseva.dao.implementation;

import com.maltseva.dao.contract.ContactDao;
import com.maltseva.entity.Contact;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Primary
@Repository("jdbcContact")
public class ContactDaoImpl implements ContactDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Contact findById(int id) {
        return entityManager.find(Contact.class, id);
    }

    public void save(Contact contact) {
        entityManager.persist(contact);
    }

    public List<Contact> findAll(int idUser) {
        TypedQuery<Contact> queryFoundByUserId = entityManager.createQuery("SELECT c FROM Contact c WHERE c.idUser = :id", Contact.class);
        queryFoundByUserId.setParameter("id", idUser);

        List<Contact> contactFoundByUserId = queryFoundByUserId.getResultList();
        return (contactFoundByUserId.size() == 0)
                ? null
                : contactFoundByUserId;
    }

    public void delete(Contact contact) {
        entityManager.remove(contact);
    }

    public void update(Contact contact) {
        entityManager.merge(contact);
    }

    public List<Contact> findBySearch(String searchRequest) {
        String query = "SELECT c FROM Contact c WHERE c.lastName LIKE :search OR c.firstName LIKE :search OR c.cellPhone LIKE :search";
        TypedQuery<Contact> queryBySearch = entityManager.createQuery(query, Contact.class);
        queryBySearch.setParameter("search", '%' + searchRequest + '%');

        List<Contact> contactFoundByUserId = queryBySearch.getResultList();
        return (contactFoundByUserId.size() == 0)
                ? null
                : contactFoundByUserId;
    }
}
