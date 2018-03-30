package com.lazermann.myio.master.configs;

import com.lazermann.myio.master.job.UpdateServerStatusJob;
import org.quartz.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class SchedulerConfig {

    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(UpdateServerStatusJob.class).build();
    }

    @Bean
    public Trigger sampleJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(2).repeatForever();

        return TriggerBuilder.newTrigger().forJob(sampleJobDetail())
                .withIdentity("sampleTrigger").withSchedule(scheduleBuilder).build();
    }
}
