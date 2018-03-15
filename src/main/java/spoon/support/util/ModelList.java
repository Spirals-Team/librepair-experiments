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
package spoon.support.util;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import spoon.experimental.modelobs.FineModelChangeListener;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.path.CtRole;
import spoon.support.reflect.declaration.CtElementImpl;

/**
 * The implementation of the {@link List}, which is used by Spoon model objects.
 * It assures:
 * 1) each inserted {@link CtElement} gets assigned correct parent
 * 2) each change is reported in {@link FineModelChangeListener}
 *
 * @param <T>
 */
public abstract class ModelList<T extends CtElement> extends AbstractList<T> {

	private final CtElement owner;
	private List<T> list = CtElementImpl.emptyList();

	protected ModelList(CtElement owner) {
		this.owner = owner;
	}

	protected abstract CtRole getRole();
	protected abstract int getDefaultCapacity();
	protected abstract void onSizeChanged(int newSize);

	@Override
	public T get(int index) {
		return list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public T set(int index, T element) {
		T oldElement = list.get(index);
		if (oldElement == element) {
			//no change
			return oldElement;
		}
		ensureModifiableStatementsList();
		getModelChangeListener().onListDelete(owner, getRole(), list, index, oldElement);
		element.setParent(owner);
		getModelChangeListener().onListAdd(owner, getRole(), list, index, element);
		list.set(index, element);
		return oldElement;
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(T e) {
		if (e == null) {
			return false;
		}
		ensureModifiableStatementsList();
		e.setParent(owner);
		getModelChangeListener().onListAdd(owner, getRole(), list, e);
		boolean result = list.add(e);
		onSizeChanged(list.size());
		return result;
	}

	@Override
	public boolean remove(Object o) {
		if (list.isEmpty()) {
			return false;
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			//first do not use equals, but same
			if (list.get(i) == o) {
				remove(i);
				return true;
			}
		}
		int idx = list.indexOf(o);
		if (idx >= 0) {
			remove(idx);
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public void clear() {
		getModelChangeListener().onListDeleteAll(owner, getRole(), list, new ArrayList<>(list));
		list = CtElementImpl.emptyList();
		onSizeChanged(list.size());
	}

	@Override
	public boolean equals(Object o) {
		return list.equals(o);
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public void add(int index, T element) {
		if (element == null) {
			return;
		}
		ensureModifiableStatementsList();
		element.setParent(owner);
		getModelChangeListener().onListAdd(owner, getRole(), list, index, element);
		list.add(index, element);
		onSizeChanged(list.size());
	}

	@Override
	public T remove(int index) {
		T oldElement = list.get(index);
		getModelChangeListener().onListDelete(owner, getRole(), list, index, oldElement);
		list.remove(index);
		onSizeChanged(list.size());
		return oldElement;
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	private void ensureModifiableStatementsList() {
		if (list == CtElementImpl.<CtStatement>emptyList()) {
			list = new ArrayList<>(getDefaultCapacity());
		}
	}

	private FineModelChangeListener getModelChangeListener() {
		return owner.getFactory().getEnvironment().getModelChangeListener();
	}
}
