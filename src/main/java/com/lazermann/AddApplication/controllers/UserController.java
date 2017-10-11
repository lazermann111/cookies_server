package com.lazermann.AddApplication.controllers;

import com.lazermann.AddApplication.dao.UserDao;
import com.lazermann.AddApplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    private UserDao _userDao;

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public String delete(long id) {
        try {
            User user = new User(id);
            _userDao.delete(user);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "User succesfully deleted!";
    }

    @RequestMapping(value="/get-by-email", method = RequestMethod.GET)
    public String getByEmail(String email) {
        String userId;
        try {
            User user = _userDao.getByEmail(email);
            userId = String.valueOf(user.getId());
        }
        catch(Exception ex) {
            return "User not found";
        }
        return "The user id is: " + userId;
    }

    @RequestMapping(value="/save")
    public String create(String email, String name) {
        try {
            User user = new User(email, name);
            _userDao.save(user);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "User succesfully saved!";
    }
    @RequestMapping(value="/cheat")
    public String cheat(String email, String name) {
        try {
            User user = new User(email, name);
            _userDao.fillDb();
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "User succesfully saved!";
    }


}