package com.lazermann.myio.master.dto;


import com.lazermann.myio.master.model.Region;

public class ServerBaseDto {

    private long id;
    private Region region;
    private int playersNumber;
    private String URL;

    public ServerBaseDto(long id, Region region, int playersNumber, String URL) {
        this.id = id;
        this.region = region;
        this.playersNumber = playersNumber;
        this.URL = URL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
