package com.lazermann.myio.master.model;


import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="HttpServer")
public class HttpServer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String URL;
    private Region region;
    private long lastHeartbeat;
    private GameType gameType;
    private boolean active;


    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<GameServer> gameServers;


    private long totalPlayers;
    private long maxPlayers;


    public HttpServer() {
    }

    public HttpServer(String URL, Region region, long lastHeartbeat, GameType gameType, List<GameServer> gameServers) {
        this.URL = URL;
        this.region = region;
        this.lastHeartbeat = lastHeartbeat;
        this.gameType = gameType;
        this.gameServers = gameServers;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
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

    public List<GameServer> getGameServers() {
        return gameServers;
    }

    public void setGameServers(List<GameServer> gameServers) {
        this.gameServers = gameServers;
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

    public long getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(long totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public long getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(long maxPlayers) {
        this.maxPlayers = maxPlayers;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
