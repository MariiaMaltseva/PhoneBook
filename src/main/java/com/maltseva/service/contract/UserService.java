package com.maltseva.service.contract;

import com.maltseva.entity.User;

public interface UserService {
    boolean checkLoginValid(String login, String password);

    void save(User newUser);

    User findUserByLogin(String login);
}
