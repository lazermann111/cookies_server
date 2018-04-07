package com.lazermann.myio.master.controllers;

import com.lazermann.myio.master.dao.ServerDao;
import com.lazermann.myio.master.dto.GameServerDto;
import com.lazermann.myio.master.dto.HttpServerBaseDto;
import com.lazermann.myio.master.dto.HttpServerDto;
import com.lazermann.myio.master.model.HttpServer;
import com.lazermann.myio.master.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/server")
public class ServerController
{
    @Autowired
    ServerDao serverDao;

    /*
    *  Called from each server in a some period, like 5-10 sec
    *  contains actual server state.
    */
    @RequestMapping(value="/heartbeat", method = RequestMethod.POST)
    public ResponseEntity heartBeat(@RequestBody HttpServerDto baseDto)
    {
        HttpServer res = null;
        if(baseDto.getURL() == null || baseDto.getRegion() == null)
            return  new ResponseEntity<>("Wrong server info! " + baseDto,HttpStatus.BAD_REQUEST);
        try {

            // Basically we have constraint on server URL, since it cannot be 2 servers with same url
           serverDao.saveOrUpdate(baseDto);

        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @RequestMapping(value="/getServer", method = RequestMethod.GET)
    public ResponseEntity getServerInRegion(Region regionToPlay)
    {

        try {
            HttpServerDto server  = serverDao.getServerToConnect(regionToPlay);
            if(server == null)
                return new ResponseEntity<>("No active servers here: " + regionToPlay ,HttpStatus.INTERNAL_SERVER_ERROR);
            else  return new ResponseEntity<>(server, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }

    }

    /*
     *  Called in MainMenuScreen of client
     *  @return List of all active non-full servers, sorted by players number DESC
     */
    @RequestMapping(value="/getServersForAllRegions", method = RequestMethod.GET)
    public ResponseEntity getServersForAllRegions()
    {
        List<HttpServerBaseDto> res;
        try {
            res  = serverDao.getServersForAllRegions();
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /*
        * Used for quick cleanup of database, to avoid situation of duplicated HttpServer infos
        * Doesn't harm our logic, since active servers have to send updates quite often,
        * so new info will be in 5-10 sec
        */
    @RequestMapping(value="/dropAll", method = RequestMethod.GET)
    public ResponseEntity dropAll() {

        try {
            serverDao.dropAllServersInfo();
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Servers dropped successfully!", HttpStatus.OK);
    }

    /*
    *  Returns List of all active  servers in given region
    */
    @RequestMapping(value="/getServers", method = RequestMethod.GET)
    public ResponseEntity getAllServersInRegion(Region regionToPlay)
    {
        List<GameServerDto> res;
        try {
            res  = serverDao.getServersInRegion(regionToPlay);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(value="/cheat", method = RequestMethod.GET)
    public ResponseEntity cheat() {
        try {
            serverDao.fillDb();
        }
        catch(Exception ex) {
            return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Servers created successfully!",HttpStatus.CREATED);
    }

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<HttpServerDto> data = null;
        try {
            data = serverDao.getAllServers();
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


}
