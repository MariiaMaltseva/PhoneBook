package com.maltseva.dao.implementation;

import com.maltseva.dao.contract.UserDao;
import com.maltseva.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.util.List;

@Primary
@Repository("jdbcUser")
public class UserDaoImpl  implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public User findById(int id){
        return entityManager.find(User.class, id);
    }

    public Integer findUserIdByLogin(String login){
        TypedQuery<Integer> queryFindByLogin = entityManager.createQuery("SELECT u.id FROM User u WHERE u.login = :login", Integer.class);
        queryFindByLogin.setParameter("login", login);
        List<Integer> foundUserId = queryFindByLogin.getResultList();
        return (foundUserId.size() == 0) ? null : foundUserId.get(0);
    }

    public void save(User newUser){
            entityManager.persist(newUser);
    }
}
