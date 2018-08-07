package guru.bonacci.oogway.sannyas.service.processing;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static org.apache.commons.lang.StringUtils.reverse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.sannyas.service.gr.GRSeeker;
import guru.bonacci.oogway.sannyas.service.steps.DuplicateRemover;

@ExtendWith(SpringExtension.class)
public class ForePlayerTests {

	@TestConfiguration
    static class TestContext {
  
        @Bean
        public ForePlayer player() {
    		return new ForePlayer();
        }
    }

	private static final String INPUT = "some string without meaning";

	@Autowired
	ForePlayer player;

	@MockBean
	GRSeeker sannyasin;
	
	@MockBean
	DuplicateRemover duplicateRemover;

	@Test
    public void shouldGoForItSimple() {
		when(sannyasin.preprocessingSteps()).thenReturn(asList(identity()));
		when(duplicateRemover.apply(INPUT)).thenReturn(INPUT);

		String preprocessedInput = player.play(sannyasin, INPUT);
		assertThat(preprocessedInput, is(equalTo(INPUT)));
	}

	@Test
    public void shouldGoForItComplicated() {
		String inputReverse = "gninaem tuohtiw gnirts emos";
		String somethingElse = "something else";
		
		Function<String,String> f = str -> reverse(str);
		when(sannyasin.preprocessingSteps()).thenReturn(asList(f, f, f));
		when(duplicateRemover.apply(inputReverse)).thenReturn(somethingElse);

		String preprocessedInput = player.play(sannyasin, INPUT);
		System.out.println(preprocessedInput);
		assertThat(preprocessedInput, is(equalTo(somethingElse)));
	}
}
