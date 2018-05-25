package recommender.service;

import java.io.FileNotFoundException;
import java.util.List;

import cc.kave.commons.model.naming.types.ITypeName;
import recommender.model.Recommendation;

public interface IRecommendationGenerator {
	
	public List<Recommendation> getRecommendations(ITypeName type) throws FileNotFoundException;
	
}
