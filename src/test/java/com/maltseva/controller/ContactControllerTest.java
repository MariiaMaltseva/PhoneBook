package com.maltseva.controller;

import com.maltseva.entity.Contact;
import com.maltseva.entity.User;
import com.maltseva.service.contract.ContactService;
import com.maltseva.service.contract.UserService;
import com.maltseva.service.implementation.ContactServiceImpl;
import com.maltseva.service.implementation.UserServiceImpl;
import com.maltseva.viewmodel.ContactCreate;
import com.maltseva.viewmodel.UserRegister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {
    User testUser = new User("Petrov Peter", "peter", "qwerty");
    Contact testContact = new Contact(2, "Ivanov", "Ivan", "Ivanovich", "+380 99 999 9999");
    ContactCreate user = new ContactCreate();
    List<Contact> contacts = null;
    Principal principal = () -> "peter";
    ContactCreate contactCreate = new ContactCreate();

    ModelMap model = new ModelMap();
    BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Contact(), "contact");

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    private UserService userService = spy(UserServiceImpl.class);

    @Mock
    private ContactService contactService = spy(ContactServiceImpl.class);

    @InjectMocks
    ContactController contactController;

    @Test
    public void testShowAllContacts() throws Exception {
        contacts = Arrays.asList(new Contact(), new Contact(), new Contact());

        when(request.getUserPrincipal()).thenReturn(principal);

        when(userService.findUserByLogin(principal.getName())).thenReturn(testUser);
        when(contactService.findAll(testUser.getId())).thenReturn(contacts);

        String view = contactController.showAllContacts(model, request, response);
        assertEquals(view, "listOfContacts");
        assertEquals(model.get("contact"), contacts);
        assertEquals(model.get("userName"), testUser.getFullName());
    }

    @Test
    public void testEmptyContactList() throws Exception {
        when(request.getUserPrincipal()).thenReturn(principal);

        when(userService.findUserByLogin(principal.getName())).thenReturn(testUser);
        when(contactService.findAll(testUser.getId())).thenReturn(contacts);

        String view = contactController.showAllContacts(model, request, response);
        assertEquals(view, "newContact");
        assertEquals(model.get("message"), "Your Phone Book is empty. Please add new contact.");
        assertEquals(model.get("userName"), testUser.getFullName());
    }

    @Test
    public void testSearch() throws Exception {
        contacts = Arrays.asList(new Contact(), new Contact(), new Contact());

        when(request.getUserPrincipal()).thenReturn(principal);
        when(userService.findUserByLogin(principal.getName())).thenReturn(testUser);

        when(contactService.findBySearch("test")).thenReturn(contacts);
        String view = contactController.search("test", model, request, response);
        assertEquals(view, "listOfContacts");
        assertEquals(model.get("contact"), contacts);
        assertEquals(model.get("userName"), testUser.getFullName());

    }

    @Test
    public void testNotFindBySearchRequest() throws Exception {
        when(request.getUserPrincipal()).thenReturn(principal);
        when(userService.findUserByLogin(principal.getName())).thenReturn(testUser);

        when(contactService.findBySearch("test")).thenReturn(contacts);
        String view = contactController.search("test", model, request, response);
        assertEquals(view, "listOfContacts");
        assertEquals(model.get("message"), "Unfortunately, your search returned no results. Please try again.");
        assertEquals(model.get("userName"), testUser.getFullName());
    }

    @Test
    public void testViewNewContact() throws Exception {
        when(request.getUserPrincipal()).thenReturn(principal);
        when(userService.findUserByLogin(principal.getName())).thenReturn(testUser);

        when(contactService.findBySearch("test")).thenReturn(contacts);
        String view = contactController.newContact(model, request, response);
        assertEquals(view, "newContact");
        assertEquals(model.get("userName"), testUser.getFullName());
    }

    @Test
    public void testViewForUpdate() throws Exception {
        when(request.getUserPrincipal()).thenReturn(principal);
        when(userService.findUserByLogin(principal.getName())).thenReturn(testUser);

        when(contactService.findById(6)).thenReturn(testContact);
        String view = contactController.edit(6, model, request, response);
        assertEquals(view, "newContact");
        assertEquals(model.get("userName"), testUser.getFullName());
        assertEquals(model.get("contact"), testContact);
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(contactService).delete(8);

        String view = contactController.delete(8, model);

        assertEquals(view, "redirect:/");
    }

    @Test
    public void testSave() throws Exception {
        contactCreate.setCellPhone("+380 99 999 9999");
        contactCreate.setHomePhone("");
        contactCreate.setAddress("");
        contactCreate.setEmail("");
        when(request.getUserPrincipal()).thenReturn(principal);
        when(userService.findUserByLogin(principal.getName())).thenReturn(testUser);

        Contact newContact = new Contact(testUser.getId(),
                contactCreate.getLastName(),contactCreate.getFirstName(),
                contactCreate.getMiddleName(), contactCreate.getCellPhone());

        doNothing().when(contactService).save(newContact);
        String view = contactController.save(contactCreate, bindingResult, model, request, response);
        assertEquals(view, "redirect:/");
    }

    @Test
    public void testSaveWithError() throws Exception {
        bindingResult.addError(new FieldError("contact", "lastName", "Your Last Name must be more than 3 characters"));
        contactCreate.setCellPhone("+380 99 999 9999");
        contactCreate.setHomePhone("");
        contactCreate.setAddress("");
        contactCreate.setEmail("");
        when(request.getUserPrincipal()).thenReturn(principal);
        when(userService.findUserByLogin(principal.getName())).thenReturn(testUser);

        Contact newContact = new Contact(testUser.getId(),
                contactCreate.getLastName(),contactCreate.getFirstName(),
                contactCreate.getMiddleName(), contactCreate.getCellPhone());

        String view = contactController.save(contactCreate, bindingResult, model, request, response);
        assertEquals(view, "newContact");
        assertEquals(model.get("userName"), testUser.getFullName());
        assertEquals("Your Last Name must be more than 3 characters", bindingResult.getFieldError().getDefaultMessage());
    }
}