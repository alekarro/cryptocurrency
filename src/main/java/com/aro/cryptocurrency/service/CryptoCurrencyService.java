package com.aro.cryptocurrency.service;

import com.aro.cryptocurrency.model.CryptoCurrencyRequest;

public interface CryptoCurrencyService {
    void outputCryptoCurrencies(CryptoCurrencyRequest parameters);
    void outputCryptoCurrenciesScheduled(CryptoCurrencyRequest parameters);
}
