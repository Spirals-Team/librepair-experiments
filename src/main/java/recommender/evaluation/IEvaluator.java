package recommender.evaluation;

import java.util.List;

import cc.kave.commons.model.events.completionevents.IProposal;
import recommender.model.Recommendation;

public interface IEvaluator {
	
	void evaluate(List<Recommendation> resultList, IProposal actualSelection);

	void summarizeResults();
}
