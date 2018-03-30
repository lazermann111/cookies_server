package com.lazermann.myio.master.dao;

import com.lazermann.myio.master.dto.GameServerDto;
import com.lazermann.myio.master.helpers.DozerHelper;
import com.lazermann.myio.master.model.GameServer;
import com.lazermann.myio.master.model.Region;
import org.dozer.DozerBeanMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GameServerDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    DozerBeanMapper dozerMapper;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void fillDb() throws Exception {
        GameServer a = new GameServer(Region.ASIA, 5, "asia1.com");
        a.setActive(false);
        getSession().save(a);


        GameServer a2 = new GameServer(Region.ASIA, 15, "asia2.com");
        getSession().save(a2);

        GameServer a3 = new GameServer(Region.ASIA, 25, "asia2.com");
        getSession().save(a3);

        GameServer eu = new GameServer(Region.EUROPE, 25, "EU1.com");
        getSession().save(eu);


    }

    @SuppressWarnings("unchecked")
    public List<GameServerDto> getAllServers() {
        List<GameServer> list = getSession().createQuery("from GameServer").list();
        return DozerHelper.map(dozerMapper, list, GameServerDto.class);
    }

    @SuppressWarnings("unchecked")
    public GameServerDto getServerToConnect(Region region) {
        GameServer server = (GameServer)
                getSession().createQuery("from GameServer server where server.region = :r and server.active ORDER BY server.playersNumber")
                 .setParameter("r", region).uniqueResult();
        return dozerMapper.map(server, GameServerDto.class);
    }


    public void save(GameServer server) throws Exception {
        getSession().save(server);
    }

    public GameServer getServerByUrl(String URL) {
        return (GameServer) getSession().createQuery(
                "from GameServer sever where sever.url = :url")
                .setParameter("url", URL)
                .uniqueResult();
    }

    public GameServer getServerById(long id) {
        return   getSession().get(GameServer.class, id);
    }

}

