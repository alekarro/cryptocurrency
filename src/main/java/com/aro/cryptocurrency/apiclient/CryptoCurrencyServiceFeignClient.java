package com.aro.cryptocurrency.apiclient;

import com.aro.cryptocurrency.model.CryptoCurrencyRequest;
import com.aro.cryptocurrency.model.CryptoCurrencyResponse;
import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;

@Headers({
    "Accept: application/json"
})
public interface CryptoCurrencyServiceFeignClient {

    @RequestLine("GET /listings/latest")
    CryptoCurrencyResponse getCryptoCurrencies(@QueryMap CryptoCurrencyRequest parameters);

}
