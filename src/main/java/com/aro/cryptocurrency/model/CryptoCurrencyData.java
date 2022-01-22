package com.aro.cryptocurrency.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CryptoCurrencyData {
    private String name;
    private String symbol;
    @JsonProperty("quote")
    private Map<String,CryptoCurrencyQuote> quotes;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nCryptoCurrency:\n");
        sb.append("\tName: ").append(name).append("\n");
        sb.append("\tSymbol: ").append(symbol).append("\n");
        sb.append("\tQuotes:\n");
        if (quotes != null) {
            quotes.entrySet().forEach(entry -> {
                sb.append("\t\tCurrency: ").append(entry.getKey()).append(":\n");
                sb.append(entry.getValue());
                }
            );
        }
        return sb.toString();
    }
}
