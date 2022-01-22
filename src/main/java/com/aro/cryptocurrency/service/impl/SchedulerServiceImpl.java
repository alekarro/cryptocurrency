package com.aro.cryptocurrency.service.impl;

import com.aro.cryptocurrency.model.TimerInfo;
import com.aro.cryptocurrency.scheduler.SimpleTriggerListener;
import com.aro.cryptocurrency.service.SchedulerService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static com.aro.cryptocurrency.scheduler.SchedulerConfig.buildJobDetail;
import static com.aro.cryptocurrency.scheduler.SchedulerConfig.buildTrigger;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SimpleTriggerListener simpleTriggerListener;

    public <T extends Job> void schedule(final Class<T> jobClass, final TimerInfo timerInfo,
            final String jsonData) {

        final JobDetail jobDetail = buildJobDetail(jobClass, jsonData);
        final Trigger trigger = buildTrigger(jobClass, timerInfo);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
            scheduler.getListenerManager().addTriggerListener(simpleTriggerListener);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
