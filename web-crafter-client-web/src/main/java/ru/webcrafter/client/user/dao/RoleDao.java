package ru.webcrafter.client.user.dao;

import ru.webcrafter.client.user.Role;

import java.util.Collection;

public interface RoleDao {
    boolean roleExists(String name);

    Role getRole(long roleId);

    Role getRole(String name);

    Role createRole(String name);

    void updateRole(Role role);

    boolean deleteRole(Role role);

    Collection<Role> getRoles();
}
