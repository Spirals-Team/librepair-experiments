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
package spoon.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.spi.RootLogger;

import spoon.SpoonException;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.DerivedProperty;

import static spoon.metamodel.SpoonMetaModel.addUniqueObject;
import static spoon.metamodel.SpoonMetaModel.getOrCreate;

/**
 * Represents a field of Spoon model type.
 * Each MMField belongs to one MMType
 */
public class MMField {
	/**
	 * Name of the field
	 */
	final String name;
	/**
	 * {@link CtRole} of the field
	 */
	final CtRole role;
	/**
	 * The list of {@link MMType}s which contains this field
	 */
	final MMType ownerType;
	/**
	 * Type of value container [single, list, set, map]
	 */
	MMContainerType valueContainerType;
	/**
	 * The type of value of this field - can be Set, List, Map or any non collection type
	 */
	private CtTypeReference<?> valueType;
	/**
	 * The item type of value of this field - can be non collection type
	 */
	private CtTypeReference<?> itemValueType;

	Boolean derived;

	public CtMethod<?> get;
	public CtMethod<?> set;
	public CtMethod<?> add;
	public CtMethod<?> addFirst;
	public CtMethod<?> addLast;
	public CtMethod<?> addOn;
	public CtMethod<?> remove;


	/**
	 * methods of this field defined directly on ownerType.
	 * There is PropertyGetter or PropertySetter annotation with `role` of this {@link MMField}
	 */
	final List<CtMethod<?>> roleMethods = new ArrayList<>();
	final Map<String, List<CtMethod<?>>> roleMethodsBySignature = new HashMap<>();
	/**
	 * List of fields with same `role`, from super type of `ownerType` {@link MMType}
	 */
	final List<MMField> superFields = new ArrayList<>();

	/**
	 * own and inherited methods grouped by method signature and the methods
	 * in list ordered by own methods are first
	 */
	final Map<String, List<CtMethod<?>>> allRoleMethodsBySignature = new HashMap<>();

	MMField(String name, CtRole role, MMType ownerType) {
		super();
		this.name = name;
		this.role = role;
		this.ownerType = ownerType;
	}

	void addMethod(CtMethod<?> method) {
		roleMethods.add(method);
		String signature = method.getSignature();
		addUniqueObject(getOrCreate(roleMethodsBySignature, signature, () -> new ArrayList<>()), method);
		addUniqueObject(getOrCreate(allRoleMethodsBySignature, signature,() -> new ArrayList<>()), method);
	}

	void addSuperField(MMField superMMField) {
		if (addUniqueObject(superFields, superMMField)) {
			for (Map.Entry<String, List<CtMethod<?>>> e : superMMField.allRoleMethodsBySignature.entrySet()) {
				getOrCreate(allRoleMethodsBySignature, e.getKey(), () -> new ArrayList<>()).addAll(e.getValue());
			}
		}
	}

	public String getName() {
		return name;
	}

	public CtRole getRole() {
		return role;
	}

	public MMType getOwnerType() {
		return ownerType;
	}

	public MMContainerType getValueContainerType() {
		return valueContainerType;
	}

	public CtTypeReference<?> getValueType() {
		return valueType;
	}

	void setValueType(CtTypeReference<?> valueType) {
		this.valueType = valueType;
		this.valueContainerType = MMContainerType.valueOf(valueType.getActualClass());
		if (valueContainerType != MMContainerType.SINGLE) {
			if(valueContainerType == MMContainerType.MAP) {
				itemValueType = valueType.getActualTypeArguments().get(1);
				if (String.class.getName().equals(valueType.getActualTypeArguments().get(0).getQualifiedName()) == false) {
					throw new SpoonException("Unexpected container of type: " + valueType.toString());
				}
			} else {
				itemValueType = valueType.getActualTypeArguments().get(0);
			}
				
		} else {
			itemValueType = valueType;
		}
	}

	public CtTypeReference<?> getItemValueType() {
		return itemValueType;
	}
	public void setItemValueType(CtTypeReference<?> itemValueType) {
		this.itemValueType = itemValueType;
	}

	public boolean isDerived() {
		if (derived == null) {
			if(role == CtRole.FIELD) {
				this.getClass();
			}
			CtTypeReference<DerivedProperty> derivedProperty = get.getFactory().createCtTypeReference(DerivedProperty.class);
			//if DerivedProperty is found on any getter of this type, then this field is derived
			boolean isConreteMethod = false;
			List<CtMethod<?>> ownGetterMethods = roleMethodsBySignature.get(get.getSignature());
			if (ownGetterMethods != null) {
				for (CtMethod<?> ctMethod : ownGetterMethods) {
					if(ctMethod.getAnnotation(derivedProperty) != null) {
						derived = Boolean.TRUE;
						return true;
					}
					isConreteMethod = isConreteMethod || ctMethod.getBody() != null;
				}
				if (isConreteMethod) {
					//there exists a implementation of getter for this field in this type and there is no  DerivedProperty here, so it is not derived!
					derived = Boolean.FALSE;
					return false;
				}
			}
			//inherit derived property from super type
			//if DerivedProperty annotation is not found on any get method, then it is not derived
			derived = Boolean.FALSE;
			//check all super fields. If any of them is derived then this field is derived too
			for (MMField superField : superFields) {
				if (superField.isDerived()) {
					derived = Boolean.TRUE;
					break;
				}
			}
		}
		return derived;
	}

	public List<CtMethod<?>> getRoleMethods() {
		return roleMethods;
	}

	public List<MMField> getSuperFields() {
		return superFields;
	}

	public Map<String, List<CtMethod<?>>> getAllRoleMethodsBySignature() {
		return allRoleMethodsBySignature;
	}
	
	@Override
	public String toString() {
		return ownerType.getName() + "#" + getName() + "<" + valueType + ">";
	}
	
	/**
	 * @return the super MMField which has same valueType and which is in root of the most implementations
	 */
	public MMField getRootSuperField() {
		List<MMField> potentialRootSuperFields = new ArrayList<>();
		if (roleMethods.size() > 0) {
			potentialRootSuperFields.add(this);
		}
		superFields.forEach(superField -> {
			addUniqueObject(potentialRootSuperFields, superField.getRootSuperField());
		});
		int idx = 0;
		if (potentialRootSuperFields.size() > 1) {
			CtTypeReference<?> expectedValueType = this.valueType.getTypeErasure();
			for (int i = 1; i < potentialRootSuperFields.size(); i++) {
				MMField superField = potentialRootSuperFields.get(i);
				if (superField.valueType.getTypeErasure().equals(expectedValueType) == false) {
					break;
				}
				idx = i;
			}
		}
//		if (role == CtRole.ASSIGNMENT && ownerType.getName().equals("CtLocalVariable")) {
//			this.getClass();
//		}
		return potentialRootSuperFields.get(idx);
	}
}
