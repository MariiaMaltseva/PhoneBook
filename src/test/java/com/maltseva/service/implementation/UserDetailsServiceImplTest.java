package com.maltseva.service.implementation;

import com.maltseva.dao.contract.UserDao;
import com.maltseva.dao.implementation.UserDaoImpl;
import com.maltseva.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    User testUser = new User("Petrov Peter", "peter", "qwerty");

    @Mock
    private UserDao userDao = spy(UserDaoImpl.class);

    @InjectMocks
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    public void loadUserByUsername() throws Exception, UsernameNotFoundException {
        when(userDao.findUserIdByLogin("peter")).thenReturn(3);
        when(userDao.findById(3)).thenReturn(testUser);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("peter");
        assertEquals(userDetails, new org.springframework.security.core.userdetails.User(testUser.getLogin(),
                testUser.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("USER"))));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_ThrowUsernameNotFoundException_NotExistUser() throws Exception, UsernameNotFoundException {
        when(userDao.findUserIdByLogin("test")).thenReturn(null).thenThrow(UsernameNotFoundException.class);
        when(userDao.findById(3)).thenReturn(testUser);

        userDetailsServiceImpl.loadUserByUsername("test");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_ThrowUsernameNotFoundException_ProblemWithDatabase() throws Exception, UsernameNotFoundException {
        when(userDao.findUserIdByLogin("peter")).thenReturn(3);
        when(userDao.findById(3)).thenReturn(null).thenThrow(UsernameNotFoundException.class);

        userDetailsServiceImpl.loadUserByUsername("peter");
    }
}