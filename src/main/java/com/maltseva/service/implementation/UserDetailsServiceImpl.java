package com.maltseva.service.implementation;

import com.maltseva.dao.contract.UserDao;
import com.maltseva.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource(name = "${dao.implementation.user}")
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginUser) throws UsernameNotFoundException {
        Integer userIdFoundByLogin = userDao.findUserIdByLogin(loginUser);
        if (userIdFoundByLogin == null) {
            throw new UsernameNotFoundException("Can't find User with the provided login");
        }

        User userFoundById = userDao.findById(userIdFoundByLogin);
        if (userFoundById == null) {
            throw new UsernameNotFoundException("Can not get User data");
        }

        return new org.springframework.security.core.userdetails.User(userFoundById.getLogin(),
                userFoundById.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
