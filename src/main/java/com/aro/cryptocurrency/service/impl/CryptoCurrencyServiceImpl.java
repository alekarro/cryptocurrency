package com.aro.cryptocurrency.service.impl;

import com.aro.cryptocurrency.apiclient.CryptoCurrencyServiceFeignClient;
import com.aro.cryptocurrency.jobs.OutputCryptoCurrenciesJob;
import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import com.aro.cryptocurrency.model.CryptoCurrencyResponse;
import com.aro.cryptocurrency.model.TimerInfo;
import com.aro.cryptocurrency.service.CryptoCurrencyService;
import com.aro.cryptocurrency.service.SchedulerService;
import com.aro.cryptocurrency.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private SchedulerService schedulerService;

    private CryptoCurrencyServiceFeignClient cryptoCurrencyServiceFeignClient;

    private TimerInfo cryptoCurrencyTimerInfo;

    @Override
    @Scheduled(cron = "0 0 0 0 1")
    public void outputCryptoCurrencies(CryptoCurrencyRequest parameters) {
        final CryptoCurrencyResponse response = cryptoCurrencyServiceFeignClient.getCryptoCurrencies(parameters);
        if (response != null) {
            //LOG.info(response.getData().toString());
            System.out.print(response.getData().toString());
        }
    }

    @Override
    public void outputCryptoCurrenciesScheduled(CryptoCurrencyRequest parameters) {
        try {
            schedulerService.schedule(OutputCryptoCurrenciesJob.class, cryptoCurrencyTimerInfo, JsonUtils.toJson(parameters));
        } catch (JsonProcessingException e) {
            log.error("Error: parameters " + parameters + " cannot be converted to string", e);
        }
    }

}
