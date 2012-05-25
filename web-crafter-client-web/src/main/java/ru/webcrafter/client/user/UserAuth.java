package ru.webcrafter.client.user;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Pattern;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ex_user")
public class UserAuth implements Serializable {
    private Long userId;
    private Long createdOn;
    private boolean active;
    private String passwordHash;
    private Set<Role> userRoles;

    @NotEmpty
    @Length(min = 4, max = 16)
    @Pattern(regex = "^[a-zA-Z\\d_]{4,12}$", message = "Invalid screen name.")
    private String userName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    @NaturalId(mutable = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String value) {
        this.userName = value;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long value) {
        this.createdOn = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    @Column(name = "password", length = 32)
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String value) {
        this.passwordHash = value;
    }

    public boolean hasRole(String roleName) {
        Set<Role> myRoles = getUserRoles();
        for (Role next : myRoles) {
            if (next.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public void removeRole(Role role) {
        getUserRoles().remove(role);
        role.getRoleUserAuths().remove(this);
    }

    public void addRole(Role role) {
        getUserRoles().add(role);
        role.getRoleUserAuths().add(this);
    }

    @Transient
    public Role[] getRoles() {
        return (Role[]) getUserRoles().toArray(new Role[0]);
    }

    @ManyToMany(targetEntity = ru.webcrafter.client.user.Role.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "ex_user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    protected Set<Role> getUserRoles() {
        if (userRoles == null) {
            userRoles = new HashSet<Role>();
        }
        return userRoles;
    }

    protected void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public String toString() {
        return userName;
    }

    public int hashCode() {
        return (userName != null) ? userName.hashCode() : super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!getClass().equals(obj.getClass())) return false;
        UserAuth x = (UserAuth) obj;
        // see: Hibernate In Action, pg. 124-125
        return equals(userName, x.userName); // equals via business key
    }

    protected final boolean equals(final Object left, final Object right) {
        return (left != null) ? left.equals(right) : (right == null);
    }
}
