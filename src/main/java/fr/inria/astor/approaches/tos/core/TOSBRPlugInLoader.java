package fr.inria.astor.approaches.tos.core;

import java.util.ArrayList;
import java.util.List;

import com.martiansoftware.jsap.JSAPException;

import fr.inria.astor.approaches.ingredientbased.IngredientBasedApproach;
import fr.inria.astor.approaches.ingredientbased.IngredientBasedPlugInLoader;
import fr.inria.astor.approaches.jgenprog.operators.ReplaceOp;
import fr.inria.astor.approaches.tos.ingredients.TOSBStatementIngredientSpace;
import fr.inria.astor.approaches.tos.ingredients.TOSIngredientSearchStrategy;
import fr.inria.astor.core.loop.AstorCoreEngine;
import fr.inria.astor.core.loop.spaces.ingredients.scopes.IngredientSpaceScope;
import fr.inria.astor.core.loop.spaces.operators.OperatorSpace;
import fr.inria.astor.core.manipulation.filters.TargetElementProcessor;
import fr.inria.astor.core.setup.ConfigurationProperties;
import fr.inria.main.evolution.ExtensionPoints;

/**
 * Load the plug ins that TOS approaches needs.
 * 
 * @author Matias Martinez
 *
 */
public class TOSBRPlugInLoader extends IngredientBasedPlugInLoader {

	@Override
	protected void loadTargetElements(AstorCoreEngine approach) throws Exception {

		List<TargetElementProcessor<?>> targetElementProcessors = new ArrayList<TargetElementProcessor<?>>();
		targetElementProcessors.add(new StatementFixSpaceProcessor());
		approach.setTargetElementProcessors(targetElementProcessors);
		log.debug("loaded SBR target element: " + targetElementProcessors);
	}

	@Override
	protected void loadIngredientPool(AstorCoreEngine approach) throws JSAPException, Exception {
		IngredientBasedApproach ibra = (IngredientBasedApproach) approach;

		List<TargetElementProcessor<?>> ingredientProcessors = approach.getTargetElementProcessors();
		TOSBStatementIngredientSpace ingredientspace = new TOSBStatementIngredientSpace(ingredientProcessors);
		String scope = ConfigurationProperties.getProperty(ExtensionPoints.INGREDIENT_STRATEGY_SCOPE.identifier);
		if (scope != null) {
			ingredientspace.scope = IngredientSpaceScope.valueOf(scope.toUpperCase());
		}
		ibra.setIngredientPool(ingredientspace);
		log.debug("loaded SBR ingredient space ");

	}

	@Override
	protected void loadIngredientSearchStrategy(AstorCoreEngine approach) throws Exception {

		super.loadIngredientSearchStrategy(approach);
		IngredientBasedApproach ibra = (IngredientBasedApproach) approach;

		ibra.setIngredientSearchStrategy(new TOSIngredientSearchStrategy(ibra.getIngredientPool()));
	}

	@Override
	protected void loadOperatorSpaceDefinition(AstorCoreEngine approach) throws Exception {

		OperatorSpace opSpace = new OperatorSpace();
		opSpace.register(new ReplaceOp());
		approach.setOperatorSpace(opSpace);

	}

}
