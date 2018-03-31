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


        GameServer a = new GameServer(localhost, true, 10,50);
        GameServer b = new GameServer(localhost, false, 10,50);
        GameServer c = new GameServer(localhost, true, 1,50);

        localhost.getGameServers().add(a);
        localhost.getGameServers().add(b);
        localhost.getGameServers().add(c);

        getSession().save(localhost);

    }

    @SuppressWarnings("unchecked")
    public List<GameServerDto> getAllServers() {
        List<GameServer> list = getSession().createQuery("from GameServer").list();
        return DozerHelper.map(dozerMapper, list, GameServerDto.class);
    }

    @SuppressWarnings("unchecked")
    public GameServerDto getServerToConnect(Region region) {
        List<GameServer> server =
                getSession().createQuery("from GameServer server where server.region = :r and server.active is true ORDER BY server.playersNumber")
                 .setParameter("r", region).list();
        if(server.isEmpty()) return null;
        return dozerMapper.map(server.get(0), GameServerDto.class);
    }


    @SuppressWarnings("unchecked")
    public List<GameServerDto> getServersInRegion(Region region) {
       List<GameServer>  servers =
                getSession().createQuery("from GameServer server where server.region = :r")
                        .setParameter("r", region).list();
        return DozerHelper.map(dozerMapper, servers, GameServerDto.class);
    }

    public HttpServer saveOrUpdate(HttpServerDto dto) throws Exception {

        HttpServer dbServer = getServerByUrl(dto.getURL());
        if(dbServer == null)
        {
            HttpServer res = dozerMapper.map(dto, HttpServer.class);


            getSession().save(res);
            return res;
        }
        else
        {
            dto.setId(dbServer.getId());
            dozerMapper.map(dto,  dbServer);
            getSession().update(dbServer);
            return dbServer;
        }

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

