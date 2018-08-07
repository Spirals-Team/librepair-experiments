package guru.bonacci.oogway.sannyas.service.processing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;
import guru.bonacci.oogway.sannyas.service.general.Sannyasin;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=SannyasTestApp.class, webEnvironment=NONE)
public class SannyasPickerTests {

	@Autowired
	SannyasinPicker picker;

	@Disabled // with only one seeker-impl this has become a useless test
	@Test
    public void shouldPickDifferentOnes() {
		Set<Sannyasin> result = new HashSet<>();	
		for (int i=0; i<10; i++)
			result.add(picker.pickOne());
		
		assertThat(result.size(), greaterThan(1));
	}
}
