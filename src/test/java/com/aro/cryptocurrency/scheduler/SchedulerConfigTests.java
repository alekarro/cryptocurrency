package com.aro.cryptocurrency.scheduler;

import com.aro.cryptocurrency.model.TimerInfo;
import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import static com.aro.cryptocurrency.TestUtils.createTimerInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals(null, jobDetail.getJobDataMap().get("Job"));
        assertEquals("Job", jobDetail.getKey().getName());
    }

    @Test
    public void buildTrigger_success(){
        final Trigger trigger = SchedulerConfig.buildTrigger(Job.class, createTimerInfo(1,true,2,3));
        assertEquals("Job", trigger.getKey().getName());
    }

    @Test
    public void buildTrigger_null(){
        final Exception exception = assertThrows(NullPointerException.class, () -> {
            final Trigger trigger = SchedulerConfig.buildTrigger(Job.class, null);
        });
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void buildTrigger_negativeValues(){
        final Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SchedulerConfig.buildTrigger(Job.class, createTimerInfo(-1,true,-2,-3));
        });
        assertEquals("Repeat interval must be >= 0", exception.getMessage());
    }

}
