package com.lazermann.myio.master.controllers;

import com.lazermann.myio.master.dto.ServerBaseDto;
import com.lazermann.myio.master.model.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/server")
public class GameServerController
{


    public ResponseEntity registerNewServer(ServerBaseDto baseDto)
    {
        return null;
    }


    public ResponseEntity heartBeat(ServerBaseDto baseDto)
    {
        return null;
    }

    public ResponseEntity getServerInRegion(Region regionToPlay)
    {
        return null;
    }
}
