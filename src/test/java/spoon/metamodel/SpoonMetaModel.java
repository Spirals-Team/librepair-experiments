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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import spoon.Launcher;
import spoon.SpoonException;
import spoon.reflect.annotations.PropertyGetter;
import spoon.reflect.annotations.PropertySetter;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypeParameter;
import spoon.reflect.factory.Factory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtTypeParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.PrinterHelper;
import spoon.reflect.visitor.filter.AllTypeMembersFunction;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.visitor.ClassTypingContext;

/**
 * Represents a Spoon meta model
 */
public class SpoonMetaModel {
	public static final String CLASS_SUFFIX = "Impl";
	/**
	 * qualified names of packages which contains interfaces of spoon model
	 */
	public static final Set<String> MODEL_IFACE_PACKAGES = new HashSet<>(Arrays.asList(
			"spoon.reflect.code",
			"spoon.reflect.declaration",
			"spoon.reflect.reference"));

	private final Factory factory;
	private PrinterHelper problems;
	private final Map<String, CtMethod<?>> allUnhandledMethodSignatures = new HashMap<>();

	/**
	 * {@link MMType}s by name
	 */
	private final Map<String, MMType> name2mmType = new HashMap<>();

	private final CtTypeReference<?> statementRef;
	private final CtTypeReference<?> iterableRef;
	private final CtTypeReference<?> mapRef;

	/**
	 * Parses spoon sources and creates factory with spoon model
	 */
	public SpoonMetaModel() {
		this(null);
	}

	/**
	 * @param factory already loaded factory with all Spoon model types
	 */
	public SpoonMetaModel(Factory factory) {
		this.factory = factory = (factory == null ? createFactory() : factory);
		statementRef = factory.createCtTypeReference(CtStatement.class);
		iterableRef = factory.createCtTypeReference(Iterable.class);
		mapRef = factory.createCtTypeReference(Map.class);
		problems = new PrinterHelper(factory.getEnvironment());
		
		factory.getModel().getRootPackage().filterChildren(new TypeFilter<>(CtInterface.class))
			.forEach((CtInterface<?> iface) -> {
				if (MODEL_IFACE_PACKAGES.contains(iface.getPackage().getQualifiedName())) {
					getOrCreateMMType(iface);
				}
			});
		
		//collect all method signatures marked as as PropertyGetter or PropertySetter which are not assigned to MMField
		if (allUnhandledMethodSignatures.size() > 0) {
			problems.write("Unhandled method signatures:").writeln();
			problems.incTab();
			forEachSortedEntry(allUnhandledMethodSignatures, (k, v) -> problems.write(getDeclaringTypeSignature(v)).writeln());
			problems.decTab();
		}
		//detect unused CtRoles
		Set<CtRole> unhandledRoles = new HashSet<>(Arrays.asList(CtRole.values()));
		name2mmType.values().forEach(mmType2 -> {
			unhandledRoles.removeAll(mmType2.role2field.keySet());
		});

		List<String> unhandledRoleNames = new ArrayList();
		unhandledRoles.forEach(it -> unhandledRoleNames.add(it.name()));
		forEachSorted(unhandledRoleNames, role -> {
			problems.write("Unused CtRole." + role + " found").writeln();
		});
	}

	protected Factory createFactory() {
		final Launcher launcher = new Launcher();
		launcher.getEnvironment().setNoClasspath(true);
		launcher.getEnvironment().setCommentEnabled(true);
		// Spoon model interfaces
		launcher.addInputResource("./src/main/java/spoon/reflect/code");
		launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
		launcher.addInputResource("./src/main/java/spoon/reflect/reference");
		launcher.addInputResource("src/main/java/spoon/support/reflect/declaration");
		launcher.addInputResource("src/main/java/spoon/support/reflect/code");
		launcher.addInputResource("src/main/java/spoon/support/reflect/reference");
		launcher.buildModel();
		return launcher.getFactory();
	}

	private MMType getOrCreateMMType(CtType<?> type) {
		String mmTypeName = getMMTypeIntefaceName(type);
		MMType mmType = getOrCreate(name2mmType, mmTypeName, () -> new MMType());
		if (mmType.name == null) {
			//it is not initialized yet. Do it now 
			mmType.name = mmTypeName;
			if (type instanceof CtInterface<?>) {
				CtInterface<?> iface = (CtInterface<?>) type;
				mmType.modelInteface = iface;
				mmType.modelClass = getImplementationOfInterface(iface);
			} else if (type instanceof CtClass<?>) {
				CtClass<?> clazz = (CtClass<?>) type;
				mmType.modelClass = clazz;
				mmType.modelInteface = getInterfaceOfImplementation(clazz);
			} else {
				throw new SpoonException("Unexpected spoon model type: " + type.getQualifiedName());
			}
			
			if (mmType.modelClass != null) {
				addFieldsOfType(mmType, mmType.modelClass);
			} 
			if (mmType.modelInteface != null) {
				//add fields of interface too. They are not added by above call of addFieldsOfType, because the MMType already exists in name2mmType
				addFieldsOfType(mmType, mmType.modelInteface);
			}
			detectMethodKinds(mmType);
		}
		return mmType;
	}

