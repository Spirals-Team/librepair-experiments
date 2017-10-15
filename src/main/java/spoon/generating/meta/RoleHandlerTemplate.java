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
package spoon.generating.meta;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.path.CtRole;

class RoleHandlerTemplate extends AbstractHandler<Node, ValueType> {

	private RoleHandlerTemplate() {
		super($Role$.ROLE, $TargetType$.class, ValueType.class);
	}

	@Override
	public Object getValue(CtElement element) {
		return castTarget(element).$getterName$();
	}

	@Override
	public void setValue(CtElement element, Object value) {
		castTarget(element).$setterName$(castValue(value));
	}
}

enum $Role$ {
	ROLE
}

class $TargetType$ {
}

class Node {
	ValueType $getterName$() {
		return null;
	}
	void $setterName$(ValueType value) {
	}
}

class ValueType {
}

class AbstractHandler<T, U> implements RoleHandler {

	AbstractHandler($Role$ role, Class<?> targetClass, Class<?> valueClass) {
	}
	T castTarget(Object e) {
		return null;
	}
	U castValue(Object value) {
		return null;
	}
	@Override
	public CtRole getRole() {
		return null;
	}
	@Override
	public Class<? extends CtElement> getTargetType() {
		return null;
	}
	@Override
	public <T extends CtElement, U> U getValue(T element) {
		return null;
	}
	@Override
	public <T extends CtElement, U> void setValue(T element, U value) {
	}
	@Override
	public Class<?> getValueClass() {
		// TODO Auto-generated method stub
		return null;
	}
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
}
