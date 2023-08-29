package org.aro.cryptocurrency.apiclient;

import feign.Contract;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ApiClientConfigurationTests {

    @InjectMocks
    private ApiClientConfiguration apiClientConfiguration;

    @Mock
    private Encoder encoder;

    @Mock
    private Decoder decoder;

    @Mock
    private Contract contract;

    @Mock
    private RequestInterceptor apiKeyCryptoCurrencyRequestInterceptor;

    @Test
    public void cryptoCurrencyFeignClient_successful() {
        final CryptoCurrencyServiceFeignClient client = apiClientConfiguration.cryptoCurrencyFeignClient(
                encoder,
                decoder,
                contract,
                apiKeyCryptoCurrencyRequestInterceptor,
                "https://pro-api.coinmarketcap.com/v1/cryptocurrency"
        );

        final String clientString = client.toString();
        assertTrue(clientString.contains("https://pro-api.coinmarketcap.com/v1/cryptocurrency"));
        assertTrue(clientString.contains("CryptoCurrencyServiceFeignClient"));
    }

    @Test
    public void cryptoCurrencyFeignClient_exceptions() {
        Exception exception = assertThrows(NullPointerException.class, () ->
                apiClientConfiguration.cryptoCurrencyFeignClient(
                        null,
                        decoder,
                        contract,
                        apiKeyCryptoCurrencyRequestInterceptor,
                        "https://pro-api.coinmarketcap.com/v1/cryptocurrency"
                ));
        assertEquals("encoder", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () ->
                apiClientConfiguration.cryptoCurrencyFeignClient(
                        encoder,
                        decoder,
                        null,
                        apiKeyCryptoCurrencyRequestInterceptor,
                        "https://pro-api.coinmarketcap.com/v1/cryptocurrency"
                ));

        exception = assertThrows(NullPointerException.class, () ->
                apiClientConfiguration.cryptoCurrencyFeignClient(
                        encoder,
                        decoder,
                        contract,
                        apiKeyCryptoCurrencyRequestInterceptor,
                        ""
                ));
        assertEquals("name", exception.getMessage());
    }
}
