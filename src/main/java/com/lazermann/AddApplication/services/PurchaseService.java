package com.lazermann.AddApplication.services;

import com.lazermann.AddApplication.dao.PurchaseDao;
import com.lazermann.AddApplication.dao.UserDao;
import com.lazermann.AddApplication.dto.PurchaseDto;
import com.lazermann.AddApplication.dto.UserDto;
import com.lazermann.AddApplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    UserDao userDao;

    public void fillDB() throws Exception {
        purchaseDao.fillDB();
    }

    public ResponseEntity addPurchase(String card_id, String name, String price) throws Exception {
        purchaseDao.addPurchase(card_id, name, price);
        User user = userDao.getUser(card_id);
        if(user.getBalance() < Float.parseFloat(price))
            return new ResponseEntity<>("There is not enough money", HttpStatus.BAD_REQUEST);
        user.setBalance(user.getBalance() - Float.parseFloat(price));
        userDao.update(user);
        return new ResponseEntity<>("Successfully paid " + price + "$", HttpStatus.OK);

    }

    public List<PurchaseDto> getAllPurchases(String card_id) throws Exception {
        return purchaseDao.getAllPurchases(card_id);
    }

    public List<PurchaseDto> getAllPurchases(Date from, Date to) throws Exception {
        return purchaseDao.getAllPurchases(from, to);
    }

    public List<PurchaseDto> getAllPurchasesForDay() throws Exception {
        Date from = new Date();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.HOUR,0);
        from = date.getTime();

        Date to = new Date();
        date.set(Calendar.HOUR, 24);

        return purchaseDao.getAllPurchases(from, to);
    }
}