	private void detectMethodKinds(MMType mmType) {
		// collect getters, setters, ...
		for (MMField mmField : mmType.role2field.values()) {
			Set<String> unhandledSignatures = new HashSet<>(mmField.allRoleMethodsBySignature.keySet());
			// look for getter
			forEachValueWithKeyPrefix(mmField.allRoleMethodsBySignature, new String[] { "get", "is" }, (k, v) -> {
				if (v.get(0).getParameters().isEmpty() == false) {
					// ignore getters with some parameters
					return;
				}
				createFieldHandler(unhandledSignatures, mmField, null, -1, () -> mmField.get, (m) -> mmField.get = m, "get()")
						.accept(k, v);
			});

			mmField.setValueType(mmField.get == null ? null : mmField.get.getType());
			if (mmField.getValueType() instanceof CtTypeParameterReference) {
				CtTypeReference<?> boundingType = ((CtTypeParameterReference) mmField.getValueType()).getBoundingType();
				if (boundingType == null) {
					boundingType = factory.Type().OBJECT;
				}
				mmField.setValueType(boundingType);
			}
			if (mmField.getValueType() == null) {
				problems.write("Missing getter for " + mmField.ownerType.getName() + " and CtRole." + mmField.role)
						.writeln();
			}
			// look for setter
			forEachValueWithKeyPrefix(mmField.allRoleMethodsBySignature, "set", (k, v) -> {
				if (v.get(0).getParameters().size() == 1) {
					// setters with 1 parameter equal to getter return value
					createFieldHandler(unhandledSignatures, mmField, mmField.getValueType(), 0, () -> mmField.set,
							(m) -> mmField.set = m, "set(value)").accept(k, v);
					return;
				}
			});
			if (mmField.set == null) {
				problems.write("Missing setter for " + mmField.ownerType.getName() + " and CtRole." + mmField.role)
						.writeln();
			}

			if (mmField.getValueContainerType() != MMContainerType.SINGLE) {
				forEachValueWithKeyPrefix(mmField.allRoleMethodsBySignature, new String[] { "add", "insert" },
						(k, v) -> {
							if (v.get(0).getParameters().size() == 1) {
								if (v.get(0).getSimpleName().endsWith("AtTop")
										|| v.get(0).getSimpleName().endsWith("Begin")) {
									// adders with 1 parameter and fitting
									// value type
									createFieldHandler(unhandledSignatures, mmField, mmField.getItemValueType(), 0,
											() -> mmField.addFirst, (m) -> mmField.addFirst = m, "addFirst(value)")
													.accept(k, v);
									return;
								} else if (v.get(0).getSimpleName().endsWith("AtBottom")
										|| v.get(0).getSimpleName().endsWith("End")) {
									// adders with 1 parameter and fitting
									// value type
									createFieldHandler(unhandledSignatures, mmField, mmField.getItemValueType(), 0,
											() -> mmField.addLast, (m) -> mmField.addLast = m, "addLast(value)").accept(k,
													v);
									return;
								} else {
									// adders with 1 parameter and fitting
									// value type
									createFieldHandler(unhandledSignatures, mmField, mmField.getItemValueType(), 0,
											() -> mmField.add, (m) -> mmField.add = m, "add(value)").accept(k, v);
									return;
								}
							}
							if (v.get(0).getParameters().size() == 2) {
								// adders with 2 parameters. First int
								if (v.get(0).getParameters().get(0).getType().getSimpleName().equals("int")) {
									createFieldHandler(unhandledSignatures, mmField, mmField.getItemValueType(), 1,
											() -> mmField.addOn, (m) -> mmField.addOn = m, "addOn(int, value)").accept(k,
													v);
								}
								return;
							}
						});
				forEachValueWithKeyPrefix(mmField.allRoleMethodsBySignature, "remove", (k, v) -> {
					if (v.get(0).getParameters().size() == 1) {
						createFieldHandler(unhandledSignatures, mmField, mmField.getItemValueType(), 0, () -> mmField.remove,
								(m) -> mmField.remove = m, "remove(value)").accept(k, v);
					}
				});
			}
			unhandledSignatures.forEach(key -> {
				CtMethod<?> representant = mmField.allRoleMethodsBySignature.get(key).get(0);
				getOrCreate(allUnhandledMethodSignatures, getDeclaringTypeSignature(representant),
						() -> representant);
			});
		}
	}
	
