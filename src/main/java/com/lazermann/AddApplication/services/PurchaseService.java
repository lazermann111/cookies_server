package com.lazermann.AddApplication.services;

import com.lazermann.AddApplication.dao.PurchaseDao;
import com.lazermann.AddApplication.dao.UserDao;
import com.lazermann.AddApplication.dto.PurchaseDto;
import com.lazermann.AddApplication.dto.UserDto;
import com.lazermann.AddApplication.model.Purchase;
import com.lazermann.AddApplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public ResponseEntity addPurchase(String card_id, String price) throws Exception {
        User user = userDao.getUser(card_id);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + card_id);
        if(user.getBalance() < Float.parseFloat(price))
            return new ResponseEntity<>("There is not enough money.\n Balance: " + user.getBalance(), HttpStatus.BAD_REQUEST);

        PurchaseDto purchaseDto = purchaseDao.addPurchase(card_id, price);//add to table Purchase
        user.setBalance(user.getBalance() - Float.parseFloat(price));
        userDao.update(user);

        return new ResponseEntity<>(purchaseDto, HttpStatus.OK);

    }

    public List<PurchaseDto> getAllPurchases(String card_id) throws Exception {
        User user = userDao.getUser(card_id);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + card_id);
        return purchaseDao.getAllPurchases(card_id);
    }

    public List<PurchaseDto> getAllPurchases(Date from, Date to) throws Exception {
        //LocalDateTime dateTimeFrom = LocalDateTime.of(from.getYear(), from.getMonth(), from.getDay(), 0, 0);
        //LocalDateTime dateTimeTo = LocalDateTime.of(to.getYear(), to.getMonth(), to.getDay(), 24, 0);
        Calendar fromC = Calendar.getInstance();
        fromC.setTime(from);
        Calendar toC = Calendar.getInstance();
        toC.setTime(to);
        return purchaseDao.getAllPurchases(fromC, toC);
    }

    public List<PurchaseDto> getAllPurchasesForDay() throws Exception {
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.set(Calendar.HOUR_OF_DAY, 0);
        dateFrom.set(Calendar.MINUTE, 0);
        dateFrom.set(Calendar.HOUR,0);
        System.out.println("Date from: " + dateFrom.toString());


        Calendar dateTo = Calendar.getInstance();
        dateTo.set(Calendar.HOUR_OF_DAY, 23);
        dateTo.set(Calendar.MINUTE, 59);
        dateTo.set(Calendar.HOUR, 23);

        System.out.println("Date from: " + dateTo.toString());


        return purchaseDao.getAllPurchases(dateFrom, dateTo);
    }

    public ResponseEntity refund(String card_id, String purchaseId, String price) throws Exception {
        Purchase purchase = purchaseDao.getPurchase(card_id, purchaseId);
        if(purchase == null)
            throw new IllegalArgumentException("There is no purchase with id " + purchaseId);
        if(purchase.getPrice() != Float.parseFloat(price))
            throw new IllegalArgumentException("Entered wrong amount!");

        User user = userDao.getUser(card_id);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + card_id);
        if(purchase.getPrice() < 0)
            throw new IllegalArgumentException("Your entered id for refund operation");
        user.setBalance(user.getBalance() + purchase.getPrice());

        userDao.update(user);
        purchaseDao.addPurchase(card_id, String.valueOf(purchase.getPrice()*(-1.0)));

        return new ResponseEntity<>("Purchase " + purchaseId + "is refunded! \nBalance: " + user.getBalance(), HttpStatus.OK);
    }
}
