package guru.bonacci.oogway.sannyas.service.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.Properties;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.sannyas.service.SannyasTestApp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SannyasTestApp.class, webEnvironment = NONE)
public class LemmatizorTests {

	@Autowired
	Lemmatizor lemmatizor;

	public static Stream<Arguments> data() {
		return Stream.of(
				of( "How could you be seeing into my eyes like open doors?",
						"how could you be see into my eye like open door ?" ),
				of( "You led me down into my core where I've became so numb",
						"you lead I down into my core where I have become so numb" ),
				of( "Without a soul my spirit's sleeping somewhere cold",
						"without a soul my spirit 's sleep somewhere cold" ),
				of( "Until you find it there and led it back home", "until you find it there and lead it back home" ),
				of( "You woke me up inside", "you wake I up inside" ),
				of( "Called my name and saved me from the dark", "call my name and save I from the dark" ),
				of( "You have bidden my blood and it ran", "you have bid my blood and it run" ),
				of( "Before I would become undone", "before I would become undo" ),
				of( "You saved me from the nothing I've almost become",
						"you save I from the nothing I have almost become" ),
				of( "You were bringing me to life", "you be bring I to life" ),
				of( "Now that I knew what I'm without", "now that I know what I be without" ),
				of( "You can've just left me", "you can have just leave I" ),
				of( "You breathed into me and made me real", "you breathe into I and make I real" ),
				of( "Frozen inside without your touch", "frozen inside without you touch" ),
				of( "Without your love, darling", "without you love , darling" ),
				of( "Only you are the life among the dead", "only you be the life among the dead" ),
				of( "I've been living a lie, there's nothing inside", "I have be live a lie , there be nothing inside" ),
				of( "You were bringing me to life", "you be bring I to life" ));
	}

	@ParameterizedTest
	@MethodSource("data")
	public void shouldWork(String input, String output) {
		assertEquals(lemmatizor.apply(input), output);
	}

	@Configuration
	static class LemmatizorConfiguration {

		@Bean
		public StanfordCoreNLP lemmatizatorPipeline() {
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit, pos, lemma");
			return new StanfordCoreNLP(props);
		}
	}
}
