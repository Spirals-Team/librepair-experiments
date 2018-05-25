package recommender.evaluation;

import java.util.ArrayList;
import java.util.List;

import cc.kave.commons.model.events.completionevents.IProposal;
import recommender.model.Recommendation;

public class Evaluator implements IEvaluator {

	private static List<Double> apList;
	private static List<Integer> rankList;
	private static int truePositive;
	private static int falsePositive;
	private static int falseNegative;

	public Evaluator() {
		apList = new ArrayList<>();
		rankList = new ArrayList<>();
		truePositive = 0;
		falsePositive = 0;
		falseNegative = 0;
	}

	public void evaluate(List<Recommendation> resultList, IProposal actualSelection) {
		String name = NameUtility.getName(actualSelection);
		int rank = getRank(name, resultList);
		rankList.add(rank);
		apList.add(getAveragePrecision(rank));
	}

	public void summarizeResults() {
		System.out.println("Number of processed queries in total: " + apList.size());
		System.out.println("Number of unsuccessful recommendations where a hit-list exists, but no entry was found: "
				+ falsePositive);
		System.out.println("Number of unsuccessful recommendations without a hit-list: " + falseNegative);
		System.out.println("Number of successful recommendations: " + truePositive);
		System.out.println("Precision of recommender: " + getPrecision());
		System.out.println("Recall of recommender: " + getRecall());
		System.out.println("On average " + getAverageRank() + " entries must be observed to find a match.");
	}

	private int getRank(String name, List<Recommendation> resultList) {
		if (resultList.isEmpty() || name == null) {
			// FN: we do not have a hit list for this event
			falseNegative += 1;
			return -1;
		}
		int rank = 0;
		for (Recommendation r : resultList) {
			if (r.getName().equals(name)) {
				rank = resultList.indexOf(r);
				rank += 1;
			}
		}
		if (rank > 0) {
			// TP: rank > 0, hit-list existing
			truePositive += 1;
		} else {
			// FP: rank = 0, hit-list existing, but no entry found
			falsePositive += 1;
		}
		return rank;
	}

	/**
	 * Calculates precision with TruePositive / (TruePositive + FalsePositive) where
	 * TruePositive denotes the amout of queries found in the hit-list and
	 * FalsePositive the amount of missed hits.
	 * 
	 * @return double precision
	 */
	private double getPrecision() {
		if ((truePositive + falsePositive) != 0) {
			return (double) truePositive / (truePositive + falsePositive);
		}
		return 0.0;
	}

	/**
	 * Calculates recall with TruePositive / (FalseNegative + TruePositive) where
	 * TruePositive denotes the amount of queries found in the hit-list and
	 * FalseNegative the ones where no hit-list exists.
	 * 
	 * @return double recall
	 */
	private double getRecall() {
		if ((falseNegative + truePositive) != 0) {
			return (double) truePositive / (falseNegative + truePositive);
		}
		return 0.0;
	}

	/**
	 * Calculates the averagePrecision of an entry at rank k. It corresponds to the
	 * average distance from the top-result to the actual rank; the bigger AP is,
	 * the better.
	 * 
	 * @param rankOfK
	 * @return double averagePrecision
	 */
	private double getAveragePrecision(int rankOfK) {
		return rankOfK > 0 ? (1.0 / rankOfK) : 0.0;
	}

	/**
	 * Calculates the meanAveragePrecision of a list of averagePrecisions. It
	 * corresponds to the average of the averagePrecisions, i.e. the average
	 * distance of all ranks to the top-result.
	 * 
	 * @param rankOfK
	 * @return double meanAveragePrecision
	 */
	@SuppressWarnings("unused")
	private double getMeanAveragePrecision() {
		double apSum = 0.0;
		int count = 0;
		for (double ap : apList) {
			if(ap > 0.0) {
				count += 1;
			}
			apSum += ap;
		}
		return count != 0 ? (apSum / count) : null;
	}
	
	private double getAverageRank() {
		double rankSum = 0.0;
		int count = 0;
		for (int rank : rankList) {
			if(rank > 0) {
				count += 1;
				rankSum += rank;
			}
		}
		if(count != 0 && rankSum!=0.0) {
			return (rankSum / count);
		}else {
			return 0.0;
		}
	}
}
