package com.aro.cryptocurrency.service.impl;

import com.aro.cryptocurrency.JsonUtils;
import com.aro.cryptocurrency.apiclient.CryptoCurrencyServiceFeignClient;
import com.aro.cryptocurrency.jobs.OutputCryptoCurrenciesJob;
import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import com.aro.cryptocurrency.model.CryptoCurrencyResponse;
import com.aro.cryptocurrency.model.TimerInfo;
import com.aro.cryptocurrency.service.CryptoCurrencyService;
import com.aro.cryptocurrency.service.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
    private static final Logger LOG = LoggerFactory.getLogger(CryptoCurrencyServiceImpl.class);

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private CryptoCurrencyServiceFeignClient cryptoCurrencyServiceFeignClient;

    @Autowired
    private TimerInfo cryptoCurrencyTimerInfo;

    @Override
    public void outputCryptoCurrencies(CryptoCurrencyRequest parameters) {
        final CryptoCurrencyResponse response = cryptoCurrencyServiceFeignClient.getCryptoCurrencies(parameters);
        if(response != null) {
            //LOG.info(response.getData().toString());
            System.out.print(response.getData().toString());
        }
    }

    @Override
    public void outputCryptoCurrenciesScheduled(CryptoCurrencyRequest parameters) {
        schedulerService.schedule(OutputCryptoCurrenciesJob.class, cryptoCurrencyTimerInfo, JsonUtils.toJson(parameters));
    }

}
