package com.aro.cryptocurrency.service.impl;

import com.aro.cryptocurrency.JsonUtils;
import com.aro.cryptocurrency.apiclient.CryptoCurrencyServiceFeignClient;
import com.aro.cryptocurrency.jobs.OutputCryptoCurrenciesJob;
import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import com.aro.cryptocurrency.model.CryptoCurrencyResponse;
import com.aro.cryptocurrency.model.TimerInfo;
import com.aro.cryptocurrency.service.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static com.aro.cryptocurrency.TestUtils.createParameters;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        service = new CryptoCurrencyServiceImpl();
        ReflectionTestUtils.setField(service, "cryptoCurrencyServiceFeignClient", cryptoCurrencyServiceFeignClient);
        ReflectionTestUtils.setField(service, "schedulerService", schedulerService);
        ReflectionTestUtils.setField(service, "cryptoCurrencyTimerInfo", cryptoCurrencyTimerInfo);
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
    public void outputCryptoCurrencies_exception() {
        final Exception exception = assertThrows(NullPointerException.class, () -> {
            service.outputCryptoCurrencies(null);
        });
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void outputCryptoCurrenciesScheduled_successful() {
        final CryptoCurrencyRequest parameters = createParameters(1,5,"USD","price");
        service.outputCryptoCurrenciesScheduled(parameters);
        verify(schedulerService, times(1)).schedule(OutputCryptoCurrenciesJob.class, cryptoCurrencyTimerInfo, JsonUtils.toJson(parameters));
    }

}