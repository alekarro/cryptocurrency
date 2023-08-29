package org.aro.cryptocurrency.scheduler;

import org.aro.cryptocurrency.model.TimerInfo;
import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class SchedulerConfig {

    public static <T extends Job> JobDetail buildJobDetail(final Class<T> jobClass, final String jsonData) {
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(), jsonData);

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static <T extends Job> Trigger buildTrigger(final Class<T> jobClass, final TimerInfo info) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().
                withIntervalInMilliseconds(info.getRepeatIntervalMs());

        if (info.isRunForever()) {
            builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(info.getTotalFireCount() - 1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + info.getInitialOffsetMs()))
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "api.coinmarketcap.timer")
    public TimerInfo cryptoCurrencyTimerInfo() {
        return new TimerInfo();
    }

}
