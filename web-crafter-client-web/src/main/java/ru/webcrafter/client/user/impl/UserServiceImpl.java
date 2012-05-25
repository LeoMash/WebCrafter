package ru.webcrafter.client.user.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.webcrafter.client.user.Role;
import ru.webcrafter.client.user.UserAuth;
import ru.webcrafter.client.user.UserService;
import ru.webcrafter.client.user.dao.RoleDao;
import ru.webcrafter.client.user.dao.UserDao;

import java.util.Collection;

@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    public boolean hasUser(String userId) {
        return userDao.userExists(userId);
    }

    @Transactional(readOnly = true)
    public UserAuth getUser(String userId) {
        return userDao.getUser(userId);
    }

    public UserAuth createUser(UserAuth userAuthDto) {
        UserAuth userAuth = userDao.createUser(userAuthDto);
        Role userRole = roleDao.getRole("ROLE_USER");
        if (userRole == null) {
            userRole = roleDao.createRole("ROLE_USER");
        }
        userAuth.addRole(userRole);
        return userAuth;
    }

    @Transactional(readOnly = true)
    public boolean isUserInRole(String userName, String roleName) {
        if (userName == null || roleName == null) return false;
        UserAuth userAuth = userDao.getUser(userName);
        if (userAuth == null) return false;
        return userAuth.hasRole(roleName);
    }

    public void updateUser(UserAuth userAuthDto) {
        userDao.updateUser(userAuthDto);
    }

    public boolean deleteUser(UserAuth userAuth) {
        return userDao.deleteUser(userAuth);
    }

    @Transactional(readOnly = true)
    public Collection<UserAuth> getUsers() {
        return userDao.getUsers();
    }

    public Role createRole(String roleName) {
        return roleDao.createRole(roleName);
    }

    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }

    public boolean deleteRole(Role role) {
        return roleDao.deleteRole(role);
    }

    @Transactional(readOnly = true)
    public Role getRole(String roleName) {
        return roleDao.getRole(roleName);
    }

    @Transactional(readOnly = true)
    public Collection<Role> getRoles() {
        return roleDao.getRoles();
    }
}
