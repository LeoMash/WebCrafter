package ru.webcrafter.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import ru.webcrafter.core.entities.User;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    HibernateTemplate hibernateTemplate;

    public void addUser(User user) {
        hibernateTemplate.save(user);
    }

    public User getUser(long id) {
        User user = hibernateTemplate.get(User.class, id);
        return user;
    }

    public void updateUser(User user) {
        hibernateTemplate.update(user);
    }

    public List<User> getAllUsers() {
        return hibernateTemplate.find("from User");
    }


}
