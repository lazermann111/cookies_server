package com.lazermann.myio.master.job;

import com.lazermann.myio.master.dao.UserDao;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class UpdateServerStatusJob extends QuartzJobBean
{

    @Autowired
    private UserDao service;



    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("******************* execute UpdateServerStatusJob ********************");


    }
}