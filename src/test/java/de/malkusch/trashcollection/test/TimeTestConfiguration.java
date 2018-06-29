package de.malkusch.trashcollection.test;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
class TimeTestConfiguration {

	@Bean
	@Primary
	Clock clock(TestClock clock) {
		return clock;
	}

	@Bean
	TestClock testClock() {
		return new TestClock();
	}

}
