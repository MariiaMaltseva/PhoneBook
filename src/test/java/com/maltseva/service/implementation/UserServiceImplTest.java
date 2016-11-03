package com.maltseva.service.implementation;

import com.maltseva.dao.contract.UserDao;
import com.maltseva.dao.implementation.UserDaoImpl;
import com.maltseva.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    User testUser = new User("Petrov Peter", "peter", "qwerty");

    @Mock
    private UserDao userDao = spy(UserDaoImpl.class);

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void testCheckLoginAndPasswordValid() throws Exception {
        when(userDao.findUserIdByLogin("peter")).thenReturn(3);
        when(userDao.findById(3)).thenReturn(testUser);
        boolean result = userService.checkLoginValid(testUser.getLogin(), testUser.getPassword());
        assertTrue(result);

        verify(userDao).findUserIdByLogin("peter");
        verify(userDao).findById(3);
    }

    @Test
    public void testCheckValidLoginAndInValidPassword() throws Exception {
        when(userDao.findUserIdByLogin("peter")).thenReturn(3);
        when(userDao.findById(3)).thenReturn(testUser);

        boolean result = userService.checkLoginValid(testUser.getLogin(), anyString());
        assertFalse(result);

        verify(userDao).findUserIdByLogin("peter");
        verify(userDao).findById(3);
    }

    @Test
    public void testCheckInValidLogin() throws Exception {
        when(userDao.findUserIdByLogin("test")).thenReturn(null);

        boolean result = userService.checkLoginValid("test", anyString());
        assertFalse(result);

        verify(userDao).findUserIdByLogin("test");
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testSave() throws Exception {
        doNothing().when(userDao).save(testUser);
        userService.save(testUser);
        verify(userDao).save(testUser);
    }

    @Test
    public void testFindExistedUserByLogin() throws Exception {
        when(userDao.findUserIdByLogin("peter")).thenReturn(5);
        when(userDao.findById(5)).thenReturn(testUser);

        User userFoundByLogin = userService.findUserByLogin("peter");
        assertEquals(userFoundByLogin, testUser);

        verify(userDao).findUserIdByLogin("peter");
        verify(userDao).findById(5);
    }

    @Test
    public void testFindNotExistedUserByLogin() throws Exception {
        when(userDao.findUserIdByLogin("test")).thenReturn(null);

        User userFoundByLogin = userService.findUserByLogin("test");
        assertEquals(userFoundByLogin, null);

        verify(userDao).findUserIdByLogin("test");
        verifyNoMoreInteractions(userDao);
    }
}