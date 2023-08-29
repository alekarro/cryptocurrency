package org.aro.cryptocurrency.scheduler;

import org.aro.cryptocurrency.model.TimerInfo;
import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import static org.aro.cryptocurrency.TestUtils.createTimerInfo;
import static org.junit.jupiter.api.Assertions.*;

public class SchedulerConfigTests {

    @Test
    public void buildJobDetail_success(){
        final JobDetail jobDetail = SchedulerConfig.buildJobDetail(Job.class, "{json}");
        assertEquals("{json}", jobDetail.getJobDataMap().get("Job"));
        assertEquals("Job", jobDetail.getKey().getName());
    }

    @Test
    public void buildJobDetail_successEmptyData(){
        final JobDetail jobDetail = SchedulerConfig.buildJobDetail(Job.class, "");
        assertEquals("", jobDetail.getJobDataMap().get("Job"));
        assertEquals("Job", jobDetail.getKey().getName());
    }

    @Test
    public void buildJobDetail_successNull(){
        final JobDetail jobDetail = SchedulerConfig.buildJobDetail(Job.class, null);
        assertNull(jobDetail.getJobDataMap().get("Job"));
        assertEquals("Job", jobDetail.getKey().getName());
    }

    @Test
    public void buildTrigger_success(){
        final Trigger trigger = SchedulerConfig.buildTrigger(Job.class, createTimerInfo(1,true,2,3));
        assertEquals("Job", trigger.getKey().getName());
    }

    @Test
    public void buildTrigger_null(){
        final Exception exception = assertThrows(NullPointerException.class, () -> SchedulerConfig.buildTrigger(Job.class, new TimerInfo()));
        assertNull(exception.getMessage());
    }

    @Test
    public void buildTrigger_negativeValues(){
        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
                SchedulerConfig.buildTrigger(Job.class, createTimerInfo(-1,true,-2,-3)));
        assertEquals("Repeat interval must be >= 0", exception.getMessage());
    }

}
