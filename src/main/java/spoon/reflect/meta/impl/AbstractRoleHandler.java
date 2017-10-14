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
package spoon.reflect.meta.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import spoon.SpoonException;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.CtRoleHandler;
import spoon.reflect.path.CtRole;

/**
 * common implementation of {@link CtRoleHandler}
 * @param <T> the type of node whose attribute has to be manipulated
 * @param <U> the type of value of the attribute
 */
abstract class AbstractRoleHandler<T, U> implements CtRoleHandler {

	private final CtRole role;
	private final Class<?> targetClass;
	private final Class<?> valueClass;

	protected AbstractRoleHandler(CtRole role, Class<?> targetType, Class<?> valueType) {
		this.role = role;
		this.targetClass = targetType;
		this.valueClass = valueType;
	}

	@Override
	public CtRole getRole() {
		return role;
	}

	@Override
	public Class<?> getTargetType() {
		return targetClass;
	}


	@SuppressWarnings("unchecked")
	protected T castTarget(CtElement element) {
		return (T) element;
	}
	@SuppressWarnings("unchecked")
	protected U castValue(Object value) {
		return (U) value;
	}

	protected void checkItemsClass(Iterable<?> iterable) {
		//check that each item has expected class
		for (Object value : iterable) {
			if (value != null && valueClass.isInstance(value) == false) {
				throw new ClassCastException(value.getClass().getName() + " cannot be cast to " + valueClass.getName());
			}
		}
	}

	public <V extends CtElement> void setValue(V element, Object value) {
		throw new SpoonException("Setting of CtRole." + role.name() + " is not supported for " + element.getClass().getSimpleName());
	};

	@Override
	public boolean isSingleValue() {
		return false;
	}

	@Override
	public boolean isValueList() {
		return false;
	}

	@Override
	public boolean isValueSet() {
		return false;
	}

	@Override
	public boolean isValueMap() {
		return false;
	}


	@Override
	public Class<?> getValueClass() {
		return valueClass;
	}

	abstract static class SingleHandler<T, U> extends AbstractRoleHandler<T, U> {

		protected SingleHandler(CtRole role, Class<?> targetType, Class<?> valueClass) {
			super(role, targetType, valueClass);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean isSingleValue() {
			return true;
		}
	}

	abstract static class ListHandler<T, V> extends AbstractRoleHandler<T, List<V>> {

		protected ListHandler(CtRole role, Class<?> targetType, Class<?> valueClass) {
			super(role, targetType, valueClass);
		}

		@Override
		public boolean isValueList() {
			return true;
		}

		@Override
		protected List<V> castValue(Object value) {
			List<V> list = super.castValue(value);
			//check that each item has expected class
			checkItemsClass(list);
			return list;
		}
	}
	abstract static class SetHandler<T, V> extends AbstractRoleHandler<T, Set<V>> {

		protected SetHandler(CtRole role, Class<?> targetType, Class<?> valueClass) {
			super(role, targetType, valueClass);
		}

		@Override
		public boolean isValueSet() {
			return true;
		}

		@Override
		protected Set<V> castValue(Object value) {
			Set<V> set = super.castValue(value);
			//check that each item has expected class
			checkItemsClass(set);
			return set;
		}
	}

	abstract static class MapHandler<T, V> extends AbstractRoleHandler<T, Map<String, V>> {

		protected MapHandler(CtRole role, Class<?> targetType, Class<?> valueClass) {
			super(role, targetType, valueClass);
		}

		@Override
		public boolean isValueSet() {
			return true;
		}

		@Override
		protected Map<String, V> castValue(Object value) {
			Map<String, V> map = super.castValue(value);
			//check that each item has expected class
			checkItemsClass(map.values());
			return map;
		}
	}
}
