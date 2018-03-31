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
package spoon.reflect.visitor.printer.change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import spoon.SpoonException;
import spoon.experimental.modelobs.ChangeCollector;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.cu.position.BodyHolderSourcePosition;
import spoon.reflect.cu.position.DeclarationSourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.path.CtRole;
import spoon.support.reflect.cu.position.SourcePositionImpl;

/**
 * source code position helper methods
 */
public abstract class SourcePositionUtils  {

	public static List<SourceFragment> getSourceFragmentsOfElement(ChangeCollector changeCollector, CtElement element) {
		//it is not muted yet, so some child is modified
		//detect source code fragments of this element
		SourcePosition sourcePosition = getNonEmptySourcePosition(element);
		if (sourcePosition == null) {
			//we have no origin sources.
			return Collections.emptyList();
		}
		//The origin sources of this element are available
		//check if this element was changed
		Set<CtRole> changedRoles = changeCollector.getChanges(element);
		if (changedRoles.isEmpty()) {
			//element was not changed and we know origin sources
			//use origin source instead of printed code
			return Collections.singletonList(getMainSourcePositionFragment(element));
		}
		//element is changed. Detect source fragments of this element
		List<SourceFragment> fragments = SourcePositionUtils.getSourcePositionFragments(element);
		if (fragments.size() <= 1) {
			//there is only one source fragment and it is modified.
			//So we cannot use origin sources
			return Collections.emptyList();
		}
		/*
		 * there are more fragments.
		 * Mark which fragments contains source code of data from modified roles
		 */
		//detect which roles of this element contains a change
		if (markChangedFragments(element, fragments, changedRoles)) {
			return fragments;
		}
		//this kind of changes is not supported for this element yet. We cannot use origin sources :-(
		return Collections.emptyList();
	}

	/**
	 * @param element
	 * @return {@link SourcePosition} if it is not empty and the origin sources are available. Else it returns null
	 */
	private static SourcePosition getNonEmptySourcePosition(CtElement element) {
		if (element != null) {
			SourcePosition sp = element.getPosition();
			if (sp.getSourceStart() >= 0 && sp.getSourceEnd() >= 0 && sp.getCompilationUnit() != null && sp.getCompilationUnit().getOriginalSourceCode() != null) {
				return sp;
			}
		}
		return null;
	}

	/**
	 * @param element an {@link CtElement}
	 * @return {@link SourceFragment} of source code of whole `element` or null if origin sources are not available
	 */
	private static SourceFragment getMainSourcePositionFragment(CtElement element) {
		SourcePosition sp = getNonEmptySourcePosition(element);
		if (sp != null) {
			return new SourceFragment(sp, FragmentType.MAIN_FRAGMENT, sp.getSourceStart(), sp.getSourceEnd() + 1);
		}
		return null;
	}
	/**
	 * @param element the {@link CtElement} whose fragments has to be analyzed
	 * @return {@link List} of {@link SourceFragment}s of the `element`.
	 * If the origin source of element is not available then returns empty list.
	 */
	private static List<SourceFragment> getSourcePositionFragments(CtElement element) {
		SourcePosition sp = getNonEmptySourcePosition(element);
		if (sp != null) {
			return getSourcePositionFragments(element, sp);
		}
		return Collections.emptyList();
	}

	private static List<SourceFragment> getSourcePositionFragments(CtElement element, SourcePosition sourcePosition) {
		if (sourcePosition instanceof DeclarationSourcePosition) {
			List<SourceFragment> fragments = new ArrayList<>(4);
			DeclarationSourcePosition dsp = (DeclarationSourcePosition) sourcePosition;
			int endOfLastFragment = dsp.getSourceStart();
			if (endOfLastFragment < dsp.getModifierSourceStart()) {
				throw new SpoonException("DeclarationSourcePosition#sourceStart < modifierSourceStart for class: " + element.getClass());
			}
			fragments.add(new SourceFragment(sourcePosition, FragmentType.MODIFIERS,  dsp.getModifierSourceStart(), endOfLastFragment = dsp.getModifierSourceEnd() + 1));
			if (endOfLastFragment < dsp.getNameStart()) {
				fragments.add(new SourceFragment(sourcePosition, FragmentType.BEFORE_NAME,  endOfLastFragment, dsp.getNameStart()));
			}
			fragments.add(new SourceFragment(sourcePosition, FragmentType.NAME, dsp.getNameStart(), endOfLastFragment = dsp.getNameEnd() + 1));
			if (dsp instanceof BodyHolderSourcePosition) {
				BodyHolderSourcePosition bhsp = (BodyHolderSourcePosition) dsp;
				if (endOfLastFragment < bhsp.getBodyStart()) {
					fragments.add(new SourceFragment(sourcePosition, FragmentType.AFTER_NAME,  endOfLastFragment, bhsp.getBodyStart()));
				}
				fragments.add(new SourceFragment(sourcePosition, FragmentType.BODY, bhsp.getBodyStart(), endOfLastFragment = bhsp.getBodyEnd() + 1));
				if (endOfLastFragment != bhsp.getBodyEnd() + 1) {
					throw new SpoonException("Last bodyEnd is not equal to SourcePosition#sourceEnd: " + element.getClass());
				}
			}
			return fragments;
		}
		return Collections.singletonList(new SourceFragment(sourcePosition, FragmentType.MAIN_FRAGMENT, sourcePosition.getSourceStart(), sourcePosition.getSourceEnd() + 1));
	}

