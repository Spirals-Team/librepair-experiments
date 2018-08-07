package guru.bonacci.oogway.sannyas.service.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "filter.profanity.file.name=badwords-test.txt")
public class ProfanityFilterTests {

	@TestConfiguration
    static class TestContext {
  
        @Bean
        public ProfanityFilter filter() {
    		return new ProfanityFilter();
        }
    }

	@Autowired
	ProfanityFilter filter;

	@Test
	public void shouldGiveOneOfTheExpectedAnswers() {
		assertThat(true, is(not(filter.test("word1"))));
		assertThat(true, is(filter.test("aa")));
	}
}
