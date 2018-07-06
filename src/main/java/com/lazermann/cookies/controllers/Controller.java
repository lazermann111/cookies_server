package com.lazermann.cookies.controllers;

import com.lazermann.cookies.dao.Dao;
import com.lazermann.cookies.dto.CookieInfoDto;
import com.lazermann.cookies.dto.YtCounterDto;
import com.lazermann.cookies.model.CookieInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/cookies")
@CrossOrigin
public class Controller {

    @Autowired
    Dao dao;



    @RequestMapping(value="/addCookie", method = RequestMethod.POST)
    public ResponseEntity create(String proxy, String cookie) {
        try
        {
            CookieInfo cookieInfo = new CookieInfo();
            cookieInfo.setCookie(cookie);
            cookieInfo.setProxy(proxy);

            dao.save(cookieInfo);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Cookies added succesfully!",HttpStatus.CREATED);

    }




    @RequestMapping(value="/getCookie", method = RequestMethod.GET)
    public ResponseEntity getCookie(String proxy) {
        CookieInfoDto c;
        try
        {
            c = dao.getCookieByProxy(proxy);
        }
        catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("There is no cookies with proxy " + proxy ,HttpStatus.BAD_REQUEST);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(c,HttpStatus.CREATED);

    }



    @RequestMapping(value="/getAllProxies", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<String> data = null;
        try
        {
            data = dao.getAllProxies();
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }


    @RequestMapping(value="/getCounter", method = RequestMethod.GET)
    public ResponseEntity getCounter(String url) {
        Long res = -1l;
        try
        {
            YtCounterDto d = dao.getYtCounterByURL(url);
            res = d.getCounter();
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @RequestMapping(value="/increaseCounter", method = RequestMethod.GET)
    public ResponseEntity increaseCounter(String url) {

        try
        {
           dao.increaseYtCounter(url);

        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }



}