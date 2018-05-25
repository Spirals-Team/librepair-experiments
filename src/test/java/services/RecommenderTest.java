package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.Lists;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.events.completionevents.IProposal;
import recommender.evaluation.Evaluator;
import recommender.model.Recommendation;
import recommender.service.IRecommendationGenerator;
import recommender.service.impl.Export;
import recommender.service.impl.ReadingArchiveEvents;
import recommender.service.impl.RecommendationGenerator;
import recommender.visitor.CompletionExpressionVisitor;
public class RecommenderTest {
	
	
	@Before
	public void setup() throws UnsupportedEncodingException, FileNotFoundException, IOException {

	}
	
	@Test
	public void testGetRecommendation() throws FileNotFoundException {

		Evaluator evaluator = new Evaluator();
		
		for (String user : findAllUsers("src/test/java/Recommender")) {
			ReadingArchiveEvents ra = new ReadingArchiveEvents(new File(user));
			while (ra.hasNext()) {
				IIDEEvent event = ra.getNext(IIDEEvent.class);
				if (event instanceof CompletionEvent) {
					ICompletionEvent ce = (CompletionEvent) event;

					HashMap<List<Recommendation>, IProposal> evaluationSet = new HashMap<List<Recommendation>, IProposal>();

					ce.getContext().getSST().accept(new CompletionExpressionVisitor("src/test/java/Recommender/TestCollections", evaluationSet, ce),null);

					Iterator<Entry<List<Recommendation>, IProposal>> it = evaluationSet.entrySet().iterator();
					while (it.hasNext()) {
						
						Map.Entry<List<Recommendation>, IProposal> pair = (Map.Entry<List<Recommendation>, IProposal>) it.next();
						evaluator.evaluate(pair.getKey(), pair.getValue());
					}
				}
			}
			ra.close();
		}		
	}

	public static List<String> findAllUsers(String path) {
		List<String> zips = Lists.newLinkedList();
		for (File f : FileUtils.listFiles(new File(path), new String[] { "zip" }, true)) {
			zips.add(f.getAbsolutePath());
			System.out.println(f.getAbsolutePath());
		}
		return zips;
	}
	



}
