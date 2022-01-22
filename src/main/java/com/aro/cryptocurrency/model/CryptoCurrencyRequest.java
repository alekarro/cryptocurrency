package com.aro.cryptocurrency.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "api.coinmarketcap.request.v1.cryptocurrency.listings.latest")
@Component
@Setter
@Getter
public class CryptoCurrencyRequest {
    private int start;
    private int limit;
    private String convert;
    private String sort;
}
