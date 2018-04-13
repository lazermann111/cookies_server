package com.lazermann.myio.master.dto;


import com.lazermann.myio.master.model.GameType;

import java.util.List;

public class TeamDto
{

    private long id;

    // selected by team leader
    private GameType gameType;

    // selected by team leader
    private boolean autoFill;

    // sets to true in moment when leader press play
    // at this point everyone will receive update and start to join
    private boolean ingame;

    // sets to true in moment when leader press play
    private String serverToConnect;

    private List<String> teamMemberNames;


    private String teamLeaderName;


    public TeamDto() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public boolean isAutoFill() {
        return autoFill;
    }

    public void setAutoFill(boolean autoFill) {
        this.autoFill = autoFill;
    }

    public boolean isIngame() {
        return ingame;
    }

    public void setIngame(boolean ingame) {
        this.ingame = ingame;
    }

    public String getServerToConnect() {
        return serverToConnect;
    }

    public void setServerToConnect(String serverToConnect) {
        this.serverToConnect = serverToConnect;
    }

    public List<String> getTeamMemberNames() {
        return teamMemberNames;
    }

    public void setTeamMemberNames(List<String> teamMemberNames) {
        this.teamMemberNames = teamMemberNames;
    }

    public String getTeamLeaderName() {
        return teamLeaderName;
    }

    public void setTeamLeaderName(String teamLeaderName) {
        this.teamLeaderName = teamLeaderName;
    }
}
