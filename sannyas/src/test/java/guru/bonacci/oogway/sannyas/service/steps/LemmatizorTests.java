package guru.bonacci.oogway.sannyas.service.steps;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Properties;

import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import guru.bonacci.oogway.sannyas.service.steps.Lemmatizor;

public class LemmatizorTests extends AbstractStepTest<Lemmatizor> {

	public LemmatizorTests(String in, String out) {
		super(in, out, Lemmatizor.class);
	}

	@Parameters
	public static Collection<Object[]> data() {
		return asList(new Object[][] {
				{ "How could you be seeing into my eyes like open doors?",
						"how could you be see into my eye like open door ?" },
				{ "You led me down into my core where I've became so numb",
						"you lead I down into my core where I have become so numb" },
				{ "Without a soul my spirit's sleeping somewhere cold",
						"without a soul my spirit 's sleep somewhere cold" },
				{ "Until you find it there and led it back home", "until you find it there and lead it back home" },
				{ "You woke me up inside", "you wake I up inside" },
				{ "Called my name and saved me from the dark", "call my name and save I from the dark" },
				{ "You have bidden my blood and it ran", "you have bid my blood and it run" },
				{ "Before I would become undone", "before I would become undo" },
				{ "You saved me from the nothing I've almost become",
						"you save I from the nothing I have almost become" },
				{ "You were bringing me to life", "you be bring I to life" },
				{ "Now that I knew what I'm without", "now that I know what I be without" },
				{ "You can've just left me", "you can have just leave I" },
				{ "You breathed into me and made me real", "you breathe into I and make I real" },
				{ "Frozen inside without your touch", "frozen inside without you touch" },
				{ "Without your love, darling", "without you love , darling" },
				{ "Only you are the life among the dead", "only you be the life among the dead" },
				{ "I've been living a lie, there's nothing inside", "I have be live a lie , there be nothing inside" },
				{ "You were bringing me to life", "you be bring I to life" } });
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
