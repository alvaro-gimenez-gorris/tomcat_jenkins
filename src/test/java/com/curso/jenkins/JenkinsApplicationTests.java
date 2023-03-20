package com.curso.jenkins;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JenkinsApplicationTests {

	@Test
	void contextLoads() {

		assertTrue(true);
	}

	@Test
	void contextLoadsTwo() {

		assertEquals(1, 1);
	}
	@Test
	void failedTest() {

		assertEquals(1, 0);
	}
}
