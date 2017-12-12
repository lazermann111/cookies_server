package com.lazermann.AddApplication.controllers;

import com.lazermann.AddApplication.dao.UserDao;
import com.lazermann.AddApplication.dto.UserDto;
import com.lazermann.AddApplication.model.User;
import com.lazermann.AddApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    UserService userService;

    /*@RequestMapping(value="/delete", method = RequestMethod.POST)
    public String delete(long id) {
        try {
            User user = new User(id);
            userService.delete(user);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "User succesfully deleted!";
    }*/

    /*@RequestMapping(value="/get-by-email", method = RequestMethod.GET)
    public String getByEmail(String email) {
        String userId;
        try {
            User user = userService.getByEmail(email);
            userId = String.valueOf(user.getId());
        }
        catch(Exception ex) {
            return "User not found";
        }
        return "The user id is: " + userId;
    }*/

    @RequestMapping(value="/createUser", method = RequestMethod.POST)
    public ResponseEntity create(String firstName, String secondName, String email, float balance) {
        try {
            User user = new User(firstName, secondName, email, balance);
            userService.save(user);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Users created succesfully!",HttpStatus.CREATED);

    }

    @RequestMapping(value="/getUser", method = RequestMethod.GET)
    public ResponseEntity getUser(String userId) {
        UserDto userDto;
        try {
            userDto = userService.getUserById(Long.parseLong(userId));
        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("There is now user with id " + userId ,HttpStatus.BAD_REQUEST);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userDto,HttpStatus.CREATED);

    }

    @RequestMapping(value="/cheat", method = RequestMethod.GET)
    public ResponseEntity cheat() {
        try {
            userService.fillDB();
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Users created succesfully!",HttpStatus.CREATED);
    }

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<UserDto> data = null;
        try {
            data = userService.getAllUsers();
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @RequestMapping(value="/getBalance", method = RequestMethod.GET)
    public ResponseEntity getBalance(String card_id) {
        float balance;
        try{
            balance = userService.getBalance(Long.parseLong(card_id));
        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("There is now user with id " + card_id ,HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            return new ResponseEntity<>("Error: " + ex.getMessage() ,HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }



}