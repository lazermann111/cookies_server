package com.lazermann.AddApplication.services;


import com.lazermann.AddApplication.dao.UserDao;
import com.lazermann.AddApplication.dto.UserDto;
import com.lazermann.AddApplication.model.User;
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

    public UserDto getUserById(String id) throws Exception {
        UserDto userDto = userDao.getUserById(id);
        if(userDto == null)
            throw new IllegalArgumentException("There is no user with id " + id);
        if(!userDto.isActive())
            throw new IllegalArgumentException("Card " + id + " is blocked!");
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



}
