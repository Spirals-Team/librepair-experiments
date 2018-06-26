package fr.inria.astor.core.manipulation.synthesis.dynamoth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.inria.lille.repair.common.Candidates;
import fr.inria.lille.repair.expression.Expression;

/**
 * Created by Thomas Durieux on 06/03/15.
 * 
 * @author Matias Martinez
 *
 */
public class DynamothSynthesizerWOracle extends DynamothSynthesizer {

	public DynamothSynthesizerWOracle(DynamothSynthesisContext data) {
		super();
		this.values = data.getValues();
		this.nopolContext = data.getNopolContext();
		// Explicitly NULL
		this.oracle = null;
	}

	@Override
	public Candidates combineValues() {
		final Candidates result = new Candidates();
		List<String> collectedTests = new ArrayList<>(values.keySet());

		Collections.sort(collectedTests, new Comparator<String>() {
			@Override
			public int compare(String s, String t1) {
				if (values.get(t1).isEmpty()) {
					return -1;
				}
				if (values.get(s).isEmpty()) {
					return 1;
				}
				return values.get(t1).get(0).size() - values.get(s).get(0).size();
			}
		});
		for (int i = 0; i < collectedTests.size(); i++) {
			final String key = collectedTests.get(i);
			List<Candidates> listValue = values.get(key);
			for (Candidates expressions : listValue) {
				for (Expression expression : expressions) {
					expression.getValue().setConstant(isConstant(expression));
				}
			}
		}
		long currentTime = System.currentTimeMillis();
		Candidates lastCollectedValues = null;
		for (int k = 0; k < collectedTests
				.size() /* && currentTime - startTime <= remainingTime */; k++) {
			final String key = collectedTests.get(k);
			List<Candidates> listValue = values.get(key);
			currentTime = System.currentTimeMillis();
			for (int i = 0; i < listValue
					.size() /* && currentTime - startTime <= remainingTime */; i++) {
				Candidates eexps = listValue.get(i);
				if (eexps == null) {
					continue;
				}
				if (lastCollectedValues != null
						&& lastCollectedValues.intersection(eexps, false).size() == eexps.size()) {
					continue;
				}
				lastCollectedValues = eexps;
				if (nopolContext.isSortExpressions()) {
					Collections.sort(eexps, Collections.reverseOrder());
				}
				// final Object angelicValue;
				// if (i < oracle.get(key).length) {
				// angelicValue = oracle.get(key)[i];
				// } else {
				// angelicValue = oracle.get(key)[oracle.get(key).length - 1];
				// }
				currentTime = System.currentTimeMillis();
				// check if one of the collected value can be a patch
				for (int j = 0; j < eexps
						.size() /*
								 * && currentTime - startTime <= remainingTime
								 */; j++) {
					Expression expression = eexps.get(j);
					if (expression == null || expression.getValue() == null) {
						continue;
					}
					if (// angelicValue.equals(expression.getValue().getRealValue())
						// &&
					checkExpression(key, i, expression)) {
						result.add(expression);
						if (nopolContext.isOnlyOneSynthesisResult()) {
							return result;
						}
					}
				}

				DataCombinerModified combiner = new DataCombinerModified();
				final int iterationNumber = i;

				combiner.addCombineListener(new DataCombinerModified.CombineListener() {
					@Override
					public boolean check(Expression expression) {
						// if
						// (!angelicValue.equals(expression.getValue().getRealValue()))
						// {
						// return false;
						// }
						if (checkExpression(key, iterationNumber, expression)) {
							result.add(expression);// Not modify the result...
							return true;
						}
						return false;
					}
				});
				currentTime = System.currentTimeMillis();
				// combine eexps
				long maxCombinerTime = TimeUnit.SECONDS.toMillis(10);

				// combiner.combine(eexps, /* angelicValue */ null,
				// maxCombinerTime, nopolContext);
				Candidates result1 = combiner.combine(eexps, /* angelicValue */ null, maxCombinerTime, nopolContext);
			}
		}

		return result;
	}

	@Override
	protected boolean checkExpression(String testName, int iterationNumber, Expression expression) {
		nbExpressionEvaluated++;
		if (checkedExpression.contains(expression.toString())) {
			return false;
		}
		checkedExpression.add(expression.toString());
		for (String test : values.keySet()) {
			List<Candidates> listCandidates = values.get(test);
			for (int i = 0; i < listCandidates.size(); i++) {
				Candidates valueOtherTest = listCandidates.get(i);
				if (test.equals(testName) && iterationNumber == i) {
					continue;
				}
			}
		}
		return true;
	}
}
