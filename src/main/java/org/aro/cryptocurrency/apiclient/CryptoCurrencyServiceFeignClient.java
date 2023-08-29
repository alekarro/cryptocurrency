package org.aro.cryptocurrency.apiclient;

import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;
import org.aro.cryptocurrency.model.CryptoCurrencyRequest;
import org.aro.cryptocurrency.model.CryptoCurrencyResponse;

@Headers({
        "Accept: application/json"
})
public interface CryptoCurrencyServiceFeignClient {

    @RequestLine("GET /listings/latest")
    CryptoCurrencyResponse getCryptoCurrencies(@QueryMap CryptoCurrencyRequest parameters);

}
