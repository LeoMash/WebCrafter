package ru.webcrafter.client.user;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ex_role")
public class Role implements Serializable {

    private Long roleId;

    @NotEmpty
    @Length(min = 4, max = 30)
    private String name;

    private Set<UserAuth> roleUserAuths;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long value) {
        this.roleId = value;
    }

    @NaturalId
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "userRoles", targetEntity = UserAuth.class)
    public Set<UserAuth> getRoleUserAuths() {
        if (roleUserAuths == null) {
            roleUserAuths = new HashSet<UserAuth>();
        }
        return roleUserAuths;
    }

    public void setRoleUserAuths(Set<UserAuth> roleUserAuths) {
        this.roleUserAuths = roleUserAuths;
    }

    public int hashCode() {
        return (name != null) ? name.hashCode() : super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!getClass().equals(obj.getClass())) return false;
        Role x = (Role) obj;
        // see: Hibernate In Action, pg. 124-125
        return equals(name, x.name); // equals via business key
    }

    protected final boolean equals(final Object left, final Object right) {
        return (left != null) ? left.equals(right) : (right == null);
    }
}
