package com.aro.cryptocurrency.jobs;

import com.aro.cryptocurrency.service.CryptoCurrencyService;
import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.aro.cryptocurrency.JsonUtils.toObject;

@Component
public class OutputCryptoCurrenciesJob implements Job {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Override
    public void execute(JobExecutionContext context) {
        final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        final CryptoCurrencyRequest parameters =
        (CryptoCurrencyRequest)toObject((String)jobDataMap.get(this.getClass().getSimpleName()), CryptoCurrencyRequest.class);
        cryptoCurrencyService.outputCryptoCurrencies(parameters);
    }
}
