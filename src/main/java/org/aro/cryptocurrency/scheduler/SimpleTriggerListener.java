package org.aro.cryptocurrency.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SimpleTriggerListener implements TriggerListener {
    private final AtomicInteger numberOfFirings = new AtomicInteger();

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info("Trigger fired - " + context.getJobDetail().getKey());
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("Trigger misfired - " + trigger.getJobKey());
        numberOfFirings.incrementAndGet();
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.info("Trigger completed - " + context.getJobDetail().getKey());
        numberOfFirings.incrementAndGet();
    }

    public AtomicInteger getNumberOfFirings() {
        return numberOfFirings;
    }
}