	/**
	 * @return all the problems found in spoon meta model
	 */
	public String getProblems() {
		return problems.toString();
	}

	private <V> void forEachValueWithKeyPrefix(Map<String, V> map, String prefix, BiConsumer<String, V> consumer) {
		forEachValueWithKeyPrefix(map, new String[] { prefix }, consumer);
	}

	private <V> void forEachValueWithKeyPrefix(Map<String, V> map, String[] prefixes, BiConsumer<String, V> consumer) {
		for (Map.Entry<String, V> e : map.entrySet()) {
			for (String prefix : prefixes) {
				if (e.getKey().startsWith(prefix)) {
					consumer.accept(e.getKey(), e.getValue());
				}
			}
		}
	}

	private BiConsumer<String, List<CtMethod<?>>> createFieldHandler(Set<String> unhandledSignatures,
			MMField mmField, CtTypeReference valueType, int valueParamIdx, Supplier<CtMethod<?>> fieldGetter,
			Consumer<CtMethod<?>> fieldSetter, String fieldName) {
		return (k, v) -> {
			//Look for first method which has a body
			CtMethod<?> method = getConcreteMethod(v);
			// use getTypeErasure() comparison, because some getters and setters
			// does not fit exactly
			List<CtParameter<?>> params = method.getParameters();
			boolean checkParameterType = params.size() > 0 || valueType != null;
			CtTypeReference<?> newValueType = typeMatches(valueType,
					checkParameterType ? params.get(valueParamIdx).getType() : null);
			if (newValueType != null || checkParameterType == false) {
				// the method parameter matches with expected valueType
				if (newValueType != valueType) {
					// there is new value type, we have to update it in mmField
					if (mmField.getValueType() == valueType) {
						mmField.setValueType(newValueType);
					} else if (mmField.getItemValueType() == valueType) {
						mmField.setItemValueType(newValueType);
					} else {
						throw new SpoonException("Cannot update valueType");
					}
				}
				unhandledSignatures.remove(k);
				if (fieldGetter.get() == null) {
					// we do not have method yet for this field
					fieldSetter.accept(method);
				} else {
					// there is already a method for this field
					if (valueType != null) {
						// 1) choose the method with more fitting valueType
						boolean oldMatches = valueType
								.equals(fieldGetter.get().getParameters().get(valueParamIdx).getType());
						boolean newMatches = valueType.equals(method.getParameters().get(valueParamIdx).getType());
						if (oldMatches != newMatches) {
							if (oldMatches) {
								// old is matching
								// add new as unhandled signature
								unhandledSignatures.add(k);
							} else {
								// new is matching
								// add old as unhandled signature
								unhandledSignatures.add(fieldGetter.get().getSignature());
								// and set new is current field value
								fieldSetter.accept(method);
							}
							// do report problem. The conflict was resolved
							return;
						}
					} else if (params.isEmpty()) {
						// the value type is not known yet and there is no input
						// parameter. We are resolving getter field.
						// choose the getter whose return value is a collection
						// of second one
						CtTypeReference<?> oldReturnType = fieldGetter.get().getType();
						CtTypeReference<?> newReturnType = method.getType();
						boolean isOldIterable = oldReturnType.isSubtypeOf(iterableRef);
						boolean isNewIterable = newReturnType.isSubtypeOf(iterableRef);
						if (isOldIterable != isNewIterable) {
							// they are not some. Only one of them is iterable
							if (isOldIterable) {
								if (isIterableOf(oldReturnType, newReturnType)) {
									// use old method, which is multivalue
									// represantation of new method
									// OK - no conflict. Finish
									return;
								}
							} else {
								if (isIterableOf(newReturnType, oldReturnType)) {
									// use new method, which is multivalue
									// represantation of old method
									fieldSetter.accept(method);
									// OK - no conflict. Finish
									return;
								}
							}
							// else report ambiguity
						}
					}

					problems.write(mmField.ownerType.getName() + " has ambiguous " + fieldName + " :").writeln().incTab()
							.write(fieldGetter.get().getType() + "#" + getDeclaringTypeSignature(fieldGetter.get()))
							.writeln().write(method.getType() + "#" + getDeclaringTypeSignature(method)).writeln()
							.decTab();
				}
			}
		};
	}

