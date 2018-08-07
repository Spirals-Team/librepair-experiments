package guru.bonacci.oogway.sannyas.service.processing;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.sannyas.service.events.SannyasGateway;
import guru.bonacci.oogway.sannyas.service.gr.GRSeeker;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@ExtendWith(SpringExtension.class)
public class PitchforkManagerTests {

	@TestConfiguration
    static class TestContext {
  
        @Bean
        public PitchforkManager manager() {
    		return new PitchforkManager();
        }
    }

	@Autowired
	PitchforkManager manager;

	@MockBean
	SannyasinPicker sannyasinPicker;

	@MockBean
	GRSeeker sannyasin;

	@MockBean
	ForePlayer forePlayer;

	@MockBean
	CleaningAgent cleaningAgent;

	@MockBean
	SannyasGateway gateway;
	
	@Test
	public void shouldJustRunThroughAllTheseMockCallsInThisMeaninglessTest() {
		String input = "yet another beautiful day today";
		String preprocessedInput = "another beautiful day";
		List<GemCarrier> found = asList(new GemCarrier("that"), new GemCarrier("is true"), new GemCarrier("beautiful stranger"));
		List<GemCarrier> clutterless = asList(new GemCarrier("that"), new GemCarrier("true"), new GemCarrier("stranger"));
		
		when(sannyasinPicker.pickOne()).thenReturn(sannyasin);
		when(forePlayer.play(sannyasin, input)).thenReturn(preprocessedInput);
		when(sannyasin.seek(preprocessedInput)).thenReturn(found);
		when(cleaningAgent.noMoreClutter(sannyasin, found)).thenReturn(clutterless);
		
		manager.delegate(input);
		//TODO verify something
	}
}
