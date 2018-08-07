package guru.bonacci.spectre.sentiment.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import guru.bonacci.spectre.spectreshared.persistence.Spec;
import guru.bonacci.spectre.spectreshared.persistence.SpecRepository;
import guru.bonacci.spectre.spectreutilities.enrichment.SpectreService;

@Service
public class SentimentService implements SpectreService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private SpecRepository repo;

	@Autowired
	private StanfordCoreNLP pipeline;
	
	public void enrich(String id) {
		try {
			// Too lazy for refined error handling today...
			Spec spec = repo.findById(id).get();
			repo.addData("sentiment", findSentimentDesc(spec.message), spec);
		} catch(Exception e) {
			logger.error("Oops", e);
		}

	}

	String findSentimentDesc(String line) {
		return toCss(findSentiment(line));
	}

	Integer findSentiment(String line) {
		logger.debug("in: " + line);

		int mainSentiment = 0;
		if (line != null && line.length() > 0) {
			int longest = 0;
			Annotation annotation = pipeline.process(line);
			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment = sentiment;
					longest = partText.length();
				}

			}
		}

		logger.debug("out: " + mainSentiment);
		return mainSentiment;
	}
	
	private String toCss(int sentiment) {
        switch (sentiment) {
        case 0:
            return "very negative";
        case 1:
            return "negative";
        case 2:
            return "neutral";
        case 3:
            return "positive";
        case 4:
            return "very positive";
        default:
            return "default";
        }
     }
}