	private static class TypeToFragmentDescriptor {
		Class<? extends CtElement> type;
		Map<FragmentType, FragmentDescriptor> fragmentToRoles = new HashMap<>();
		TypeToFragmentDescriptor(Class<? extends CtElement> type) {
			this.type = type;
		}

		TypeToFragmentDescriptor fragment(FragmentType ft, Consumer<FragmentDescriptor> initializer) {
			FragmentDescriptor fd = new FragmentDescriptor();
			initializer.accept(fd);
			fragmentToRoles.put(ft, fd);
			return this;
		}

		public boolean matchesElement(CtElement element) {
			return type.isInstance(element);
		}
	}

	static class FragmentDescriptor {
		Set<CtRole> roles;
		private List<BiPredicate<String, String>> startTokenDetector = new ArrayList<>();
		private List<BiPredicate<String, String>> endTokenDetector = new ArrayList<>();
		private Set<CtRole> startScanRole = new HashSet<>();

		FragmentDescriptor role(CtRole... roles) {
			this.roles = new HashSet(Arrays.asList(roles));
			return this;
		}

		FragmentDescriptor startWhenKeyword(String...tokens) {
			initTokenDetector(startTokenDetector, "writeKeyword", tokens);
			return this;
		}
		FragmentDescriptor endWhenKeyword(String...tokens) {
			initTokenDetector(endTokenDetector, "writeKeyword", tokens);
			return this;
		}
		FragmentDescriptor startWhenSeparator(String...tokens) {
			initTokenDetector(startTokenDetector, "writeSeparator", tokens);
			return this;
		}
		FragmentDescriptor endWhenSeparator(String...tokens) {
			initTokenDetector(endTokenDetector, "writeSeparator", tokens);
			return this;
		}
		FragmentDescriptor startWhenIdentifier(String...tokens) {
			initTokenDetector(startTokenDetector, "writeIdentifier", tokens);
			return this;
		}
		FragmentDescriptor endWhenIdentifier(String...tokens) {
			initTokenDetector(endTokenDetector, "writeIdentifier", tokens);
			return this;
		}
		FragmentDescriptor startWhenScan(CtRole...roles) {
			startScanRole.addAll(Arrays.asList(roles));
			return this;
		}

		void initTokenDetector(List<BiPredicate<String, String>> detectors, String tokenWriterMethodName, String...tokens) {
			BiPredicate<String, String> predicate;
			if (tokens.length == 0) {
				predicate = (methodName, token) -> {
					return methodName.equals(tokenWriterMethodName);
				};
			} else if (tokens.length == 1) {
				String expectedToken = tokens[0];
				predicate = (methodName, token) -> {
					return methodName.equals(tokenWriterMethodName) && expectedToken.equals(token);
				};
			} else {
				Set<String> kw = new HashSet<>(Arrays.asList(tokens));
				predicate = (methodName, token) -> {
					return methodName.equals(tokenWriterMethodName) && kw.contains(token);
				};
			}
			detectors.add(predicate);
		}

		void applyTo(SourceFragment fragment, Set<CtRole> toBeAppliedRoles) {
			if (roles != null) {
				for (CtRole ctRole : roles) {
					if (toBeAppliedRoles.remove(ctRole)) {
						//the role of this fragment is modified -> fragment is modified
						fragment.setModified(true);
						//and continue to remove other roles if they are changed too
					}
				}
			}
			fragment.fragmentDescriptor = this;
		}

		boolean isTriggeredByToken(boolean isStart, String tokenWriterMethodName, String token) {
			for (BiPredicate<String, String> predicate : isStart ? startTokenDetector : endTokenDetector) {
				if (predicate.test(tokenWriterMethodName, token)) {
					return true;
				}
			}
			return false;
		}

