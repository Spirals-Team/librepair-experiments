/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.pattern.matcher;

import spoon.SpoonException;
import spoon.pattern.ModelValueResolver;
import spoon.pattern.ParameterValueProvider;
import spoon.pattern.ParameterValueProviderFactory;
import spoon.pattern.Pattern;
import spoon.pattern.UnmodifiableParameterValueProvider;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtTypeInformation;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.chain.CtConsumableFunction;
import spoon.reflect.visitor.chain.CtConsumer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class defines an engine for matching a template to pieces of code.
 */
public class PatternMatcher implements CtConsumableFunction<Object> {

	private ParameterValueProviderFactory parameterValueProviderFactory = UnmodifiableParameterValueProvider.Factory.INSTANCE;

	/**
	 * The to be matched {@link Pattern}
	 */
	private ModelValueResolver pattern;

	/**
	 * @param pattern a {@link Pattern} whose model and parameters will be matched
	 */
	public PatternMatcher(ModelValueResolver pattern) {
		this.pattern = pattern;
//		System.out.println(pattern.toString());
		List<CtElement> model = this.pattern.getModel();
		//just check (same like in old code) if Template matches itself - TODO remove it?
		//The template doesn't matches itself if it contains live statements
//		ParameterValueProvider parameters = parameterValueProviderFactory.createParameterValueProvider();
//		if (matchesList(pattern, parameters, model, model, true) == false) {
//			throw new SpoonException("TemplateMatcher was unable to find itself, it certainly indicates a bug. Please revise your template or report an issue.");
//		}
	}

	/**
	 * Consumer {@link Match} objects
	 */
	@Override
	public void apply(Object input, CtConsumer<Object> outputConsumer) {
		if (input == null) {
			return;
		}
		if (input.getClass().isArray()) {
			input = Arrays.asList((Object[]) input);
		}

		MatchingScanner scanner = new MatchingScanner(pattern, outputConsumer);
		ParameterValueProvider parameters = parameterValueProviderFactory.createParameterValueProvider();
		if (input instanceof Collection<?>) {
			scanner.scan(null, (Collection<CtElement>) input);
		} else {
			scanner.scan(null, (CtElement) input);
		}
	}

	/**
	 * Matches a target program sub-tree against a template.
	 * The matching parameter values are stored into `parameters`
	 *
	 * @param targetRoot
	 * 		the target to be tested for match
	 * @return true if matches
	 */
	public ParameterValueProvider matches(ParameterValueProvider parameters, CtElement targetRoot) {
		if (this.pattern.isInModel(targetRoot)) {
			// This case can occur when we are scanning the entire package for example see TemplateTest#testTemplateMatcherWithWholePackage
			// Correct template matches itself of course, but client does not want that
			return null;
		}
		ParameterValueProvider resultParams = PatternMatcher.matchesList(pattern, parameters, Collections.singletonList(targetRoot), pattern.getModel(), true).getParameters();
		return resultParams;
	}

	/**
	 * Detects whether `template` AST node and `target` AST node are matching.
	 * This method is called for each node of to be matched template
	 * and for appropriate node of `target`
	 * @param pattern TODO
	 * @param parameters TODO
	 * @param target actually checked AST node from target model
	 * @param template actually checked AST node from template
	 * @return true if template matches this node, false if it does not matches
	 *
	 * note: Made private to hide the Objects.
	 */
	public static ParameterValueProvider matchElement(ModelValueResolver pattern, ParameterValueProvider parameters, Object target, Object template) {
		return matchesList(pattern, parameters, Collections.singletonList(target), Collections.singletonList(template), true).getParameters();
	}

	private static final Map<CtRole, Class[]> roleToSkippedClass = new HashMap<>();
	static {
		roleToSkippedClass.put(CtRole.COMMENT, new Class[]{Object.class});
		roleToSkippedClass.put(CtRole.POSITION, new Class[]{Object.class});
		roleToSkippedClass.put(CtRole.TYPE, new Class[]{CtInvocation.class, CtExecutableReference.class});
		roleToSkippedClass.put(CtRole.DECLARING_TYPE, new Class[]{CtExecutableReference.class});
		roleToSkippedClass.put(CtRole.INTERFACE, new Class[]{CtTypeReference.class});
		roleToSkippedClass.put(CtRole.MODIFIER, new Class[]{CtTypeReference.class});
	}

	/**
	 * @param roleHandler the to be checked role
	 * @param targetClass the class which is going to be checked
	 * @return true if the role is relevant for matching process
	 */
	public static boolean isMatchingRole(RoleHandler roleHandler, Class<?> targetClass) {
		//match on super roles only. Ignore derived roles
		if (roleHandler.getRole().getSuperRole() != null) {
			return false;
		}
		Class<?>[] classes = roleToSkippedClass.get(roleHandler.getRole());
		if (classes != null) {
			for (Class<?> cls : classes) {
				if (cls.isAssignableFrom(targetClass)) {
					return false;
				}
			}
		}
		return true;
	}

