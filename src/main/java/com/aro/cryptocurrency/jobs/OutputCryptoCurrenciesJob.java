package com.aro.cryptocurrency.jobs;

import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import com.aro.cryptocurrency.service.CryptoCurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import static com.aro.cryptocurrency.utils.JsonUtils.toObject;

@Component
@Slf4j
@AllArgsConstructor
public class OutputCryptoCurrenciesJob implements Job {

    private CryptoCurrencyService cryptoCurrencyService;

    @Override
    public void execute(JobExecutionContext context) {
        final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        try {
            final CryptoCurrencyRequest parameters =
                    toObject((String) jobDataMap.get(this.getClass().getSimpleName()), CryptoCurrencyRequest.class);
            cryptoCurrencyService.outputCryptoCurrencies(parameters);
        } catch (JsonProcessingException e) {
            log.error("Error: parameters cannot be loaded from string", e);
        }
    }
}
