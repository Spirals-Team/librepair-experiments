package guru.bonacci.oogway.sannyas.service.filters;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;
import guru.bonacci.oogway.sannyas.service.filters.ProfanityFilter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SannyasTestApp.class, webEnvironment=NONE, properties={
	"filter.profanity.file.name=badwords-test.txt"
})
public class ProfanityFilterTests {

	@Autowired
	ProfanityFilter filter;

	@Test
	public void shouldGiveOneOfTheExpectedAnswers() {
		assertThat(true, is(not(filter.test("word1"))));
		assertThat(true, is(filter.test("aa")));
	}
}