	/**
	 * @param methods
	 * @return first non abstract method from the list. If there is none then it returns first method
	 */
	private CtMethod<?> getConcreteMethod(List<CtMethod<?>> methods) {
		for (CtMethod<?> method : methods) {
			if (method.getBody() != null) {
				return method;
			}
		}
		return methods.get(0);
	}

	/**
	 * @param valueType
	 * @return item type of the valueType. If valueType is not a
	 *         {@link Iterable} then return null
	 */
	private CtTypeReference<?> getItemValueType(CtTypeReference<?> valueType) {
		if (valueType != null) {
			if (valueType.isSubtypeOf(iterableRef) && valueType.getActualTypeArguments().size() == 1) {
				return valueType.getActualTypeArguments().get(0);
			} else if (valueType.isSubtypeOf(mapRef) && valueType.getActualTypeArguments().size() == 2) {
				return valueType.getActualTypeArguments().get(1);
			}
		}
		return null;
	}

	private Set<String> propertyGetterAndSetter = new HashSet<>(
			Arrays.asList(PropertyGetter.class.getName(), PropertySetter.class.getName()));

	private void addFieldsOfType(MMType mmType, CtType<?> ctType) {
		ctType.getTypeMembers().forEach(typeMember -> {
			if (typeMember instanceof CtMethod<?>) {
				CtMethod<?> method = (CtMethod<?>) typeMember;
				CtRole role = getRoleOfMethod(method);
				if (role != null) {
					MMField field = mmType.getOrCreateMMField(role);
					field.addMethod(method);
				} else {
					mmType.otherMethods.add(method);
				}
			}
		});
		addFieldsOfSuperType(mmType, ctType.getSuperclass());
		ctType.getSuperInterfaces().forEach(superIfaceRef -> addFieldsOfSuperType(mmType, superIfaceRef));
	}

	private static Set<String> EXPECTED_TYPES_NOT_IN_CLASSPATH = new HashSet<>(Arrays.asList(
			"java.lang.Cloneable",
			"spoon.processing.FactoryAccessor",
			"spoon.reflect.visitor.CtVisitable",
			"spoon.reflect.visitor.chain.CtQueryable",
			"spoon.template.TemplateParameter",
			"java.lang.Iterable"));
		
	
	/**
	 * add all fields of `superTypeRef` into `mmType`
	 * @param mmType sub type
	 * @param superTypeRef super type
	 */
	private void addFieldsOfSuperType(MMType mmType, CtTypeReference<?> superTypeRef) {
		if (superTypeRef == null) {
			return;
		}
		CtType<?> superType = superTypeRef.getDeclaration();
		if(superType == null) {
			if(EXPECTED_TYPES_NOT_IN_CLASSPATH.contains(superTypeRef.getQualifiedName()) == false) {
				problems.write("Type not in model: " + superTypeRef.getQualifiedName()).writeln();
			}
			return;
		}
		MMType superMMType = getOrCreateMMType(superType);
		if (superMMType != mmType) {
			mmType.addSuperType(superMMType);
		}
	}

	/**
	 * @param type
	 * @return name of {@link MMType}, which represents Spoon model {@link CtType}
	 */
	public static String getMMTypeIntefaceName(CtType<?> type) {
		String name = type.getSimpleName();
		if (name.endsWith(CLASS_SUFFIX)) {
			name = name.substring(0, name.length() - CLASS_SUFFIX.length());
		}
		return name;
	}

