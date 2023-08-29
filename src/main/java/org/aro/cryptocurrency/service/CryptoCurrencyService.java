package org.aro.cryptocurrency.service;


import org.aro.cryptocurrency.model.CryptoCurrencyRequest;

public interface CryptoCurrencyService {
    void outputCryptoCurrencies(CryptoCurrencyRequest parameters);

    void outputCryptoCurrenciesScheduled(CryptoCurrencyRequest parameters);
}