	public static ParameterValueProvider matches(ModelValueResolver pattern, ParameterValueProvider parameters, RoleHandler roleHandler, Object target, Object template) {
		if (template == target) {
			return parameters;
		}
		switch (roleHandler.getContainerKind()) {
		case LIST:
			return matchesList(pattern, parameters, (List<Object>) target, (List<Object>) template, true).getParameters();
		case SET:
			return matchesSet(pattern, parameters, roleHandler, (Set<Object>) target, (Set<Object>) template);
		case MAP:
			return matchesMap(pattern, parameters, roleHandler, (Map<String, Object>) target, (Map<String, Object>) template);
		case SINGLE:
			if (CtElement.class.isAssignableFrom(roleHandler.getValueClass())) {
				//the items are CtElement instances
				return matchElement(pattern, parameters, (CtElement) target, (CtElement) template);
			}
			/*
			 * the items are other Objects.
			 */
			return objectsEqual(target, template) ? parameters : null;
		}
		throw new SpoonException("Unexpected RoleHandler containerKind: " + roleHandler.getContainerKind());
	}

	public static boolean objectsEqual(Object a, Object b) {
		if (a == b) {
			return true;
		}
		if (a == null) {
			return false;
		}
		return a.equals(b);
	}

	/**
	 * matches `matcher` with first item of `targets` and parameters
	 * @param matcher a matcher
	 * @param targets a list
	 * @param parameters a input parameters for matching
	 * @return {@link ParameterValueProvider} if first target matches. null if `targets` is empty or first item doesn't matches
	 */
	public static ParameterValueProvider matchFirstTargetIfExists(SingleValueMatcher matcher, List<? extends Object> targets, ParameterValueProvider parameters) {
		if (targets.isEmpty()) {
			return null;
		}
		return matcher.matches(parameters, targets.get(0));
	}

	/**
	 * Matches list of target elements (TODO list of any objects?) with list of template {@link CtElement}s and or {@link ValueMatcher}s.
	 *
	 * The matching rules are:
	 * <ul>
	 * <li>template {@link CtElement} must match 1:1 with target
	 * <li>template {@link ValueMatcher} can match 0, 1 or more  target elements.
	 * <li>if there are two {@link ValueMatcher} then algorithm depends on if first one is(not) greedy.
	 * </ul>
	 *
	 * It tries all combinations until first matches. Others are not checked.
	 * @param pattern
	 * @param parameters
	 * @param targets
	 * @param templates
	 * @param matchAllTargets if true then all targets must match with templates. If false, then it is OK, when some targets stays unmatched - it is enough when all templates are matching
	 *
	 * @return true if match was found
	 */
	public static ListMatch matchesList(ModelValueResolver pattern, ParameterValueProvider parameters, List<? extends Object> targets, List<? extends Object> templates, boolean matchAllTargets) {
		return new TemplatesList(pattern, templates, matchAllTargets).matchAllWith(targets, parameters);
	}

	/**
	 * matches nodeSubstRequest on zero, one or more `targets` and then matches remaining templates
	 * @param nodeSubstRequest actually processed {@link ValueMatcher}
	 * @param targets list of to be matched targets
	 * @param parameters the input parameters for matching
	 * @param templates the list of next templates, which will be matched too
	 * @return {@link ListMatch} with matching result
	 */
	public static ListMatch matchesSubList2(ValueMatcher nodeSubstRequest, List<? extends Object> targets, ParameterValueProvider parameters, TemplatesList templates) {
		int countOfMatches = 0;
		//first match with minimum amount of occurrences
		while (countOfMatches < nodeSubstRequest.getMinOccurences()) {
			//we MUST match these elements
			parameters = matchFirstTargetIfExists(nodeSubstRequest, targets, parameters);
			if (parameters == null) {
				//there is missing required target element or it is not matching
				return ListMatch.EMPTY;
			}
			countOfMatches++;
		}
		//then check remaining optional (if any) matches
		return matchesSubList3(nodeSubstRequest, countOfMatches, targets.subList(countOfMatches, targets.size()), parameters, templates);
	}