	static <K, V> V getOrCreate(Map<K, V> map, K key, Supplier<V> valueCreator) {
		V value = map.get(key);
		if (value == null) {
			value = valueCreator.get();
			map.put(key, value);
		}
		return value;
	}
	static <T> boolean addUniqueObject(Collection<T> col, T o) {
		if (containsObject(col, o)) {
			return false;
		}
		col.add(o);
		return true;
	}
	static boolean containsObject(Iterable<? extends Object> iter, Object o) {
		for (Object object : iter) {
			if (object == o) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param iface
	 * @return {@link CtClass} of Spoon model which implements the spoon model interface. null if there is no implementation.
	 */
	public static CtClass<?> getImplementationOfInterface(CtInterface<?> iface) {
		String impl = iface.getQualifiedName().replace("spoon.reflect", "spoon.support.reflect") + CLASS_SUFFIX;
		return (CtClass<?>) iface.getFactory().Type().get(impl);
	}

	/**
	 * @param impl
	 * @return {@link CtInterface} of Spoon model which represents API of the spoon model class. null if there is no implementation.
	 */
	public static CtInterface<?> getInterfaceOfImplementation(CtClass<?> impl) {
		String iface = impl.getQualifiedName();
		if (iface.endsWith(CLASS_SUFFIX) == false || iface.startsWith("spoon.support.reflect.") == false) {
			throw new SpoonException("Unexpected spoon model implementation class: " + impl.getQualifiedName());
		}
		iface = iface.substring(0, iface.length() - CLASS_SUFFIX.length());
		iface = iface.replace("spoon.support.reflect", "spoon.reflect");
		return (CtInterface<?>) impl.getFactory().Type().get(iface);
	}

	/**
	 * @param method to be checked method
	 * @return {@link CtRole} of spoon model method. Looking into all super class/interface implementations of this method
	 */
	public static CtRole getRoleOfMethod(CtMethod<?> method) {
		Factory f = method.getFactory();
		CtAnnotation<PropertyGetter> getter = getInheritedAnnotation(method, f.createCtTypeReference(PropertyGetter.class));
		if (getter != null) {
			return getter.getActualAnnotation().role();
		}
		CtAnnotation<PropertySetter> setter = getInheritedAnnotation(method, f.createCtTypeReference(PropertySetter.class));
		if (setter != null) {
			return setter.getActualAnnotation().role();
		}
		return null;
	}

	/**
	 * Looks for method in superClass and superInterface hierarchy for the method with required annotationType
	 * @param method
	 * @param annotationType
	 * @return
	 */
	private static <A extends Annotation> CtAnnotation<A> getInheritedAnnotation(CtMethod<?> method, CtTypeReference<A> annotationType) {
		CtAnnotation<A> annotation = method.getAnnotation(annotationType);
		if (annotation == null) {
			CtType<?> declType = method.getDeclaringType();
			final ClassTypingContext ctc = new ClassTypingContext(declType);
			annotation = declType.map(new AllTypeMembersFunction(CtMethod.class)).map((CtMethod<?> currentMethod) -> {
				if (method == currentMethod) {
					return null;
				}
				if (ctc.isSameSignature(method, currentMethod)) {
					CtAnnotation<A> annotation2 = currentMethod.getAnnotation(annotationType);
					if (annotation2 != null) {
						return annotation2;
					}
				}
				return null;
			}).first();
		}
		return annotation;
	}
	private String getDeclaringTypeSignature(CtMethod m) {
		return m.getDeclaringType().getSimpleName() + "#" + m.getSignature();
	}
	/**
	 * Checks whether expectedType and realType are matching.
	 *
	 * @param expectedType
	 * @param realType
	 * @return new expectedType or null if it is not matching
	 */
	private CtTypeReference<?> typeMatches(CtTypeReference<?> expectedType, CtTypeReference<?> realType) {
		if (expectedType == null) {
			return realType;
		}
		if (expectedType.getTypeErasure().equals(realType.getTypeErasure())) {
			return expectedType;
		}
		if (expectedType.getSimpleName().equals("CtBlock") && statementRef.equals(realType.getTypeErasure())) {
			/*
			 * These are compatible setters too CtBlock CtCatch#get() void
			 * CtCatch#set(CtStatement)
			 */
			return statementRef;
		}
		if (expectedType.isSubtypeOf(realType)) {
			/*
			 * CtFieldReference<T> CtFieldAccess#getVariable() CtFieldAccess
			 * inherits from CtVariableAccess which has
			 * #setVariable(CtVariableReference<T>) it is OK to use expected
			 * type CtFieldReference<T>, when setter has CtVariableReference<T>
			 */
			return expectedType;
		}
		return null;
	}
	/**
	 * @param iterableType
	 * @param itemType
	 * @return true if itemType can be iterated by iterableType
	 */
	private boolean isIterableOf(CtTypeReference<?> iterableType, CtTypeReference<?> itemType) {
		if (iterableType.getActualTypeArguments().size() == 1) {
			CtTypeReference<?> itemTypeOfIterable = iterableType.getActualTypeArguments().get(0);
			return itemType.isSubtypeOf(itemTypeOfIterable);
		}
		return false;
	}
	private static <V> void forEachSortedEntry(Map<String, V> map, BiConsumer<String, V> consumer) {
		List<String> sortedKeys = new ArrayList<>(map.keySet());
		sortedKeys.sort((a, b) -> a.compareTo(b));
		for (String key : sortedKeys) {
			consumer.accept(key, map.get(key));
		}
	}

	private static <V> void forEachSorted(Collection<String> iter, Consumer<String> consumer) {
		List<String> sortedKeys = new ArrayList<>(iter);
		sortedKeys.sort((a, b) -> a.compareTo(b));
		for (String key : sortedKeys) {
			consumer.accept(key);
		}
	}

	public Collection<MMType> getMMTypes() {
		return Collections.unmodifiableCollection(name2mmType.values());
	}
}
