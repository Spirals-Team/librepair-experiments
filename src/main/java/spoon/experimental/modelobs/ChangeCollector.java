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
package spoon.experimental.modelobs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spoon.compiler.Environment;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.EarlyTerminatingScanner;

/**
 * Remembers changes on the spoon model
 */
public class ChangeCollector {
	private final Map<CtElement, Set<CtRole>> elementToChangeRole = new IdentityHashMap<>();
	private final ChangeListener changeListener = new ChangeListener();

	/**
	 * @param env to be checked {@link Environment}
	 * @return {@link ChangeCollector} attached to the `env` or null if there is none
	 */
	public static ChangeCollector getChangeCollector(Environment env) {
		FineModelChangeListener mcl = env.getModelChangeListener();
		if (mcl instanceof ChangeListener) {
			return ((ChangeListener) mcl).getChangeCollector();
		}
		return null;
	}

	/**
	 * Attaches itself to {@link CtModel} to listen to all changes of it's child elements
	 * @param model
	 */
	public void attachTo(CtModel model) {
		model.getUnnamedModule().getFactory().getEnvironment().setModelChangeListener(changeListener);
	}

	/**
	 * @param currentElement the {@link CtElement} whose change has to be checked
	 * @return set of {@link CtRole}s whose attribute was changed on `currentElement` since this {@link ChangeCollector} was attached
	 */
	public Set<CtRole> getDirectChanges(CtElement currentElement) {
		Set<CtRole> changes = elementToChangeRole.get(currentElement);
		if (changes == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(changes);
	}

	/**
	 * Checks if AST was changed
	 * @param currentElement the root {@link CtElement} of to be checked AST
	 * @return true if `currentElement` or any child of `currentElement` is changed
	 */
	public boolean isChangedOrChildChanged(CtElement currentElement) {
		EarlyTerminatingScanner<CtElement> scanner = new EarlyTerminatingScanner<CtElement>() {
			@Override
			protected void enter(CtElement e) {
				if (elementToChangeRole.containsKey(e)) {
					setResult(e);
					terminate();
				}
			};
		};
		scanner.scan(currentElement);
		return scanner.getResult() != null;
	}

	private void onChange(CtElement currentElement, CtRole role) {
		Set<CtRole> roles = elementToChangeRole.get(currentElement);
		if (roles == null) {
			roles = new HashSet<>();
			elementToChangeRole.put(currentElement, roles);
		}
		roles.add(role);
	}

	private class ChangeListener implements FineModelChangeListener {
		private ChangeCollector getChangeCollector() {
			return ChangeCollector.this;
		}

		@Override
		public void onObjectUpdate(CtElement currentElement, CtRole role, CtElement newValue, CtElement oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onObjectUpdate(CtElement currentElement, CtRole role, Object newValue, Object oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onObjectDelete(CtElement currentElement, CtRole role, CtElement oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onListAdd(CtElement currentElement, CtRole role, List field, CtElement newValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onListAdd(CtElement currentElement, CtRole role, List field, int index, CtElement newValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onListDelete(CtElement currentElement, CtRole role, List field, Collection<? extends CtElement> oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onListDelete(CtElement currentElement, CtRole role, List field, int index, CtElement oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onListDeleteAll(CtElement currentElement, CtRole role, List field, List oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public <K, V> void onMapAdd(CtElement currentElement, CtRole role, Map<K, V> field, K key, CtElement newValue) {
			onChange(currentElement, role);
		}

		@Override
		public <K, V> void onMapDeleteAll(CtElement currentElement, CtRole role, Map<K, V> field, Map<K, V> oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onSetAdd(CtElement currentElement, CtRole role, Set field, CtElement newValue) {
			onChange(currentElement, role);
		}

		@Override
		public <T extends Enum> void onSetAdd(CtElement currentElement, CtRole role, Set field, T newValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onSetDelete(CtElement currentElement, CtRole role, Set field, CtElement oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onSetDelete(CtElement currentElement, CtRole role, Set field, Collection<ModifierKind> oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onSetDelete(CtElement currentElement, CtRole role, Set field, ModifierKind oldValue) {
			onChange(currentElement, role);
		}

		@Override
		public void onSetDeleteAll(CtElement currentElement, CtRole role, Set field, Set oldValue) {
			onChange(currentElement, role);
		}
	}
}
