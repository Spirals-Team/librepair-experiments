package guru.bonacci.oogway.sannyas.service.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SannyasTestApp.class, properties = {
        "spring.sleuth.enabled=false",
        "spring.zipkin.enabled=false"
}, webEnvironment = NONE)
public class KeyPhraserTests {

	@Autowired
	KeyPhraser keyPhraser;
    
	public static Stream<Arguments> data() {
		return Stream.of(
	            of("If I have seen further it is by standing on the shoulders of Giants.", "have seen standing shoulders of Giants"),
	            of("I can calculate the motion of heavenly bodies but not the madness of people.", "calculate motion of heavenly bodies madness of people"),
	            of("Tact is the knack of making a point without making an enemy.", "Tact knack making point making enemy"),
	            of("Nature is pleased with simplicity. And nature is no dummy" , "Nature pleased simplicity nature dummy"),
	            of("Hello, My Name Is Doris" , "Name Doris")
	    );
    }

	@ParameterizedTest
	@MethodSource("data")
	public void shouldWork(String input, String output) {
		assertEquals(keyPhraser.apply(input), output);
	}
	
	@Test
	public void shouldComplain() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			keyPhraser.apply(null);
		});	
	}

}
