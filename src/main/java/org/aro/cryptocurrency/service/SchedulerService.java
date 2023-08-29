package org.aro.cryptocurrency.service;

import org.aro.cryptocurrency.model.TimerInfo;
import org.quartz.Job;

public interface SchedulerService {
    <T extends Job> void schedule(final Class<T> jobClass, final TimerInfo info, final String jsonData);
}
