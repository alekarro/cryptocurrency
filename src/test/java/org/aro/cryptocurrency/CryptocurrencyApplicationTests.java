package org.aro.cryptocurrency;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
@PropertySource("classpath:test.properties")
class CryptocurrencyApplicationTests {
	@Test
	public void runApp() {
	}
}
