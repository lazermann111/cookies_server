package com.lazermann.myio.master.model;

import javax.persistence.*;
import java.util.List;

/**
 * Non-random squad by invites
 */

@Entity
@Table(name="Team")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="nickname")
    private List<String> teamMemberNames;


    private String teamLeaderName;


    public Team() {
    }

    public Team(long id, GameType gameType, boolean autoFill, boolean ingame, String serverToConnect, List<String> teamMemberNames) {
        this.id = id;
        this.gameType = gameType;
        this.autoFill = autoFill;
        this.ingame = ingame;
        this.serverToConnect = serverToConnect;
        this.teamMemberNames = teamMemberNames;
    }

    public Long getId() {
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

    public void setTeamMemberNames(List<String> userList) {
        this.teamMemberNames = userList;
    }

    public String getTeamLeaderName() {
        return teamLeaderName;
    }

    public void setTeamLeaderName(String teamLeaderName) {
        this.teamLeaderName = teamLeaderName;
    }
}

