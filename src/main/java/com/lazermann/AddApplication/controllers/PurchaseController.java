package com.lazermann.AddApplication.controllers;

import com.lazermann.AddApplication.dto.PurchaseDto;
import com.lazermann.AddApplication.model.Purchase;
import com.lazermann.AddApplication.services.PurchaseService;
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
    public ResponseEntity addPurchase(String card_id, String price) {
        ResponseEntity response;
        try {
            response = purchaseService.addPurchase(card_id, price);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @RequestMapping(value = "/getAllPurchases", method = RequestMethod.GET)
    public ResponseEntity getAllPurchases(String card_id) {
        List<PurchaseDto> purchases = null;
        try {
            purchases = purchaseService.getAllPurchases(card_id);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPurchasesFromTo", method = RequestMethod.GET)
    public ResponseEntity getAllPurchases(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd")Date from, @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to) {
        List<PurchaseDto> purchases = null;
        try {
            purchases = purchaseService.getAllPurchases(from, to);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllPurchasesForDay", method = RequestMethod.GET)
    public ResponseEntity getAllPurchasesForDay() {
        List<PurchaseDto> purchases = null;
        try {
            purchases = purchaseService.getAllPurchasesForDay();
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ResponseEntity refund(String card_id, String purchaseId) {
        ResponseEntity response;
        try {
            response = purchaseService.refund(card_id, purchaseId);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
