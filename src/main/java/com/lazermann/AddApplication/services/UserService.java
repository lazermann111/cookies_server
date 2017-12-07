package com.lazermann.AddApplication.services;


import com.lazermann.AddApplication.dao.UserDao;
import com.lazermann.AddApplication.dto.UserDto;
import com.lazermann.AddApplication.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;


    public User getByEmail(String email) throws Exception {
        return userDao.getByEmail(email);
    }

    public User getById(long id) throws Exception {
        return userDao.getUserById(id);
    }


    public void fillDB() throws Exception {
        userDao.fillDb();
    }

    public void delete(User user) throws Exception {
        userDao.delete(user);
    }

    public void save(User user) throws Exception {
        userDao.save(user);
    }

    public List<UserDto> getAll() {
        return userDao.getAll();
    }

    public float getBalance(long card_id) {
        return userDao.getBalance(card_id);
    }
}
