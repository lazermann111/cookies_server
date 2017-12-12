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

    public UserDto getUserById(long id) throws Exception {
        UserDto userDto = userDao.getUserById(id);
        if(userDto == null)
            throw new IllegalArgumentException("There is no user with id " + id);
        return userDto;
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

    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers();
    }

    public float getBalance(long card_id) {
        float balance = userDao.getBalance(card_id);
        return balance;
    }
}
