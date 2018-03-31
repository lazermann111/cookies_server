package com.lazermann.myio.master.model;

import javax.persistence.*;

@Entity
@Table(name="GameServer")
public class GameServer {


    public GameServer() {
    }

    public GameServer(HttpServer owner, boolean active, int playersNumber, int maxPlayersNumber)
    {
        this.owner = owner;
        this.active = active;
        this.playersNumber = playersNumber;
        this.maxPlayersNumber = maxPlayersNumber;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private HttpServer owner;
    private boolean active;
    private int playersNumber;
    private int maxPlayersNumber;



    public HttpServer getOwner() {
        return owner;
    }

    public void setOwner(HttpServer owner) {
        this.owner = owner;
    }

    public int getMaxPlayersNumber() {
        return maxPlayersNumber;
    }

    public void setMaxPlayersNumber(int maxPlayersNumber) {
        this.maxPlayersNumber = maxPlayersNumber;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }




}
