package org.aro.cryptocurrency.service.impl;

import org.aro.cryptocurrency.model.TimerInfo;
import org.aro.cryptocurrency.scheduler.SimpleTriggerListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.*;

import static org.aro.cryptocurrency.TestUtils.createTimerInfo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerServiceImplTests {

    @InjectMocks
    private SchedulerServiceImpl service;

    @Mock
    private Scheduler scheduler;

    @Mock
    private SimpleTriggerListener simpleTriggerListener;

    @Test
    public void schedule_successful() throws SchedulerException {
        final TimerInfo timerInfo = createTimerInfo(1,true,2,3);
        service.schedule(Job.class, timerInfo, "{jsonData}");
        verify(scheduler, times(1)).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }

    @Test
    public void schedule_nullJson() throws SchedulerException {
        final TimerInfo timerInfo = createTimerInfo(1,true,2,3);
        service.schedule(Job.class, timerInfo, null);
        verify(scheduler, times(1)).scheduleJob(any(JobDetail.class), any(Trigger.class));
    }

    @Test
    public void schedule_nullTimer() {
        final Exception exception = assertThrows(NullPointerException.class, () ->
                service.schedule(Job.class, null, null));
        assertNull(exception.getMessage());
    }

    @Test
    public void schedule_nullJob() {
        final Exception exception = assertThrows(NullPointerException.class, () ->
                service.schedule(null, createTimerInfo(1,true,2,3), "{json}"));
        assertNull(exception.getMessage());
    }
}
