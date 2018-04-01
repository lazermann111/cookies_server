package com.lazermann.myio.master.dao;

import com.lazermann.myio.master.dto.GameServerDto;
import com.lazermann.myio.master.dto.HttpServerDto;
import com.lazermann.myio.master.helpers.DozerHelper;
import com.lazermann.myio.master.model.GameServer;
import com.lazermann.myio.master.model.GameType;
import com.lazermann.myio.master.model.HttpServer;
import com.lazermann.myio.master.model.Region;
import org.dozer.DozerBeanMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ServerDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    DozerBeanMapper dozerMapper;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void fillDb() throws Exception {

        HttpServer localhost = new HttpServer
                ("localhost:8000", Region.LOCALHOST, 0, GameType.SOLO, new ArrayList<>());


        GameServer a = new GameServer( true, 10,50);
        GameServer b = new GameServer( false, 10,50);
        GameServer c = new GameServer( true, 1,50);

        localhost.getGameServers().add(a);
        localhost.getGameServers().add(b);
        localhost.getGameServers().add(c);
        localhost.setTotalPlayers(150);

        getSession().save(localhost);

    }

    @SuppressWarnings("unchecked")
    public List<HttpServerDto> getAllServers() {
        List<HttpServer> list = getSession().createQuery("from HttpServer").list();
        return DozerHelper.map(dozerMapper, list, HttpServerDto.class);
    }

    @SuppressWarnings("unchecked")
    public HttpServerDto getServerToConnect(Region region) {
        List<HttpServer> server =
                getSession().createQuery("from HttpServer server where server.region = :r  ORDER BY server.totalPlayers desc")
                 .setParameter("r", region).list();
        if(server.isEmpty()) return null;

        for (HttpServer s : server)
        {
            if(s.getTotalPlayers() < s.getMaxPlayers())
                return   dozerMapper.map(s, HttpServerDto.class);
        }

         return null;
    }


    @SuppressWarnings("unchecked")
    public List<GameServerDto> getServersInRegion(Region region) {
       List<GameServer>  servers =
                getSession().createQuery("from HttpServer server where server.region = :r")
                        .setParameter("r", region).list();
        return DozerHelper.map(dozerMapper, servers, GameServerDto.class);
    }

    public HttpServer saveOrUpdate(HttpServerDto dto) throws Exception {

        HttpServer dbServer = getServerByUrl(dto.getURL());
        if(dbServer == null)
        {
            HttpServer res = dozerMapper.map(dto, HttpServer.class);

            int maxPlayers = 0, totalPlayers =0;
            for (GameServer s :res.getGameServers())
            {
                if(!s.isActive()) continue;
                maxPlayers +=s.getMaxPlayersNumber();
                totalPlayers +=s.getPlayersNumber();
            }

            res.setTotalPlayers(totalPlayers);
            res.setMaxPlayers(maxPlayers);

            getSession().save(res);
            return res;
        }
        else
        {
            dto.setId(dbServer.getId());


            List serv = new ArrayList(dbServer.getGameServers()) ;
            mapGameServer(dto.getGameServers(), serv);
            dozerMapper.map(dto,  dbServer);

            dbServer.setGameServers(serv);

            int maxPlayers = 0, totalPlayers =0;
            for (GameServer s : dbServer.getGameServers())
            {
                if(!s.isActive()) continue;
                maxPlayers +=s.getMaxPlayersNumber();
                totalPlayers +=s.getPlayersNumber();
            }

            dbServer.setTotalPlayers(totalPlayers);
            dbServer.setMaxPlayers(maxPlayers);


            getSession().update(dbServer);
            return dbServer;
        }

    }
    //todo remove ugly hack
    private List <GameServer> mapGameServer(List<GameServerDto> source , List <GameServer> dest)
    {
        for (GameServerDto s : source)
        {
            for(GameServer d :dest)
            {
                if(s.getWorldId() == d.getWorldId())
                {
                    d.setMaxPlayersNumber(s.getMaxPlayersNumber());
                    d.setMaxPlayersNumber(s.getMaxPlayersNumber());
                }
            }
        }

        return dest;
    }

    public void update(GameServer server) throws Exception {
        getSession().update(server);
    }

    public void update(HttpServerDto server) throws Exception {
        //todo
    }

    public void update(HttpServer server) throws Exception {
        getSession().update(server);
    }


    public HttpServer getServerByUrl(String URL) {
        return (HttpServer) getSession().createQuery(
                "from HttpServer server where server.URL = :url")
                .setParameter("url", URL)
                .uniqueResult();
    }

    public GameServer getGameServerById(long id) {
        return   getSession().get(GameServer.class, id);
    }

    public HttpServer getHttpServerById(long id) {
        return   getSession().get(HttpServer.class, id);
    }

}

