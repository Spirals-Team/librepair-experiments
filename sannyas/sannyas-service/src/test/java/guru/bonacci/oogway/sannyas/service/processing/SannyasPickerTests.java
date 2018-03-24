package guru.bonacci.oogway.sannyas.service.processing;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;
import guru.bonacci.oogway.sannyas.service.general.Sannyasin;
import guru.bonacci.oogway.sannyas.service.processing.SannyasinPicker;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SannyasTestApp.class, webEnvironment=NONE)
public class SannyasPickerTests {

	@Autowired
	SannyasinPicker picker;

	@Test
    public void shouldPickDifferentOnes() {
		Set<Sannyasin> result = new HashSet<>();	
		for (int i=0; i<10; i++)
			result.add(picker.pickOne());
		
		assertThat(result.size(), greaterThan(1));
	}
}
