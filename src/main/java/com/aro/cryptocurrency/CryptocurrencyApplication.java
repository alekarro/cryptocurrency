package com.aro.cryptocurrency;

import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import com.aro.cryptocurrency.model.TimerInfo;
import com.aro.cryptocurrency.scheduler.SimpleTriggerListener;
import com.aro.cryptocurrency.service.CryptoCurrencyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CryptocurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptocurrencyApplication.class, args);
    }

    @Bean
    public CommandLineRunner startScheduledCryptoCurrencyRequests(
            final CryptoCurrencyService cryptoCurrencyService,
            final CryptoCurrencyRequest parameters,
            final TimerInfo cryptoCurrencyTimerInfo,
            final SimpleTriggerListener simpleTriggerListener,
            final ApplicationContext context
    ) {
        return args -> {
            cryptoCurrencyService.outputCryptoCurrenciesScheduled(parameters);
            if (!cryptoCurrencyTimerInfo.isRunForever()) {
                final long timeToSleep = ((cryptoCurrencyTimerInfo.getTotalFireCount() - 1) * cryptoCurrencyTimerInfo.getRepeatIntervalMs() / 1000) +
                        (cryptoCurrencyTimerInfo.getInitialOffsetMs() / 1000) + 1;
                TimeUnit.SECONDS.sleep(timeToSleep);

                while (simpleTriggerListener.getNumberOfFirings().get() < cryptoCurrencyTimerInfo.getTotalFireCount()) {
                    TimeUnit.SECONDS.sleep(5);
                }

                SpringApplication.exit(context, () -> 0);
            }
        };
    }
}
