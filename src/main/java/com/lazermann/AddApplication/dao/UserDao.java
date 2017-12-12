package com.lazermann.AddApplication.dao;

import com.lazermann.AddApplication.dto.UserDto;
import com.lazermann.AddApplication.helpers.DozerHelper;
import com.lazermann.AddApplication.model.User;
import org.dozer.DozerBeanMapper;
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

    @Autowired
    DozerBeanMapper dozerMapper;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(User user) throws Exception {
        getSession().save(user);
        //return;
    }

    public void fillDb() throws Exception {
        User c = new User("John", "Doe", "doe@gmail.com", 300f);

        getSession().save(c);

        User u = new User("Jack", "Dickerson", "dickerson@gmail.com", 300f);

        getSession().save(u);
    }

    public void delete(User user) throws Exception {
        getSession().delete(user);
        //return;
    }

    @SuppressWarnings("unchecked")
    public User getUser(String card_id) {
        return (User) getSession().createQuery(
                "from User user where user.id = :card_id")
                .setParameter("card_id", Long.parseLong(card_id))
                .uniqueResult();
    }


    @SuppressWarnings("unchecked")
    public List<UserDto> getAllUsers() {
        List<User> list = getSession().createQuery("from User").list();
        return DozerHelper.map(dozerMapper, list, UserDto.class);
    }

    public User getByEmail(String email) throws Exception {
        return (User) getSession().createQuery(
                "from User where email = :email")
                .setParameter("email", email)
                .uniqueResult();
    }

    public UserDto getUserById(long id) throws Exception {
        User user = (User) getUser(String.valueOf(id));
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + id);
        return dozerMapper.map(user, UserDto.class);
    }

    public void update(User user) {
        getSession().update(user);
        return;
    }


    public float getBalance(long card_id) {
        if(getUser(String.valueOf(card_id)) == null)
            throw new IllegalArgumentException("There is no user with id " + card_id);
        float value = (float)getSession().createQuery(
                "select usr.balance from User usr where usr.id = :card_id")
                .setParameter("card_id", card_id)
                .uniqueResult();
        return value;
    }

    public void updateBalance(String card_id) {

    }
}
