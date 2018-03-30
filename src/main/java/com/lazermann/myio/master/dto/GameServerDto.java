package com.lazermann.myio.master.dto;


import com.lazermann.myio.master.model.Region;

public class GameServerDto extends GameServerBaseDto {

    private boolean active;
    private long lastHeartbeat;


    public GameServerDto(long id, Region region, int playersNumber, String URL) {
        super(id, region, playersNumber, URL);
    }

    public GameServerDto(long id, Region region, int playersNumber, String URL, boolean active, long lastHeartbeat) {
        super(id, region, playersNumber, URL);
        this.active = active;
        this.lastHeartbeat = lastHeartbeat;


    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(long lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
}


