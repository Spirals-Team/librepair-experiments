/**
 * This file is part of choco-solver, http://choco-solver.org/
 *
 * Copyright (c) 2017, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.solver.search;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.selectors.variables.ImpactBased;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ProblemMaker;
import org.testng.annotations.Test;

import static java.lang.System.out;
import static org.chocosolver.solver.search.strategy.Search.domOverWDegSearch;
import static org.testng.Assert.assertEquals;

/**
 * @author Jean-Guillaume Fages
 * @since 22/04/15
 * Created by IntelliJ IDEA.
 */
public class ImpactTest {

	@Test(groups="10s", timeOut=60000)
	public void testCostas() {
		Model s1 = costasArray(7, false);
		Model s2 = costasArray(7, true);

		while (s1.getSolver().solve()) ;
		out.println(s1.getSolver().getSolutionCount());

		while (s2.getSolver().solve()) ;

		out.println(s2.getSolver().getSolutionCount());
		assertEquals(s1.getSolver().getSolutionCount(), s2.getSolver().getSolutionCount());
	}

	private Model costasArray(int n, boolean impact){
		Model model = ProblemMaker.makeCostasArrays(n);
		IntVar[] vectors = (IntVar[]) model.getHook("vectors");

		Solver r = model.getSolver();
		r.limitTime(20000);
		if(impact){
			r.setSearch(new ImpactBased(vectors,2,3,10,0,true));
		}else{
			r.setSearch(domOverWDegSearch(vectors));
		}
		return model;
	}
}
