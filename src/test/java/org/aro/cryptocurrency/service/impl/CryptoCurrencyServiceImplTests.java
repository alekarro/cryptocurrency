package org.aro.cryptocurrency.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aro.cryptocurrency.apiclient.CryptoCurrencyServiceFeignClient;
import org.aro.cryptocurrency.jobs.OutputCryptoCurrenciesJob;
import org.aro.cryptocurrency.model.CryptoCurrencyRequest;
import org.aro.cryptocurrency.model.CryptoCurrencyResponse;
import org.aro.cryptocurrency.model.TimerInfo;
import org.aro.cryptocurrency.service.SchedulerService;
import org.aro.cryptocurrency.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.aro.cryptocurrency.TestUtils.createParameters;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CryptoCurrencyServiceImplTests {

    @InjectMocks
    private CryptoCurrencyServiceImpl service;

    @Mock
    private CryptoCurrencyServiceFeignClient cryptoCurrencyServiceFeignClient;

    @Mock
    private SchedulerService schedulerService;

    @Mock
    private TimerInfo cryptoCurrencyTimerInfo;

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