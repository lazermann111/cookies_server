package com.lazermann.myio.master.dto;


import com.lazermann.myio.master.model.GameServerState;

public class GameServerDto   {

    private boolean active;
    private int playersNumber;
    private int maxPlayersNumber;

    private int worldId;
    private GameServerState serverState;


    public int getWorldId() {
        return worldId;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public GameServerDto() {
    }

    public GameServerDto(boolean active, int playersNumber, int maxPlayersNumber, int worldId,  GameServerState s) {
        this.active = active;
        this.playersNumber = playersNumber;
        this.maxPlayersNumber = maxPlayersNumber;
        this.worldId = worldId;
        serverState = s;
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

    public int getMaxPlayersNumber() {
        return maxPlayersNumber;
    }

    public void setMaxPlayersNumber(int maxPlayersNumber) {
        this.maxPlayersNumber = maxPlayersNumber;
    }

    public GameServerState getServerState() {
        return serverState;
    }

    public void setServerState(GameServerState serverState) {
        this.serverState = serverState;
    }
}


