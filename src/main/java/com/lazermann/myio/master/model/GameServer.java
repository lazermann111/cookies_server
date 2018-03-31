package com.lazermann.myio.master.model;

import javax.persistence.*;

@Entity
@Table(name="GameServer")
public class GameServer {


    public GameServer() {
    }

    public GameServer( boolean active, int playersNumber, int maxPlayersNumber)
    {

        this.active = active;
        this.playersNumber = playersNumber;
        this.maxPlayersNumber = maxPlayersNumber;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean active;
    private int playersNumber;
    private int maxPlayersNumber;
    private int worldId;


    public int getWorldId() {
        return worldId;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
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
