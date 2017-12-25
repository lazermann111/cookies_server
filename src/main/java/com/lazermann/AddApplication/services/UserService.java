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

    public UserDto getUserById(String cardId) throws Exception {
        UserDto userDto = userDao.getUserById(cardId);
        if(userDto == null)
            throw new IllegalArgumentException("There is no user with id " + cardId);
        if(!userDto.isActive())
            throw new IllegalArgumentException("Card " + cardId + " is blocked!");
        return userDto;
    }


    public void fillDB() throws Exception {
        userDao.fillDb();
    }

    public void delete(User user) throws Exception {
        userDao.delete(user);
    }

    public void save(User user) throws Exception {
        if(userDao.getUser(user.getBadgeNumber()) != null)
            throw new IllegalArgumentException("There is already exist user with card id " + user.getBadgeNumber());
        userDao.save(user);
    }

    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers();
    }

    public float getBalance(String card_id) throws Exception{
        User user = userDao.getUser(card_id);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + card_id);
        if(!user.isActive())
            throw new IllegalArgumentException("Card " + card_id + " is blocked!");
        float balance = userDao.getBalance(card_id);
        return balance;
    }

    public void blockCard(String cardId) throws Exception{
        User user = userDao.getUser(cardId);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + cardId);
        if(!user.isActive())
            throw new IllegalArgumentException("Card " + cardId + " is already blocked!");
        user.setActive(false);
        userDao.update(user);
    }

    public void unblockCard(String cardId) throws Exception {
        User user = userDao.getUser(cardId);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + cardId);
        if(user.isActive())
            throw new IllegalArgumentException("Card " + cardId + " is already unblocked!");
        user.setActive(true);
        userDao.update(user);
    }

    public String updateBalance(String cardId, String amount) {
        User user = userDao.getUser(cardId);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + cardId);
        if(!user.isActive())
            throw new IllegalArgumentException("Card " + cardId + " is blocked!");
        float balance = userDao.getBalance(cardId);
        balance += Float.parseFloat(amount);
        user.setBalance(balance);
        userDao.update(user);

        return "" + balance;
    }
}
