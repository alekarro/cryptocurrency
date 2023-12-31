package org.aro.cryptocurrency.jobs;

import org.aro.cryptocurrency.model.CryptoCurrencyRequest;
import org.aro.cryptocurrency.service.CryptoCurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OutputCryptoCurrencyJobTests {

    @InjectMocks
    private OutputCryptoCurrenciesJob job;

    @Mock
    private CryptoCurrencyService cryptoCurrencyService;

    @Mock
    private JobExecutionContext context;

    @Mock
    private JobDetail jobDetail;

    @Mock
    private JobDataMap jobDataMap;

    @Test
    public void execute_successful() {
        when(context.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(jobDataMap);
        when(jobDataMap.get("OutputCryptoCurrenciesJob")).thenReturn("{\"start\":1,\"limit\":15,\"convert\":\"USD\",\"sort\":\"price\"}");
        job.execute(context);
        verify(cryptoCurrencyService, times(1)).outputCryptoCurrencies(any(CryptoCurrencyRequest.class));
    }

    @Test
    public void execute_exception() {
        when(context.getJobDetail()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(jobDataMap);
        when(jobDataMap.get("OutputCryptoCurrenciesJob")).thenReturn(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> job.execute(context));
        assertEquals("argument \"content\" is null", exception.getMessage());
        verify(cryptoCurrencyService, times(0)).outputCryptoCurrencies(any(CryptoCurrencyRequest.class));
    }
}
