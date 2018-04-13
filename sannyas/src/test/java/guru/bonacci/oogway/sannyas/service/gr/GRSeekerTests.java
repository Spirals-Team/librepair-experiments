package guru.bonacci.oogway.sannyas.service.gr;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;
import guru.bonacci.oogway.sannyas.service.filters.LengthFilter;
import guru.bonacci.oogway.sannyas.service.gr.GRScraper;
import guru.bonacci.oogway.sannyas.service.gr.GRSeeker;
import guru.bonacci.oogway.sannyas.service.steps.CharacterGuardian;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SannyasTestApp.class, webEnvironment=NONE)
public class GRSeekerTests {

	@Autowired
	GRSeeker seeker;
	
	@MockBean
	CharacterGuardian characterGuardian;
	
	@MockBean
	LengthFilter lengthFilter;

	@MockBean
	GRScraper finder;

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
		
		when(finder.find("a", "b")).thenReturn(out);
		assertThat(seeker.seek("a b"), is(equalTo(out)));
	}

}
