package fr.inria.astor.approaches.tos.ingredients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import fr.inria.astor.approaches.tos.entity.TOSDynamicIngredient;
import fr.inria.astor.approaches.tos.entity.TOSVariablePlaceholder;
import fr.inria.astor.core.entities.Ingredient;
import fr.inria.astor.core.entities.ModificationPoint;
import fr.inria.astor.core.loop.spaces.ingredients.transformations.IngredientTransformationStrategy;
import fr.inria.astor.core.manipulation.sourcecode.VarAccessWrapper;
import fr.inria.astor.core.manipulation.sourcecode.VarCombinationForIngredient;
import fr.inria.astor.core.manipulation.sourcecode.VarMapping;
import fr.inria.astor.core.manipulation.sourcecode.VariableResolver;
import fr.inria.astor.core.setup.RandomManager;
import fr.inria.astor.util.MapList;
import spoon.reflect.code.CtCodeElement;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtVariable;

/**
 * 
 * @author Matias Martinez
 *
 */
public class TOSRandomTransformationStrategy implements IngredientTransformationStrategy {

	protected Logger logger = Logger.getLogger(TOSRandomTransformationStrategy.class.getName());

	public TOSRandomTransformationStrategy() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Ingredient> transform(ModificationPoint modificationPoint, Ingredient baseIngredient) {
		//
		List<Ingredient> candidateIngredients = new ArrayList<>();

		CtCodeElement codeElementToModifyFromBase = (CtCodeElement) baseIngredient.getCode();

		TOSVariablePlaceholder tosingredient = (TOSVariablePlaceholder) baseIngredient;
		// Vars in scope at the modification point
		List<CtVariable> variablesInScope = modificationPoint.getContextOfModificationPoint();

		// Check Those vars not transformed must exist in context
		List<CtVariableAccess> concreteVars = tosingredient.getVariablesNotModified();
		List<CtVariableAccess> outOfContext = VariableResolver.retriveVariablesOutOfContext(variablesInScope,
				concreteVars);
		if (outOfContext != null && !outOfContext.isEmpty()) {
			logger.debug("Concrete vars could not be mapped  " + outOfContext + "\nin context: " + variablesInScope);
			return candidateIngredients;

		}

		// Once we mapped all concrete variables (i.e., not transformed), and we
		// are sure they exist in
		// context.

		// Now we map placeholders with vars in scope:
		MapList<String, CtVariableAccess> placeholders = tosingredient.getPalceholders();

		List<CtVariableAccess> placeholdersVariables = new ArrayList<>();
		for (List<CtVariableAccess> pvs : placeholders.values()) {
			placeholdersVariables.addAll(pvs);
		}

		logger.debug("Placeholder variables to map: " + placeholdersVariables);
		VarMapping mapping = VariableResolver.mapVariablesFromContext(variablesInScope, codeElementToModifyFromBase,
				placeholdersVariables);

		// if we map all placeholder variables
		if (mapping.getNotMappedVariables().isEmpty()) {
			if (mapping.getMappedVariables().isEmpty()) {
				// nothing to transform, accept the ingredient
				logger.debug("Something is wrong: Any placeholder var was mapped ");
				candidateIngredients.add(new Ingredient(codeElementToModifyFromBase));

			} else {// We have mappings between variables
				logger.debug("Ingredient before transformation: " + baseIngredient);

				List<VarCombinationForIngredient> allCombinations = findAllVarMappingCombinationUsingRandom(
						mapping.getMappedVariables());

				if (allCombinations.size() > 0) {

					for (VarCombinationForIngredient varCombinationForIngredient : allCombinations) {
						// We create a possible candidate for each combination.
						TOSDynamicIngredient tosd = new TOSDynamicIngredient(varCombinationForIngredient, mapping,
								codeElementToModifyFromBase, tosingredient);
						candidateIngredients.add(tosd);
					}
				}
			}
		} else {

			// Placeholders without mapping: we discart it.
			logger.debug(
					String.format("Placeholders without mapping (%d/%d): %s ", mapping.getNotMappedVariables().size(),
							placeholdersVariables.size(), mapping.getNotMappedVariables().toString()));
			String varContext = "";
			for (CtVariable context : variablesInScope) {
				varContext += context.getSimpleName() + " " + context.getType().getQualifiedName() + ", ";
			}
			logger.debug("Context: " + varContext);
			for (CtVariableAccess ingredient : mapping.getNotMappedVariables()) {
				logger.debug("---out_of_context: " + ingredient.getVariable().getSimpleName() + ": "
						+ ingredient.getVariable().getType().getQualifiedName());
			}
		}

		return candidateIngredients;
	}

	public List<VarCombinationForIngredient> findAllVarMappingCombinationUsingRandom(
			Map<VarAccessWrapper, List<CtVariable>> mappedVars) {

		List<VarCombinationForIngredient> allCom = new ArrayList<>();

		List<Map<String, CtVariable>> allWithoutOrder = VariableResolver.findAllVarMappingCombination(mappedVars, null);

		for (Map<String, CtVariable> varMapping : allWithoutOrder) {
			try {
				VarCombinationForIngredient varCombinationWrapper = new VarCombinationForIngredient(varMapping);
				// In random mode, all same probabilities
				varCombinationWrapper.setProbality((double) 1 / (double) allWithoutOrder.size());
				allCom.add(varCombinationWrapper);
			} catch (Exception e) {
				logger.error("Error for obtaining a string representation of combination with " + varMapping.size()
						+ " variables");
			}
		}
		Collections.shuffle(allCom, RandomManager.getRandom());

		logger.debug("Number combination RANDOMLY sorted : " + allCom.size() + " over " + allWithoutOrder.size());

		return allCom;

	}
}
