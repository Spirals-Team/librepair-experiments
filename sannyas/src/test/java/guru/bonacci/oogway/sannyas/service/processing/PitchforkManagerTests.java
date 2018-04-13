package guru.bonacci.oogway.sannyas.service.processing;

import static java.util.Arrays.asList;
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
import guru.bonacci.oogway.sannyas.service.gr.GRSeeker;
import guru.bonacci.oogway.sannyas.service.processing.CleaningAgent;
import guru.bonacci.oogway.sannyas.service.processing.ForePlayer;
import guru.bonacci.oogway.sannyas.service.processing.PitchforkManager;
import guru.bonacci.oogway.sannyas.service.processing.SannyasinPicker;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SannyasTestApp.class, webEnvironment=NONE)
public class PitchforkManagerTests {

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
