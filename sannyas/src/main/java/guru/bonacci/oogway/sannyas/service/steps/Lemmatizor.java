package guru.bonacci.oogway.sannyas.service.steps;

import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/*
 * Lemmatisation (or lemmatization) in linguistics is the process of grouping together 
 * the inflected forms of a word so they can be analysed as a single item, identified 
 * by the word's lemma, or dictionary form.
 * 
 * In computational linguistics, lemmatisation is the algorithmic process of determining 
 * the lemma of a word based on its intended meaning. Unlike stemming, lemmatisation 
 * depends on correctly identifying the intended part of speech and meaning of a word 
 * in a sentence, as well as within the larger context surrounding that sentence, such as 
 * neighboring sentences or even an entire document.
 * 
 * 
 * This is a pipeline that takes in a string and returns various analyzed linguistic forms.
 * The String is tokenized via a tokenizer (such as PTBTokenizerAnnotator),
 * and then other sequence model style annotation can be used to add things like lemmas,
 * POS tags, and named entities. These are returned as a list of CoreLabels.
 * Other analysis components build and store parse trees, dependency graphs, etc.
 */
@Component
public class Lemmatizor implements Function<String, String> {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private StanfordCoreNLP pipeline;

	@Override
	public String apply(String documentText) {
		logger.debug("in: " + documentText);

		List<String> lemmas = new LinkedList<>();
		// Create an empty Annotation just with the given text
		Annotation document = new Annotation(documentText);
		// run all Annotators on this text
		this.pipeline.annotate(document);
		// Iterate over all of the sentences found
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// Iterate over all tokens in a sentence
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// Retrieve and add the lemma for each word into the list of
				// lemmas
				lemmas.add(token.get(LemmaAnnotation.class));
			}
		}
		String output = lemmas.stream().collect(joining(" "));

		logger.debug("out: " + output);
		return output;
	}
}
