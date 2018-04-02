package com.lazermann.myio.master.dto;

import com.lazermann.myio.master.model.GameType;
import com.lazermann.myio.master.model.Region;

public class HttpServerBaseDto
{

    private long id;
    private String URL;
    private Region region;
    private long lastHeartbeat;
    private GameType gameType;
    private int totalPlayers;
    private int maxPlayers;
    public HttpServerBaseDto(){}

    public HttpServerBaseDto(long id, String URL, Region region, long lastHeartbeat, GameType gameType) {
        this.id = id;
        this.URL = URL;
        this.region = region;
        this.lastHeartbeat = lastHeartbeat;
        this.gameType = gameType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public long getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(long lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
