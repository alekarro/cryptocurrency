package com.aro.cryptocurrency.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SimpleTriggerListener implements TriggerListener  {
    private AtomicInteger numberOfFirings = new AtomicInteger();

    private static final Logger LOG = LoggerFactory.getLogger(SimpleTriggerListener.class);

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        LOG.info("Trigger fired - " + context.getJobDetail().getKey());
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        LOG.info("Trigger misfired - " + trigger.getJobKey());
        numberOfFirings.incrementAndGet();
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        LOG.info("Trigger completed - " + context.getJobDetail().getKey());
        numberOfFirings.incrementAndGet();
    }

    public AtomicInteger getNumberOfFirings() {
        return numberOfFirings;
    }
}
