package io.github.sushnag22.weatherinfo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class WeatherInfoApplicationTests {

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("OPENWEATHERMAP_API_KEY", () -> "TEST_VALUE_1");
		registry.add("OPENWEATHERMAP_URL", () -> "TEST_VALUE_2");
		registry.add("RAPIDAPI_API_KEY", () -> "TEST_VALUE_3");
		registry.add("RAPIDAPI_HOST", () -> "TEST_VALUE_4");
		registry.add("RAPIDAPI_URL", () -> "TEST_VALUE_5");
	}

	@Test
	void contextLoads() {
	}

}