	private static ListMatch matchesSubList3(ValueMatcher nodeSubstRequest, int countOfMatches, List<? extends Object> targets, ParameterValueProvider params, TemplatesList templates) {
		if (countOfMatches >= nodeSubstRequest.getMaxOccurences()) {
			/*
			 * this nodeSubstRequest has all allowed matches.
			 * Stop here and continue with next.
			 */
			return templates.matchAllWith(targets, params);
		}
		//matching of next elements is optional
		switch (nodeSubstRequest.getMatchingStrategy()) {
		case GREEDY: {
			{ //first try to match using this matcher
				//create copy of parameters for this potential matching branch
				ParameterValueProvider resultParams = matchFirstTargetIfExists(nodeSubstRequest, targets, params);
				if (resultParams != null) {
					//this matcher passed, try to match next one using current ValueMatcher
					ListMatch match = matchesSubList3(nodeSubstRequest, countOfMatches + 1, targets.subList(1, targets.size()), resultParams, templates);
					if (match.isMatching()) {
						//all next passed too, return that match
						return match;
					}
				}
			}
			//greedy matching with current nodeSubstRequest didn't passed. Try to match using remaining templates
			return templates.matchAllWith(targets, params);
		}
		case RELUCTANT: {
			{ //first try to match using next matcher. Use the copy of parameters, so we can get rid of them if it doesn't match
				ListMatch match = templates.matchAllWith(targets, params);
				if (match.isMatching()) {
					return match;
				}
			}
			//reluctant matching didn't passed on next elements. Try to match using this matcher
			params = matchFirstTargetIfExists(nodeSubstRequest, targets, params);
			if (params != null) {
				//this matcher passed. Match next one using current ValueMatcher
				return matchesSubList3(nodeSubstRequest, countOfMatches + 1, targets.subList(1, targets.size()), params, templates);
			}
			//nothing matched
			return ListMatch.EMPTY;
		}
		case POSSESIVE:
			//match everything using this matcher
			int maxOccurences = nodeSubstRequest.getMaxOccurences();
			int idx = 0;
			while (countOfMatches < maxOccurences && targets.size() > idx) {
				ParameterValueProvider resultParams = nodeSubstRequest.matches(params, targets.get(idx));
				if (resultParams == null) {
					//it doesn't matches now. Stop and try next templates
					break;
				}
				countOfMatches++;
				idx++;
				//it matched. Use paramsCopy as current params
				params = resultParams;
			}
			//once it did not match or there is enough matches, then continue with the next matcher
			return templates.matchAllWith(targets.subList(idx, targets.size()), params);
		}
		throw new SpoonException("Unsupported quantifier " + nodeSubstRequest.getMatchingStrategy());
	}

	public static ParameterValueProvider matchesSet(ModelValueResolver pattern, ParameterValueProvider parameters, RoleHandler roleHandler, Set<Object> target, Set<Object> template) {
		if (target == null) {
			target = Collections.emptySet();
		}
		if (template == null) {
			template = Collections.emptySet();
		}
		Map<String, Object> targetByKey = new HashMap<>();
		Map<String, Object> templateByKey = new HashMap<>();
		addItemsByKey(roleHandler, targetByKey, target);
		addItemsByKey(roleHandler, templateByKey, template);
		//first match all non parameterized values
		for (Iterator<Map.Entry<String, Object>> iter = templateByKey.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, Object> e = iter.next();
			Object templateItem = e.getValue();
			if (pattern.getSubstitutionRequest(templateItem) == null) {
				iter.remove();
				Object targetItem = targetByKey.remove(e.getKey());
				if (targetItem == null) {
					//target item is missing
					return null;
				}
				ParameterValueProvider resultParams = matchElement(pattern, parameters, targetItem, templateItem);
				if (resultParams == null) {
					return null;
				}
				parameters = resultParams;
			}
		}
		//then match all remaining (parameterized) values
		for (Map.Entry<String, Object> e : templateByKey.entrySet()) {
			Object templateItem = e.getValue();
			for (Iterator<Object> iter = targetByKey.values().iterator(); iter.hasNext();) {
				Object targetItem = iter.next();
				ParameterValueProvider resultParams = matchElement(pattern, parameters, targetItem, templateItem);
				if (resultParams != null) {
					//targetItem was matched. Remove it from to be matched set
					iter.remove();
					parameters = resultParams;
				}
			}
		}
		//at the end targetByKey must be empty -> all target items were matched
		return targetByKey.isEmpty() ? parameters : null;
	}

	public static void addItemsByKey(RoleHandler roleHandler, Map<String, Object> itemsByKey, Set<Object> items) {
		for (Object item : items) {
			String key = getUniqueID(item);
			Object conflictItem = itemsByKey.put(key, item);
			if (conflictItem != null && item != conflictItem) {
				throw new SpoonException("getUniqueID is not unique for " + conflictItem.toString() + " and " + item.toString());
			}
		}
	}

	public static String getUniqueID(Object item) {
		if (item instanceof CtTypeInformation) {
			return ((CtTypeInformation) item).getQualifiedName();
		}
		if (item instanceof Enum) {
			return ((Enum) item).name();
		}
		throw new SpoonException("Unsupported getUniqueID for " + item.getClass().getName());
	}

	public static ParameterValueProvider matchesMap(ModelValueResolver pattern, ParameterValueProvider parameters,  RoleHandler roleHandler,
			Map<String, Object> target, Map<String, Object> template) {
		if (target == null) {
			target = Collections.emptyMap();
		}
		if (template == null) {
			template = Collections.emptyMap();
		}

		if (template.keySet().equals(target.keySet()) == false) {
			return null;
		}

		for (Map.Entry<String, Object> templateEntry : template.entrySet()) {
			Object targetValue = target.get(templateEntry.getKey());
			ParameterValueProvider resultParams = matchElement(pattern, parameters, targetValue, templateEntry.getValue());
			if (resultParams == null) {
				return null;
			}
			parameters = resultParams;
		}
		return parameters;
	}
}
