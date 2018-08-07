package guru.bonacci.oogway.sannyas.service.gr;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.sannyas.service.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.service.steps.CharacterGuardian;
import guru.bonacci.oogway.sannyas.service.steps.KeyPhraser;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@ExtendWith(SpringExtension.class)
public class GRSeekerTests {

	@TestConfiguration
	@Profile("!integration-test")
    static class TestContext {
  
        @Bean
        public GRSeeker grseeker() {
    		return new GRSeeker();
        }
    }
	
	@Autowired
	GRSeeker seeker;
	
	@MockBean
	GRScraper scraper;

	@MockBean
	CharacterGuardian characterGuardian;
	
	@MockBean
	KeyPhraser keyPhraser;

	@MockBean
	LengthFilter lengthFilter;
	
	@Test
	public void shouldPreProcess() {
		String in = "some input";
		String out = "some output";
		
		when(characterGuardian.apply(in)).thenReturn(out);
		assertThat(seeker.preprocessingSteps().get(0).apply(in), is(equalTo(out)));
	}
	
	@Test
	public void shouldPostFilter() {
		String in = "some input";
		boolean out = true;
		
		when(lengthFilter.test(in)).thenReturn(out);
		assertThat(seeker.postfilteringStep().get(0).test(in), is(equalTo(out)));
	}

	@Test
	public void shouldSeek() {
		List<GemCarrier> out = asList(new GemCarrier("a"), new GemCarrier("b"));
		
		when(scraper.find("a", "b")).thenReturn(out);
		assertThat(seeker.seek("a b"), is(equalTo(out)));
	}
}
