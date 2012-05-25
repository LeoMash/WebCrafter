package ru.webcrafter.server;

import ru.webcrafter.core.entities.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);

    User getUser(long id);

    void updateUser(User user);

    List<User> getAllUsers();
}
