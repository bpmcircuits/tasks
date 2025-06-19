package com.crud.tasks;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class TasksApplicationTests {
	private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

	@DynamicPropertySource
	static void loadEnv(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> dotenv.get("EXT_DB_URL"));
	}

	@Test
	void contextLoads() {
	}

}
