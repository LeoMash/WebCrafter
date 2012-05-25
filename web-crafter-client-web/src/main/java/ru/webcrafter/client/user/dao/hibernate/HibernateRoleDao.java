package ru.webcrafter.client.user.dao.hibernate;

import org.hibernate.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import ru.webcrafter.client.user.Role;
import ru.webcrafter.client.user.dao.RoleDao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class HibernateRoleDao extends HibernateDaoSupport implements RoleDao {

    public boolean roleExists(final String roleName) {
        if (roleName == null) return false;
        Object obj = getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                org.hibernate.SQLQuery q = session.createSQLQuery("SELECT roleId FROM ex_role WHERE name=?");
                q.addScalar("roleId", org.hibernate.Hibernate.LONG);
                q.setString(0, roleName);
                Iterator results = q.list().iterator();
                return (results.hasNext()) ? results.next() : null;
            }
        });
        return (obj != null);
    }

    public Collection<Role> getRoles() {
        return getHibernateTemplate().loadAll(Role.class);
    }

    public Role getRole(long id) {
        return (Role) getHibernateTemplate().get(Role.class, id);
    }

    public Role getRole(String roleName) {
        Role r = null;
        if (roleName != null) {
            List list = getHibernateTemplate().find("from Role r where r.name=?", roleName);
            if (list != null && !list.isEmpty()) r = (Role) list.get(0);
        }
        return r;
    }

    public Role createRole(String roleName) {
        if (roleExists(roleName)) throw new DataIntegrityViolationException(roleName);
        Role r = new Role();
        r.setName(roleName);
        getHibernateTemplate().save(r);
        return r;
    }

    public void updateRole(Role r) {
        if (r != null) {
            getHibernateTemplate().update(r);
        }
    }

    public boolean deleteRole(Role r) {
        if (r == null) return false;
        boolean wasDeleted = false;
        try {
            getHibernateTemplate().delete(r);
            wasDeleted = true;
        } catch (Exception zzz) {
            zzz.printStackTrace();
        }
        return wasDeleted;
    }
}