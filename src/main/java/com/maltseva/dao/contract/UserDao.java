package com.maltseva.dao.contract;

import com.maltseva.entity.User;

public interface UserDao {
    User findById(int id);

    Integer findUserIdByLogin(String login);

    void save(User newUser);
}
