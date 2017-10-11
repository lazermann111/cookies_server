package com.lazermann.AddApplication.dao;

import com.lazermann.AddApplication.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDao {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(User user) {
        getSession().save(user);
        //return;
    }

    public void fillDb() {
        User c = new User("John", "doe@gmail.com");
        c.setPointsToday(100);
        c.setPointsTotal(885);
        getSession().save(c);

        User u = new User("John", "doe@gmail.com");
        u.setPointsToday(80);
        u.setPointsTotal(885);
        getSession().save(u);

    }

    public void delete(User user) {
        getSession().delete(user);
        //return;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return getSession().createQuery("from User").list();
    }

    public User getByEmail(String email) {
        return (User) getSession().createQuery(
                "from User where email = :email")
                .setParameter("email", email)
                .uniqueResult();
    }

    public User getById(long id) {
        return (User) getSession().load(User.class, id);
    }

    public void update(User user) {
        getSession().update(user);
        return;
    }

}
