package com.maltseva.service.implementation;

import com.maltseva.dao.contract.ContactDao;
import com.maltseva.dao.implementation.ContactDaoImpl;
import com.maltseva.entity.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceImplTest {
    Contact testContact = new Contact(2, "Ivanov", "Ivan", "Ivanovich", "+380 99 999 9999");

    @Mock
    private ContactDao contactDao = spy(ContactDaoImpl.class);

    @InjectMocks
    ContactServiceImpl contactService;

    @Test
    public void testDelete() throws Exception {
        when(contactDao.findById(3)).thenReturn(testContact);
        doNothing().when(contactDao).delete(testContact);

        contactService.delete(3);
        verify(contactDao).findById(3);
        verify(contactDao).delete(testContact);
    }

    @Test
    public void testDeleteNotExistContact() throws Exception {
        when(contactDao.findById(0)).thenReturn(null);
        doNothing().when(contactDao).delete(testContact);

        contactService.delete(0);
        verify(contactDao).findById(0);
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void testUpdate() throws Exception {
        testContact.setId(2);

        doNothing().when(contactDao).update(testContact);
        contactService.save(testContact);
        verify(contactDao).update(testContact);
    }

    @Test
    public void testCreate() throws Exception {
        doNothing().when(contactDao).save(testContact);
        contactService.save(testContact);
        verify(contactDao).save(testContact);
    }

    @Test
    public void testFindAll() throws Exception {
        contactService.findAll(anyInt());
        verify(contactDao).findAll(anyInt());
    }

    @Test
    public void findById() throws Exception {
        contactService.findById(anyInt());
        verify(contactDao).findById(anyInt());

    }

    @Test
    public void findBySearch() throws Exception {
        contactService.findBySearch(anyString());
        verify(contactDao).findBySearch(anyString());
    }
}