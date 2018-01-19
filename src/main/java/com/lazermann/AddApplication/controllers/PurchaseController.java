package com.lazermann.AddApplication.controllers;

import com.lazermann.AddApplication.dto.FullPurchaseDto;
import com.lazermann.AddApplication.dto.PurchaseDto;
import com.lazermann.AddApplication.model.Purchase;
import com.lazermann.AddApplication.services.PurchaseService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/purchase")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @RequestMapping(value="/cheat", method = RequestMethod.GET)
    public ResponseEntity cheat() {
        try {
            purchaseService.fillDB();
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Purchase succesfully saved!", HttpStatus.OK);

    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ResponseEntity addPurchase(String cardId, String price, String employeeId) {
        ResponseEntity response;
        try {
            response = purchaseService.addPurchase(cardId, price, employeeId);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @RequestMapping(value = "/getAllPurchases", method = RequestMethod.GET)
    public ResponseEntity getAllPurchases(String cardId, String employeeId) {
        List<FullPurchaseDto> purchases = null;
        try {
            purchases = purchaseService.getAllPurchases(cardId, employeeId);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPurchasesFromTo", method = RequestMethod.GET)
    public ResponseEntity getAllPurchases(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd")Date from, @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to, String employeeId) {
        List<FullPurchaseDto> purchases = null;
        try {
            purchases = purchaseService.getAllPurchases(from, to, employeeId);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPurchasesForDay", method = RequestMethod.GET)
    public ResponseEntity getAllPurchasesForDay(String employeeId) {
        List<FullPurchaseDto> purchases = null;
        try {
            purchases = purchaseService.getAllPurchasesForDay(employeeId);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPurchasesXReport", method = RequestMethod.GET)
    public ResponseEntity getAllPurchasesXReport(String employeeId) {
        List<FullPurchaseDto> purchases = null;
        try {
            purchases = purchaseService.getAllPurchasesXReport(employeeId);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPurchasesZReport", method = RequestMethod.GET)
    public ResponseEntity getAllPurchasesZReport(String employeeId) {
        List<FullPurchaseDto> purchases = null;
        try {
            purchases = purchaseService.getAllPurchasesZReport(employeeId);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ResponseEntity refund(String cardId, String purchaseId, String price, String employeeId) {
        ResponseEntity response;
        try {
            response = purchaseService.refund(cardId, purchaseId, price, employeeId);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @RequestMapping(value = "/confirmPass", method = RequestMethod.GET)
    public ResponseEntity confirmPass(String password) {
        ResponseEntity responseEntity;
        try {
            responseEntity = purchaseService.confirmPass(password);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/createPassword", method = RequestMethod.POST)
    public ResponseEntity createPassword(String password) {
        ResponseEntity responseEntity;
        try {
            responseEntity = purchaseService.createPassword(password);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/getLastTimeUpdated", method = RequestMethod.GET)
    public ResponseEntity getLastTimeUpdated(String employeeId) {
        ResponseEntity responseEntity;
        try {
            responseEntity = purchaseService.getLastTimeUpdated(employeeId);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
