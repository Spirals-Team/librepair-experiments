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
public class DuplicateRemoverTests {

	@Autowired
	DuplicateRemover duplicateRemover;

	public static Stream<Arguments> data() {
		return Stream.of(
	            of("hello hello I am going home hello home", "hello I am going home")
	    );
    }

	@ParameterizedTest
	@MethodSource("data")
	public void shouldWork(String input, String output) {
		assertEquals(duplicateRemover.apply(input), output);
	}
}
