package com.lazermann.AddApplication.model;

import javax.persistence.*;

@Entity
@Table(name="servers")
public class GameServer {

    public GameServer(Region region, boolean active, long lastHeartbeat, int playersNumber, String URL) {
        this.region = region;
        this.active = active;
        this.lastHeartbeat = lastHeartbeat;
        this.playersNumber = playersNumber;
        this.URL = URL;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Region region;
    private boolean active;
    private long lastHeartbeat;
    private int playersNumber;
    private String URL;


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
