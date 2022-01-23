package com.aro.cryptocurrency.service.impl;

import com.aro.cryptocurrency.apiclient.CryptoCurrencyServiceFeignClient;
import com.aro.cryptocurrency.jobs.OutputCryptoCurrenciesJob;
import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import com.aro.cryptocurrency.model.CryptoCurrencyResponse;
import com.aro.cryptocurrency.model.TimerInfo;
import com.aro.cryptocurrency.service.SchedulerService;
import com.aro.cryptocurrency.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.aro.cryptocurrency.TestUtils.createParameters;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CryptoCurrencyServiceImplTests {

    private CryptoCurrencyServiceImpl service;

    @Mock
    private CryptoCurrencyServiceFeignClient cryptoCurrencyServiceFeignClient;

    @Mock
    private SchedulerService schedulerService;

    @Mock
    private TimerInfo cryptoCurrencyTimerInfo;


    @BeforeEach
    public void before() {
        service = new CryptoCurrencyServiceImpl(schedulerService, cryptoCurrencyServiceFeignClient, cryptoCurrencyTimerInfo);
    }

    @Test
    public void outputCryptoCurrencies_successful() {
        final CryptoCurrencyRequest parameters = new CryptoCurrencyRequest();
        final CryptoCurrencyResponse cryptoCurrencyResponse = new CryptoCurrencyResponse();
        cryptoCurrencyResponse.setData(new ArrayList<>());
        when(cryptoCurrencyServiceFeignClient.getCryptoCurrencies(parameters)).thenReturn(cryptoCurrencyResponse);
        service.outputCryptoCurrencies(parameters);
        verify(cryptoCurrencyServiceFeignClient, times(1)).getCryptoCurrencies(parameters);
    }

    @Test
    public void outputCryptoCurrencies_null() {
        service.outputCryptoCurrencies(null);
        verify(cryptoCurrencyServiceFeignClient, times(1)).getCryptoCurrencies(null);
    }

    @Test
    public void outputCryptoCurrenciesScheduled_successful() throws JsonProcessingException {
        final CryptoCurrencyRequest parameters = createParameters(1,5,"USD","price");
        service.outputCryptoCurrenciesScheduled(parameters);
        verify(schedulerService, times(1)).schedule(OutputCryptoCurrenciesJob.class, cryptoCurrencyTimerInfo, JsonUtils.toJson(parameters));
    }

}