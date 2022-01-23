## Cryptocurrencies Service with Spring Boot, Quartz scheduler, Feign client

Spring Boot application that outputs to console currency quotes from [CoinMarketCap API](https://coinmarketcap.com/api/) resource.
It uses Spring Boot, FeignClient and Quartz scheduler.

Application creates FeignClient for endpoint 
**https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start={start}&limit={limit}&convert={convert}&sort={sort}**
and launches a scheduler that makes requests to this endpoint and displays cryptocurrency quotes on the console every 2 minutes.

Query parameters, as well as scheduler parameters, can be changed in the application.yml file or passed as program arguments.


### Getting Started

##### Running the example

This project uses the Gradle Wrapper to automatically bootstrap the build chain.
 The only requirement for running this example is to have a JDK(minimum 1.8) installed and on the path. 
 
In the root of this project, run:

_./gradlew bootJar_

_java -jar build/libs/cryptocurrency-1.0.jar_

The first command compiles the project and packages it into a fat jar.
The second runs that jar and prints out the quotes sorted by market_cap 5 times each 2 minutes.



##### Parameters
You can the application with different parameters

###### Time parameters:

* **--api.coinmarketcap.timer.totalFireCount**     - total number of firings 
* **--api.coinmarketcap.timer.repeatIntervalMs**   - intervals between firings
* **--api.coinmarketcap.timer.initialOffsetMs**    - interval before start
* **--api.coinmarketcap.timer.runForever**         - indicates to work and output quotes forever, regardless of total number of firings

###### API parameters:
  
* **--api.coinmarketcap.request.v1.cryptocurrency.listings.latest.start**  - API parameter. Optionally offset the start (1-based index) of the paginated list of items to return. 
* **--api.coinmarketcap.request.v1.cryptocurrency.listings.latest.limit**  - API parameter. Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size.
* **--api.coinmarketcap.request.v1.cryptocurrency.listings.latest.convert** - API parameter. Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
* **--api.coinmarketcap.request.v1.cryptocurrency.listings.latest.sort**    - API parameter. What field to sort the list of cryptocurrencies by. Valid values are: market_cap, price,volume_24h.  

The API documentation is here  - [CoinMarketCap API documentation](https://coinmarketcap.com/api/documentation/v1/#operation/getV1CryptocurrencyListingsLatest)

##### Example

**java -jar build/libs/cryptocurrency-1.0.jar 
--api.coinmarketcap.timer.totalFireCount=1 --api.coinmarketcap.timer.repeatIntervalMs=0 --api.coinmarketcap.request.v1.cryptocurrency.listings.latest.sort=price**

outputs quotes sorted by price only 1 time.   

##### Run in Docker

1. create docker image from Dockerfile

_docker build -t cryptocurrency ._

2. start docker container

_docker run cryptocurrency_

3. start docker container with parameters:

_docker run -e api.coinmarketcap.timer.totalFireCount=1 -e api.coinmarketcap.timer.repeatIntervalMs=1 -e api.coinmarketcap.request.v1.cryptocurrency.listings.latest.sort=price cryptocurrency_





For all questions feel free to contact me at alero@op.pl.


