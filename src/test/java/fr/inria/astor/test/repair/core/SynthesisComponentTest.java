package fr.inria.astor.test.repair.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
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
import fr.inria.astor.core.manipulation.synthesis.DynamothIngredientSynthesizer;
import fr.inria.astor.core.manipulation.synthesis.DynamothSynthesizer;
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
import spoon.reflect.declaration.CtElement;

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

	@Test
	public void testSynthesis1_boolean() throws Exception {

		AstorMain main1 = new AstorMain();
		CommandSummary cs = MathCommandsTests.getMath70Command();

		cs.command.put("-flthreshold", "0.0");
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

		SuspiciousModificationPoint mp8 = (SuspiciousModificationPoint) variant.getModificationPoints().get(0);

		DynamothCollector dynamothCodeGenesis = sc.createCollector(main1.getEngine().getProjectFacade(), mp8);
		DynamothSynthesizer synthesis = new DynamothSynthesizer(dynamothCodeGenesis.getValues(),
				dynamothCodeGenesis.getNopolContext(), dynamothCodeGenesis.getOracle());

		Candidates candidates = synthesis.combineValues();
		printValuesCollected(dynamothCodeGenesis);

		// TODO: BinaryExpressionImpl modified at line 91
		System.out.println("Candidates: " + candidates.size() + " " + candidates);

		assertTrue(candidates.size() > 0);
		for (int i = 0; i < candidates.size(); i++) {
			System.out.println("i " + i + ": " + candidates.get(i));
		}
		assertTrue(candidates.stream().filter(e -> e.toString().equals("!this.resultComputed")).findAny().isPresent());

	}

	@Test
	public void testSynthesis2_Int() throws Exception {

		AstorMain main1 = new AstorMain();
		CommandSummary cs = MathCommandsTests.getMath70Command();

		cs.command.put("-flthreshold", "0.0");
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

		SuspiciousModificationPoint mp8 = (SuspiciousModificationPoint) variant.getModificationPoints().get(0);

		String[] tests = sc.getCoverTest(mp8);
		Map<String, Object[]> oracle = new HashMap<>();

		for (String testCase : tests) {
			oracle.put(testCase, new Integer[] { 0 });
		}
		DynamothCollector dynamothCodeGenesis = sc.createCollector(main1.getEngine().getProjectFacade(), mp8, oracle,
				tests);
		DynamothSynthesizer synthesis = new DynamothSynthesizer(dynamothCodeGenesis.getValues(),
				dynamothCodeGenesis.getNopolContext(), dynamothCodeGenesis.getOracle());

		Candidates candidates = synthesis.combineValues();
		printValuesCollected(dynamothCodeGenesis);

		// TODO: BinaryExpressionImpl modified at line 91
		System.out.println("Candidates: " + candidates.size() + " " + candidates);

		assertTrue(candidates.size() > 0);
		for (int i = 0; i < candidates.size(); i++) {
			System.out.println("i " + i + ": " + candidates.get(i));
		}
		assertTrue(candidates.stream().filter(e -> e.toString().equals("this.iterationCount")).findAny().isPresent());
		assertTrue(candidates.stream().filter(e -> e.toString().equals("0")).findAny().isPresent());

	}

	@Test
	public void testSynthesis2_double() throws Exception {

		AstorMain main1 = new AstorMain();
		CommandSummary cs = MathCommandsTests.getMath70Command();

		cs.command.put("-flthreshold", "0.0");
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

		SuspiciousModificationPoint mp8 = (SuspiciousModificationPoint) variant.getModificationPoints().get(0);

		String[] tests = sc.getCoverTest(mp8);
		Map<String, Object[]> oracle = new HashMap<>();

		for (String testCase : tests) {
			oracle.put(testCase, new Double[] { 0.0 });
		}
		DynamothCollector dynamothCodeGenesis = sc.createCollector(main1.getEngine().getProjectFacade(), mp8, oracle,
				tests);
		DynamothSynthesizer synthesis = new DynamothSynthesizer(dynamothCodeGenesis.getValues(),
				dynamothCodeGenesis.getNopolContext(), dynamothCodeGenesis.getOracle());

		Candidates candidates = synthesis.combineValues();
		printValuesCollected(dynamothCodeGenesis);

		// TODO: BinaryExpressionImpl modified at line 91
		System.out.println("Candidates: " + candidates.size() + " " + candidates);

		assertTrue(candidates.size() > 0);
		for (int i = 0; i < candidates.size(); i++) {
			System.out.println("i " + i + ": " + candidates.get(i));
		}
		assertTrue(candidates.stream().filter(e -> e.toString().equals("this.result")).findAny().isPresent());
		assertTrue(candidates.stream().filter(e -> e.toString().equals("this.functionValue")).findAny().isPresent());

	}

	private DynamicCollectedValues valuesOfModificationPoint(AstorMain main1, ValueCollector sc,
			SuspiciousModificationPoint mp0) {

		log.info("-mp-> " + mp0.getCodeElement());

		DynamothCollector dynamothCodeGenesis = sc.createCollector(main1.getEngine().getProjectFacade(), mp0);

		Map<String, List<Candidates>> values = printValuesCollected(dynamothCodeGenesis);
		return new DynamicCollectedValues(values);
	}

	private Map<String, List<Candidates>> printValuesCollected(DynamothCollector dynamothCodeGenesis) {
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
		return values;
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

	@Test
	public void testExtensionPoint_real() throws Exception {
		AstorMain main1 = new AstorMain();
		CommandSummary cs = MathCommandsTests.getMath70Command();
		cs.command.put("-mode", "cardumen");
		cs.command.put("-flthreshold", "0.1");
		cs.command.put("-stopfirst", "true");
		cs.command.put("-loglevel", "DEBUG");
		cs.command.put("-saveall", "true");
		cs.command.put("-maxgen", "0");
		cs.append("-parameters",
				"logtestexecution:true:disablelog:true:probabilistictransformation:false:"//
						+ ExtensionPoints.INGREDIENT_TRANSFORM_STRATEGY.identifier + File.pathSeparator
						+ SynthesisBasedTransformationStrategy.class.getCanonicalName() + File.pathSeparator + //
						ExtensionPoints.CODE_SYNTHESIS.identifier + File.pathSeparator
						+ DynamothIngredientSynthesizer.class.getCanonicalName()

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
		assertTrue(synthesizer instanceof DynamothIngredientSynthesizer);

		SuspiciousModificationPoint mp0 = (SuspiciousModificationPoint) variant.getModificationPoints().get(0);

		List<Ingredient> ingredients = synthesizerStrategy.transform(mp0, new Ingredient(mp0.getCodeElement()));
		assertTrue(ingredients.size() > 0);
		log.info("Ingredients retrieved: " + ingredients);
		for (Ingredient ingredient : ingredients) {
			CtElement elementIng = ingredient.getCode();
			assertNotNull(elementIng);
			assertFalse(elementIng.toString().isEmpty());
			log.info("--> " + elementIng.toString());
		}
	}

}
