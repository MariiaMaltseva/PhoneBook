package com.maltseva.controller;

import com.maltseva.dao.contract.ContactDao;
import com.maltseva.dao.contract.UserDao;
import com.maltseva.dao.implementation.UserDaoImpl;
import com.maltseva.entity.User;
import com.maltseva.service.contract.ContactService;
import com.maltseva.service.contract.UserService;
import com.maltseva.service.implementation.ContactServiceImpl;
import com.maltseva.service.implementation.UserServiceImpl;
import com.maltseva.viewmodel.UserRegister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    User testUser = new User("Petrov Peter", "peter", "qwerty");
    UserRegister user = new UserRegister();
    ModelMap model = new ModelMap();
    BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new User(), "user");

    @Mock
    private UserService userService = spy(UserServiceImpl.class);

    @Mock
    private ContactService contactService = spy(ContactServiceImpl.class);

    @InjectMocks
    UserController userController;

    @Test
    public void testLogin() throws Exception {
        String view = userController.login(model, "", "");
        assertEquals(view, "login");
        assertEquals(model.get("mode"), "MODE_HOME");
    }

    @Test
    public void testLoginWithError() throws Exception {
        String view = userController.login(model, "error", "");
        assertEquals(view, "login");
        assertEquals(model.get("error"), "Error! Login or password is incorrect. Please try again");
    }

    @Test
    public void testLogout() throws Exception {
        String view = userController.login(model, "", "logout");
        assertEquals(view, "login");
        assertEquals(model.get("message"), "Logged out successfully.");
    }

    @Test
    public void testRegister() throws Exception {
        String view = userController.register(model);
        assertEquals(view, "register");
        assertEquals(model.get("mode"), "MODE_REGISTER");
    }

    @Test
    public void testAddUser() throws Exception {
        when(userService.findUserByLogin("test")).thenReturn(null);
        User newUser = new User(user.getFullName(), user.getLogin(), user.getPassword());

        doNothing().when(userService).save(newUser);
        String view = userController.addUser(user, bindingResult, model);
        assertEquals(view, "login");
        assertEquals(model.get("mode"), "MODE_HOME");
    }

    @Test
    public void testAddUserWithDublicateLogin() throws Exception {
        user.setLogin("peter");
        when(userService.findUserByLogin("peter")).thenReturn(testUser);

        String view = userController.addUser(user, bindingResult, model);
        assertEquals(view, "register");
        assertEquals(model.get("mode"), "ERROR_DUPLICATE");
    }

    @Test
    public void testAddUserWithErrorLoginValidation() throws Exception {
        bindingResult.addError(new FieldError("user", "login", "Your password must be more than 4 characters"));

        String view = userController.addUser(user, bindingResult, model);
        assertEquals(view, "register");
        assertEquals(model.get("mode"), "MODE_REGISTER");
        assertEquals("Your password must be more than 4 characters", bindingResult.getFieldError().getDefaultMessage());
    }
}