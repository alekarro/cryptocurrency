package com.aro.cryptocurrency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CryptoCurrencyQuote {
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal price;

    @JsonProperty("volume_24h")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal volume24h;

    @JsonProperty("market_cap")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal marketCap;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\t\t\t").append("Price: ").append(price).append(":\n");
        sb.append("\t\t\t").append("Volume24h: ").append(volume24h).append(":\n");
        sb.append("\t\t\t").append("MarketCap: ").append(marketCap).append(":\n");
        return sb.toString();
    }
}
