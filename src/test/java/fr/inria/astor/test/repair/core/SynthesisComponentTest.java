package fr.inria.astor.test.repair.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.inria.astor.core.entities.Ingredient;
import fr.inria.astor.core.entities.ProgramVariant;
import fr.inria.astor.core.entities.SuspiciousModificationPoint;
import fr.inria.astor.core.ingredientbased.IngredientBasedApproach;
import fr.inria.astor.core.manipulation.synthesis.DynamicCollectedValues;
import fr.inria.astor.core.manipulation.synthesis.DynamothCollector;
import fr.inria.astor.core.manipulation.synthesis.IngredientSynthesizer;
import fr.inria.astor.core.manipulation.synthesis.SynthesisBasedTransformationStrategy;
import fr.inria.astor.core.manipulation.synthesis.ValueCollector;
import fr.inria.astor.core.solutionsearch.spaces.ingredients.transformations.IngredientTransformationStrategy;
import fr.inria.astor.test.repair.DummySynthesizer4TestImpl;
import fr.inria.astor.test.repair.evaluation.regression.MathCommandsTests;
import fr.inria.lille.repair.common.Candidates;
import fr.inria.main.CommandSummary;
import fr.inria.main.evolution.AstorMain;
import fr.inria.main.evolution.ExtensionPoints;

/**
 * 
 * @author Matias Martinez
 *
 */
public class SynthesisComponentTest {
	protected static Logger log = Logger.getLogger(Thread.currentThread().getName());

	@Test
	public void testValueCollection1() throws Exception {
		AstorMain main1 = new AstorMain();
		CommandSummary cs = MathCommandsTests.getMath70Command();

		cs.command.put("-flthreshold", "0.1");
		cs.command.put("-stopfirst", "true");
		cs.command.put("-loglevel", "DEBUG");
		cs.command.put("-saveall", "true");
		cs.command.put("-maxgen", "0");
		cs.append("-parameters", ("logtestexecution:true:disablelog:true"));

		log.info(Arrays.toString(cs.flat()));
		main1.execute(cs.flat());

		assertEquals(1, main1.getEngine().getVariants().size());
		ProgramVariant variant = main1.getEngine().getVariants().get(0);

		ValueCollector sc = new ValueCollector();

		log.info("***First mp to test: ");
		SuspiciousModificationPoint mp0 = (SuspiciousModificationPoint) variant.getModificationPoints().get(0);
		assertEquals("org.apache.commons.math.analysis.solvers.BisectionSolver", mp0.getCtClass().getQualifiedName());
		assertEquals(72, mp0.getSuspicious().getLineNumber());

		valuesOfModificationPoint(main1, sc, mp0);

		log.info("***Second mp to test: ");

		SuspiciousModificationPoint mp8 = (SuspiciousModificationPoint) variant.getModificationPoints().get(8);
		assertEquals("org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils",
				mp8.getCtClass().getQualifiedName());
		assertEquals(223, mp8.getSuspicious().getLineNumber());
		valuesOfModificationPoint(main1, sc, mp8);

	}

	private DynamicCollectedValues valuesOfModificationPoint(AstorMain main1, ValueCollector sc,
			SuspiciousModificationPoint mp0) {

		log.info("-mp-> " + mp0.getCodeElement());

		DynamothCollector dynamothCodeGenesis = sc.createCollector(main1.getEngine().getProjectFacade(), mp0);

		Map<String, List<Candidates>> values = dynamothCodeGenesis.getValues();
		assertTrue(!values.isEmpty());
		int nrtest = 0;
		for (String key : values.keySet()) {
			log.info("test " + nrtest++ + " :" + key);
			List<Candidates> candidates1 = values.get(key);
			log.info("nr candidates 1: " + candidates1.size());
			int i = 0;
			for (Candidates candidates2 : candidates1) {
				log.info("--Nr of candidates of " + (i++) + ": " + candidates2.size());
				int j = 0;
				for (fr.inria.lille.repair.expression.Expression expression : candidates2) {

					log.info("--*-->" + i + " " + (j++) + " " + expression.asPatch() + " " + expression.getValue());

				}
			}

		}
		return new DynamicCollectedValues(values);
	}

	@Test
	public void testExtensionPoint() throws Exception {
		AstorMain main1 = new AstorMain();
		CommandSummary cs = MathCommandsTests.getMath70Command();

		cs.command.put("-flthreshold", "0.1");
		cs.command.put("-stopfirst", "true");
		cs.command.put("-loglevel", "DEBUG");
		cs.command.put("-saveall", "true");
		cs.command.put("-maxgen", "0");
		cs.append("-parameters",
				"logtestexecution:true:disablelog:true:"//
						+ ExtensionPoints.INGREDIENT_TRANSFORM_STRATEGY.identifier + File.pathSeparator
						+ SynthesisBasedTransformationStrategy.class.getCanonicalName() + File.pathSeparator + //
						ExtensionPoints.CODE_SYNTHESIS.identifier + File.pathSeparator
						+ DummySynthesizer4TestImpl.class.getCanonicalName()

		);

		log.info(Arrays.toString(cs.flat()));
		main1.execute(cs.flat());

		assertEquals(1, main1.getEngine().getVariants().size());
		ProgramVariant variant = main1.getEngine().getVariants().get(0);

		IngredientTransformationStrategy transfst = ((IngredientBasedApproach) main1.getEngine())
				.getIngredientTransformationStrategy();

		assertNotNull(transfst);
		assertTrue(transfst instanceof SynthesisBasedTransformationStrategy);
		SynthesisBasedTransformationStrategy synthesizerStrategy = (SynthesisBasedTransformationStrategy) transfst;
		IngredientSynthesizer synthesizer = synthesizerStrategy.getSynthesizer();
		assertNotNull(synthesizer);
		assertTrue(synthesizer instanceof DummySynthesizer4TestImpl);

		SuspiciousModificationPoint mp0 = (SuspiciousModificationPoint) variant.getModificationPoints().get(0);

		List<Ingredient> ingredients = synthesizerStrategy.transform(mp0, new Ingredient(mp0.getCodeElement()));

	}

}
