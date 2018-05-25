package recommender.service;

import java.util.Comparator;

import recommender.model.Recommendation;

public class RecommendationComparator implements Comparator<Recommendation> {
	    @Override
	    public int compare(Recommendation o1, Recommendation o2) {
	        return Double.compare(o2.getPercentage(),o1.getPercentage());
	    }
	}