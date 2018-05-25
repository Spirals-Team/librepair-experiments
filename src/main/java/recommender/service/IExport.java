package recommender.service;

import java.io.FileNotFoundException;
import java.util.List;

import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import recommender.model.Recommendation;

public interface IExport {

	public void printRecommendationsToCsv(List<Recommendation> recommendations) throws FileNotFoundException;
	
	public void printEvent(ICompletionEvent completionEvent) throws FileNotFoundException;
	
}
