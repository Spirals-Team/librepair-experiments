package guru.bonacci.oogway.doorway.cheaters;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;
import static guru.bonacci.oogway.utilities.CustomListUtils.random;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import guru.bonacci.intercapere.InterCapereService;

/**
 * Postponer - someone who postpones work (especially out of laziness or
 * habitual carelessness)
 */
@RefreshScope
@Component
public class Postponer {

	@Autowired
	private InterCapereService intercapere;
	
	@Value("${file.name.answers.to.win.time:}")
	private String fileName;

	private final Logger logger = getLogger(this.getClass());

	private List<String> answers;

	@PostConstruct
	public void setup() {
		try {
			answers = readToList(fileName);
		} catch (IOException e) {
			logger.error("Can't read file: " + fileName);
		} finally {
			if (isEmpty(answers))
				answers = singletonList("I'm speechless, are you sure?");
		}
	}
	
	public String saySomething() {
		return intercapere.take(
					random(answers).get());
	}
}