		boolean isStartedByScanRole(CtRole role) {
			return startScanRole.contains(role);
		}
	}

	private static TypeToFragmentDescriptor type(Class<? extends CtElement> type) {
		return new TypeToFragmentDescriptor(type);
	}

	private static final List<TypeToFragmentDescriptor> descriptors = Arrays.asList(
			type(CtType.class)
			.fragment(FragmentType.MODIFIERS,
					i -> i.role(CtRole.ANNOTATION, CtRole.MODIFIER))
			.fragment(FragmentType.BEFORE_NAME,
					i -> i.startWhenKeyword("class", "enum", "interface", "").startWhenSeparator("@"))
			.fragment(FragmentType.NAME,
					i -> i.role(CtRole.NAME).startWhenIdentifier().endWhenIdentifier())
			.fragment(FragmentType.AFTER_NAME,
					i -> i.role(CtRole.SUPER_TYPE, CtRole.INTERFACE, CtRole.TYPE_PARAMETER))
			.fragment(FragmentType.BODY,
					i -> i.role(CtRole.TYPE_MEMBER).startWhenSeparator("{").endWhenSeparator("}")),
			type(CtExecutable.class)
			.fragment(FragmentType.MODIFIERS,
					i -> i.role(CtRole.ANNOTATION, CtRole.MODIFIER))
			.fragment(FragmentType.BEFORE_NAME,
					i -> i.role(CtRole.TYPE).startWhenScan(CtRole.TYPE_PARAMETER, CtRole.TYPE))
			.fragment(FragmentType.NAME,
					i -> i.role(CtRole.NAME).startWhenIdentifier().endWhenIdentifier())
			.fragment(FragmentType.AFTER_NAME,
					i -> i.role(CtRole.PARAMETER, CtRole.THROWN).startWhenSeparator("("))
			.fragment(FragmentType.BODY,
					i -> i.role(CtRole.BODY).startWhenSeparator("{").endWhenSeparator("}")),
			type(CtVariable.class)
			.fragment(FragmentType.MODIFIERS,
					i -> i.role(CtRole.ANNOTATION, CtRole.MODIFIER))
			.fragment(FragmentType.BEFORE_NAME,
					i -> i.role(CtRole.TYPE).startWhenScan(CtRole.TYPE))
			.fragment(FragmentType.NAME,
					i -> i.role(CtRole.NAME).startWhenIdentifier().endWhenIdentifier())
			.fragment(FragmentType.AFTER_NAME,
					i -> i.role(CtRole.DEFAULT_EXPRESSION))
	);

	/**
	 * Marks the {@link SourceFragment}s, which contains source code of `changedRoles` of `element`
	 * @param element the element
	 * @param fragments
	 * @param changedRoles
	 * @return true if {@link SourceFragment}s matches all changed roles, so we can use them
	 * false if current  `descriptors` is insufficient and we cannot use origin source code of any fragment
	 */
	private static boolean markChangedFragments(CtElement element, List<SourceFragment> fragments, Set<CtRole> changedRoles) {
		for (TypeToFragmentDescriptor descriptor : descriptors) {
			if (descriptor.matchesElement(element)) {
				Set<CtRole> toBeAssignedRoles = new HashSet<>(changedRoles);
				for (SourceFragment fragment : fragments) {
					//check if this fragment is modified
					FragmentDescriptor fd = descriptor.fragmentToRoles.get(fragment.getFragmentType());
					if (fd != null) {
						//detect if `fragment` is modified and setup fragment start/end detectors
						fd.applyTo(fragment, toBeAssignedRoles);
					}
				}
				//we can use it if all changed roles are matching to some fragment
				if (toBeAssignedRoles.isEmpty()) {
					return true;
				}
				element.getFactory().getEnvironment().debugMessage("The element of type " + element.getClass().getName() + " is not mapping these roles to SourceFragments: " + toBeAssignedRoles);
				return false;
			}
		}
		return false;
	}

	/**
	 * @param element {@link CtElement} whose source position is needed
	 * @return first defined source position of element or null if there is no source position
	 */
	public static SourcePositionImpl getMyOrParentsSourcePosition(CtElement element) {
		while (true) {
			if (element == null) {
				return null;
			}
			SourcePosition sp = element.getPosition();
			if (sp instanceof SourcePositionImpl) {
				return (SourcePositionImpl) sp;
			}
			if (element.isParentInitialized() == false) {
				return null;
			}
			element = element.getParent();
		}
	}
}
