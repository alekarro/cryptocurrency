package org.aro.cryptocurrency.apiclient;

import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(FeignClientsConfiguration.class)
@Configuration
public class ApiClientConfiguration {
    private static final String API_KEY_HEADER = "X-CMC_PRO_API_KEY";

    @Bean
    public CryptoCurrencyServiceFeignClient cryptoCurrencyFeignClient(
            Encoder encoder,
            Decoder decoder,
            Contract apiFeignContract,
            RequestInterceptor apiKeyCryptoCurrencyRequestInterceptor,
            @Value("${api.coinmarketcap.uri}") String uri) {

        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(apiFeignContract)
                .requestInterceptor(apiKeyCryptoCurrencyRequestInterceptor)
                .target(CryptoCurrencyServiceFeignClient.class, uri);

    }

    @Bean
    public RequestInterceptor apiKeyCryptoCurrencyRequestInterceptor(@Value("${api.coinmarketcap.apiKey}") String apiKey) {
        return template -> template.header(API_KEY_HEADER, apiKey);
    }

    @Bean
    public Contract apiFeignContract() {
        return new feign.Contract.Default();
    }
}
