package recommender.visitor;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.events.completionevents.IProposal;
import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.impl.visitor.AbstractTraversingNodeVisitor;
import recommender.model.Recommendation;
import recommender.service.impl.RecommendationGenerator;

public class CompletionExpressionVisitor extends AbstractTraversingNodeVisitor<Void, Void> {

	private String methodCollections;
	
	private HashMap<List<Recommendation>,IProposal> evalMap;
	
	private ICompletionEvent completionEvent;

	public CompletionExpressionVisitor(String methodCollections,HashMap<List<Recommendation>,IProposal> evalMap,ICompletionEvent completionEvent) {
		this.methodCollections = methodCollections;
		this.evalMap = evalMap;
		this.completionEvent = completionEvent;
	}

	@Override
	public Void visit(ICompletionExpression expr, Void context) {

		if (expr.getTypeReference() != null) {

			RecommendationGenerator recommender = new RecommendationGenerator(methodCollections);
			try {
				List<Recommendation> resultList = recommender.getRecommendations(expr.getTypeReference());
				evalMap.put(resultList, completionEvent.getLastSelectedProposal());
			} catch (FileNotFoundException e) {

			}
		}
		return null;
	}

}