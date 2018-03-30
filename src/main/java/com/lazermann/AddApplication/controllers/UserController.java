package com.lazermann.AddApplication.controllers;

import com.lazermann.AddApplication.dto.UserDto;
import com.lazermann.AddApplication.model.User;
import com.lazermann.AddApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    UserService userService;



    @RequestMapping(value="/createUser", method = RequestMethod.POST)
    public ResponseEntity create(String username) {
        try {
            User user = new User(username);
            userService.save(user);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Users created succesfully!",HttpStatus.CREATED);

    }

    @RequestMapping(value="/getUser", method = RequestMethod.GET)
    public ResponseEntity getUser(String id) {
        UserDto userDto;
        try {
            userDto = userService.getUserById(id);
        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("There is now user with id " + id ,HttpStatus.BAD_REQUEST);
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

}