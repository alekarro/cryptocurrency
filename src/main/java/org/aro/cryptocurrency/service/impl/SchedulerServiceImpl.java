package org.aro.cryptocurrency.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aro.cryptocurrency.model.TimerInfo;
import org.aro.cryptocurrency.scheduler.SimpleTriggerListener;
import org.aro.cryptocurrency.service.SchedulerService;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.aro.cryptocurrency.scheduler.SchedulerConfig.buildJobDetail;
import static org.aro.cryptocurrency.scheduler.SchedulerConfig.buildTrigger;


@Service
@Slf4j
@AllArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private Scheduler scheduler;

    private SimpleTriggerListener simpleTriggerListener;

    public <T extends Job> void schedule(final Class<T> jobClass, final TimerInfo timerInfo,
                                         final String jsonData) {

        final JobDetail jobDetail = buildJobDetail(jobClass, jsonData);
        final Trigger trigger = buildTrigger(jobClass, timerInfo);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
            scheduler.getListenerManager().addTriggerListener(simpleTriggerListener);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
