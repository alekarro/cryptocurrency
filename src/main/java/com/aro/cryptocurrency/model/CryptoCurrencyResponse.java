package com.aro.cryptocurrency.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CryptoCurrencyResponse {
    private List<CryptoCurrencyData> data;
}
