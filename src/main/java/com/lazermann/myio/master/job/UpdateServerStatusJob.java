package com.lazermann.myio.master.job;

import com.lazermann.myio.master.dao.ServerDao;
import com.lazermann.myio.master.model.HttpServer;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class UpdateServerStatusJob extends QuartzJobBean
{

    private static final int ONE_MIN_MS = 60000;

    @Autowired
    private ServerDao serverDao;



    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("******************* execute UpdateServerStatusJob ********************");


        List<HttpServer> allServers = serverDao.getAllServersEntities();

        for (HttpServer server : allServers)
        {
            //didnt receive heartbeat for last minute
            if(server.getLastHeartbeat() < (System.currentTimeMillis() - ONE_MIN_MS))
            {
                server.setActive(false);
                try {
                    serverDao.update(server);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }
}