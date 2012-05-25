package ru.webcrafter.client.user;

import java.util.Collection;

public interface UserService {
    boolean hasUser(String userId);

    UserAuth getUser(String userId);

    UserAuth createUser(UserAuth userAuthDto);

    void updateUser(UserAuth userAuth);

    boolean deleteUser(UserAuth userAuth);

    Collection<UserAuth> getUsers();

    boolean isUserInRole(String userName, String roleName);

    Role createRole(String roleName);

    void updateRole(Role role);

    boolean deleteRole(Role role);

    Role getRole(String roleName);

    Collection<Role> getRoles();
}