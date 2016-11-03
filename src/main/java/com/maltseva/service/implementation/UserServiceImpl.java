package com.maltseva.service.implementation;

import com.maltseva.dao.contract.UserDao;
import com.maltseva.entity.User;
import com.maltseva.service.contract.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource(name = "${dao.implementation.user}")

    private UserDao userDao;

    public boolean checkLoginValid(String login, String password) {
        Integer idFoundByLogin = userDao.findUserIdByLogin(login);

        if (idFoundByLogin != null) {
            User userFoundById = userDao.findById(idFoundByLogin);
            if (userFoundById.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void save(User newUser) {
        userDao.save(newUser);
    }

    public User findUserByLogin(String login) {
        Integer idUserFoundByLogin = userDao.findUserIdByLogin(login);
        return (idUserFoundByLogin == null)
                ? null
                : userDao.findById(idUserFoundByLogin);
    }
}
