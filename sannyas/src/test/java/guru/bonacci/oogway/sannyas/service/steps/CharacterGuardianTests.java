package guru.bonacci.oogway.sannyas.service.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SannyasTestApp.class, webEnvironment = NONE)
public class CharacterGuardianTests {

	@Autowired
	CharacterGuardian characterGuardian;

	public static Stream<Arguments> data() {
		return Stream.of(
	            of("bla bla", "bla bla"),
	            of("!hi &^&^there &", "hi there "),
	            of("ab 123c.", "ab 123c"),
	            of(" 123 " , " 123 "),
	            of("" , "")
	    );
    }
	
	@ParameterizedTest
	@MethodSource("data")
	public void shouldWork(String input, String output) {
		assertEquals(characterGuardian.apply(input), output);
	}
}
