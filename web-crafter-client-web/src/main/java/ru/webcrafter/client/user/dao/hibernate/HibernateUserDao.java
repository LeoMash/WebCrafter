package ru.webcrafter.client.user.dao.hibernate;

import org.hibernate.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import ru.webcrafter.client.user.UserAuth;
import ru.webcrafter.client.user.dao.UserDao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class HibernateUserDao extends HibernateDaoSupport implements UserDao {

    public boolean userExists(final String userName) {
        if (userName == null) return false;
        Long userId = (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                org.hibernate.SQLQuery q = session.createSQLQuery("SELECT userId FROM ex_user WHERE userName=?");
                q.addScalar("userId", org.hibernate.Hibernate.LONG);
                q.setString(0, userName);
                Iterator results = q.list().iterator();
                return (results.hasNext()) ? results.next() : null;
            }
        });
        return (userId != null && userId.longValue() > 0);
    }

    public Collection<UserAuth> getUsers() {
        return getHibernateTemplate().loadAll(UserAuth.class);
    }

    public UserAuth getUser(long id) {
        return (UserAuth) getHibernateTemplate().get(UserAuth.class, id);
    }

    public UserAuth getUser(String userName) {
        UserAuth u = null;
        if (userName != null) {
            List list = getHibernateTemplate().find(GET_USER_BY_USERNAME, userName);
            if (list != null && !list.isEmpty()) u = (UserAuth) list.get(0);
        }
        return u;
    }

    public UserAuth createUser(UserAuth u) {
        String userName = u.getUserName();
        if (userExists(userName)) throw new DataIntegrityViolationException(userName);
        u.setCreatedOn(System.currentTimeMillis());
        getHibernateTemplate().save(u);
        return u;
    }

    public void updateUser(UserAuth u) {
        if (u != null) getHibernateTemplate().update(u);
    }

    public boolean deleteUser(UserAuth u) {
        if (u == null) return false;
        boolean wasDeleted = false;
        try {
            getHibernateTemplate().delete(u);
            wasDeleted = true;
        } catch (Exception zzz) {
            zzz.printStackTrace();
        }
        return wasDeleted;
    }

    private static final String GET_USER_BY_USERNAME = "from UserAuth u where u.userName=?";
}