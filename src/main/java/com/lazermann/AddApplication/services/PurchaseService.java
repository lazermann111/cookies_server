package com.lazermann.AddApplication.services;

import com.lazermann.AddApplication.dao.PurchaseDao;
import com.lazermann.AddApplication.dao.UserDao;
import com.lazermann.AddApplication.dto.FullPurchaseDto;
import com.lazermann.AddApplication.dto.PurchaseDto;
import com.lazermann.AddApplication.dto.UserDto;
import com.lazermann.AddApplication.model.Employee;
import com.lazermann.AddApplication.model.Password;
import com.lazermann.AddApplication.model.Purchase;
import com.lazermann.AddApplication.model.User;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    PurchaseDao purchaseDao;

    @Autowired
    UserDao userDao;

    @Autowired
    DozerBeanMapper dozerMapper;

    public void fillDB() throws Exception {
        purchaseDao.fillDB();
    }

    public ResponseEntity addPurchase(String card_id, String price, String employeeId) throws Exception {
        User user = userDao.getUser(card_id);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + card_id);
        if(!user.isActive())
            throw new IllegalArgumentException("Card " + card_id + " is blocked!");
        if(user.getBalance() < Float.parseFloat(price))
            return new ResponseEntity<>("There is not enough money.\n Balance: " + user.getBalance(), HttpStatus.BAD_REQUEST);

        PurchaseDto purchaseDto = purchaseDao.addPurchase(card_id, price, employeeId);//add to table Purchase
        user.setBalance(user.getBalance() - Float.parseFloat(price));
        userDao.update(user);

        return new ResponseEntity<>(purchaseDto, HttpStatus.OK);

    }

    public List<FullPurchaseDto> getAllPurchases(String card_id, String employeeId) throws Exception {
        User user = userDao.getUser(card_id);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + card_id);

        List<PurchaseDto> purchases = purchaseDao.getAllPurchases(card_id, employeeId);

        //List<UserDto> users = userDao.getAllUsers();
        List<FullPurchaseDto> fullPurchases = new ArrayList<>();

        for (PurchaseDto purchase: purchases) {
            if (user.getBadgeNumber().equals(purchase.getBadgeId())) {
                fullPurchases.add(new FullPurchaseDto(purchase.getId(), user.getBadgeNumber(), user.getFirstName(), user.getLastName(), user.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                //break;
            }
        }

        /*for (PurchaseDto purchase: purchases) {
            for (UserDto userDto: users) {
                if (userDto.getBadgeNumber().equals(purchase)) {
                    fullPurchases.add(new FullPurchaseDto(purchase.getId(), userDto.getBadgeNumber(), userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                    break;
                }
            }
        }*/
        return fullPurchases;
    }

    public List<FullPurchaseDto> getAllPurchases(Date from, Date to, String employeeId) throws Exception {
        Calendar fromC = Calendar.getInstance();
        fromC.setTime(from);
        Calendar toC = Calendar.getInstance();
        toC.setTime(to);

        List<PurchaseDto> purchases = purchaseDao.getAllPurchases(fromC, toC, employeeId);

        List<UserDto> users = userDao.getAllUsers();
        List<FullPurchaseDto> fullPurchases = new ArrayList<>();

        for (PurchaseDto purchase: purchases) {
            for (UserDto userDto: users) {
                if (userDto.getBadgeNumber().equals(purchase.getBadgeId())) {
                    fullPurchases.add(new FullPurchaseDto(purchase.getId(), userDto.getBadgeNumber(), userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                    break;
                }
            }
        }

        return fullPurchases;
    }

    public List<FullPurchaseDto> getAllPurchasesForDay(String employeeId) throws Exception {
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


        List<PurchaseDto> purchases = purchaseDao.getAllPurchases(dateFrom, dateTo, employeeId);
        List<UserDto> users = userDao.getAllUsers();
        List<FullPurchaseDto> fullPurchases = new ArrayList<>();

        for (PurchaseDto purchase: purchases) {
            for (UserDto userDto: users) {
                if (userDto.getBadgeNumber().equals(purchase.getBadgeId())) {
                    fullPurchases.add(new FullPurchaseDto(purchase.getId(), userDto.getBadgeNumber(), userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                    break;
                }
            }
        }

        return fullPurchases;
    }

    public ResponseEntity refund(String card_id, String purchaseId, String price, String employeeId) throws Exception {
        Purchase purchase = purchaseDao.getPurchase(card_id, purchaseId);
        if(purchase == null)
            throw new IllegalArgumentException("There is no purchase with id " + purchaseId);
        if(purchase.getPrice() != Float.parseFloat(price))
            throw new IllegalArgumentException("Entered wrong amount!");

        User user = userDao.getUser(card_id);
        if(user == null)
            throw new IllegalArgumentException("There is no user with id " + card_id);
        if(!user.isActive())
            throw new IllegalArgumentException("Card " + card_id + " is blocked!");
        if(purchase.getPrice() < 0)
            throw new IllegalArgumentException("Your entered id for refund operation");
        user.setBalance(user.getBalance() + purchase.getPrice());

        userDao.update(user);
        purchaseDao.addPurchase(card_id, String.valueOf(purchase.getPrice()*(-1.0)), employeeId);
        PurchaseDto purchaseDto = dozerMapper.map(purchase, PurchaseDto.class);

        //return new ResponseEntity<>("Purchase " + purchaseId + " is refunded! \nBalance: " + user.getBalance(), HttpStatus.OK);
        return new ResponseEntity<>(purchaseDto, HttpStatus.OK);
    }

    public ResponseEntity confirmPass(String pass) {
        Password password = purchaseDao.getPassword(pass);
        if(password == null) {
            return new ResponseEntity<>("Wrong password!", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("Confirmed!", HttpStatus.OK);
        }
    }

    public ResponseEntity createPassword(String pass) {
        Password password = purchaseDao.getPassword(pass);
        if(password != null) {
            return new ResponseEntity<>("This password is already exist!", HttpStatus.BAD_REQUEST);
        }
        userDao.createPassword(pass);
        return new ResponseEntity<>("Password created!", HttpStatus.OK);
    }

    public List<FullPurchaseDto> getAllPurchasesXReport(String employeeId) throws Exception {
        /*List<PurchaseDto> purchases =  purchaseDao.getAllPurchases(employeeId);
        List<UserDto> users = userDao.getAllUsers();
        List<FullPurchaseDto> fullPurchases = new ArrayList<>();

        for (PurchaseDto purchase: purchases) {
            for (UserDto userDto: users) {
                if (userDto.getBadgeNumber().equals(purchase.getBadgeId())) {
                    fullPurchases.add(new FullPurchaseDto(purchase.getId(), userDto.getBadgeNumber(), userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                    break;
                }
            }
        }*/

        Employee employee = purchaseDao.getEmployee(employeeId);
        if(employee == null) {
            employee = new Employee();
            employee.setEmployeeId(employeeId);
            Calendar calendar = Calendar.getInstance();
            employee.setLastDate(calendar);
            purchaseDao.saveEmployee(employee);

            List<PurchaseDto> purchases =  purchaseDao.getAllPurchases(employeeId);
            List<UserDto> users = userDao.getAllUsers();
            List<FullPurchaseDto> fullPurchases = new ArrayList<>();

            for (PurchaseDto purchase: purchases) {
                for (UserDto userDto: users) {
                    if (userDto.getBadgeNumber().equals(purchase.getBadgeId())) {
                        fullPurchases.add(new FullPurchaseDto(purchase.getId(), userDto.getBadgeNumber(), userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                        break;
                    }
                }
            }

            return fullPurchases;
        }
        else {
            Calendar dateFrom = employee.getLastDate();
            Calendar dateTo = Calendar.getInstance();
            //employee.setLastDate(dateTo);
            //purchaseDao.updateEmployee(employee);

            List<PurchaseDto> purchases = purchaseDao.getAllPurchases(dateFrom, dateTo, employeeId);
            List<UserDto> users = userDao.getAllUsers();
            List<FullPurchaseDto> fullPurchases = new ArrayList<>();

            for (PurchaseDto purchase : purchases) {
                for (UserDto userDto : users) {
                    if (userDto.getBadgeNumber().equals(purchase.getBadgeId())) {
                        fullPurchases.add(new FullPurchaseDto(purchase.getId(), userDto.getBadgeNumber(), userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                        break;
                    }
                }
            }

            return fullPurchases;
        }
    }

    public List<FullPurchaseDto> getAllPurchasesZReport(String employeeId) throws Exception {//rewrite date
        Employee employee = purchaseDao.getEmployee(employeeId);
        if(employee == null) {
            employee = new Employee();
            employee.setEmployeeId(employeeId);
            Calendar calendar = Calendar.getInstance();
            employee.setLastDate(calendar);
            purchaseDao.saveEmployee(employee);


            List<PurchaseDto> purchases =  purchaseDao.getAllPurchases(employeeId);
            List<UserDto> users = userDao.getAllUsers();
            List<FullPurchaseDto> fullPurchases = new ArrayList<>();

            for (PurchaseDto purchase: purchases) {
                for (UserDto userDto: users) {
                    if (userDto.getBadgeNumber().equals(purchase.getBadgeId())) {
                        fullPurchases.add(new FullPurchaseDto(purchase.getId(), userDto.getBadgeNumber(), userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                        break;
                    }
                }
            }

            return fullPurchases;
        }
        else {
            Calendar dateFrom = employee.getLastDate();
            Calendar dateTo = Calendar.getInstance();
            employee.setLastDate(dateTo);
            purchaseDao.updateEmployee(employee);

            List<PurchaseDto> purchases = purchaseDao.getAllPurchases(dateFrom, dateTo, employeeId);
            List<UserDto> users = userDao.getAllUsers();
            List<FullPurchaseDto> fullPurchases = new ArrayList<>();

            for (PurchaseDto purchase: purchases) {
                for (UserDto userDto: users) {
                    if (userDto.getBadgeNumber().equals(purchase.getBadgeId())) {
                        fullPurchases.add(new FullPurchaseDto(purchase.getId(), userDto.getBadgeNumber(), userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName(), purchase.getPrice(), purchase.getDate()));
                        break;
                    }
                }
            }

            return fullPurchases;
        }
    }

    public ResponseEntity getLastTimeUpdated(String employeeId) {
        Employee employee = purchaseDao.getEmployee(employeeId);
        if(employee == null)
            return new ResponseEntity<>("No record!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(employee.getLastDate(), HttpStatus.OK);
    }
}
