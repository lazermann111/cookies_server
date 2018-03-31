package com.lazermann.myio.master.dto;

import java.util.List;

public class HttpServerDto extends HttpServerBaseDto {

    List<GameServerDto> gameServers;


    public HttpServerDto(){}
    public HttpServerDto(List<GameServerDto> gameServers) {
        this.gameServers = gameServers;
    }

    public List<GameServerDto> getGameServers() {
        return gameServers;
    }

    public void setGameServers(List<GameServerDto> gameServers) {
        this.gameServers = gameServers;
    }
}
