package ru.webcrafter.client.user.dao;

import ru.webcrafter.client.user.UserAuth;

import java.util.Collection;

public interface UserDao {
    boolean userExists(String userName);

    UserAuth getUser(long userId);

    UserAuth getUser(String userName);

    UserAuth createUser(UserAuth newUserAuth);

    void updateUser(UserAuth userAuth);

    boolean deleteUser(UserAuth userAuth);

    Collection<UserAuth> getUsers();
}
