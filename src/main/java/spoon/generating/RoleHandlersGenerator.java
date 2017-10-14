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
package spoon.generating;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import spoon.SpoonException;
import spoon.processing.AbstractManualProcessor;
import spoon.reflect.annotations.PropertyGetter;
import spoon.reflect.annotations.PropertySetter;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtTypeParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtWildcardReference;
import spoon.reflect.visitor.PrinterHelper;
import spoon.reflect.visitor.chain.CtConsumer;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.template.Substitution;

public class RoleHandlersGenerator extends AbstractManualProcessor {
	public static final String TARGET_PACKAGE = "spoon.reflect.meta.impl";

	class RoleIntefaceMethods {
		CtRole role;
		CtType<?> iface;
		// own methods with PropertyGetter or PropertySetter annotation
		List<CtMethod<?>> roleMethods = new ArrayList<>();
		// own and inherited methods grouped by method signature and the methods
		// in list ordered by own methods are first
		Map<String, List<CtMethod<?>>> allRoleMethodsBySignature = new HashMap<>();
		CtMethod<?> get;
		CtMethod<?> set;
		CtMethod<?> add;
		CtMethod<?> addFirst;
		CtMethod<?> addLast;
		CtMethod<?> addOn;
		CtMethod<?> remove;
		CtTypeReference<?> valueType;
		CtTypeReference<?> itemValueType;
		List<RoleIntefaceMethods> superTypes = new ArrayList<>();

		RoleIntefaceMethods(CtRole role, CtType<?> iface) {
			super();
			this.role = role;
			this.iface = iface;
		}

		void addRoleMethod(CtMethod m) {
			roleMethods.add(m);
			List<CtMethod<?>> methods = getOrCreate(allRoleMethodsBySignature, m.getSignature(),
					() -> new ArrayList<>());
			addUniqueObject(methods, m);
		}

		void addSuperType(RoleIntefaceMethods superTypeRIM) {
			if (addUniqueObject(superTypes, superTypeRIM)) {
				for (Map.Entry<String, List<CtMethod<?>>> e : superTypeRIM.allRoleMethodsBySignature.entrySet()) {
					getOrCreate(allRoleMethodsBySignature, e.getKey(), () -> new ArrayList<>()).addAll(e.getValue());
				}
			}
		}

		String getHandlerName() {
			String typeName = iface.getSimpleName();
			return typeName + "_" + role.name() + "_RoleHandler";
		}

		@Override
		public String toString() {
			return getHandlerName() + "<" + valueType + ">";
		}

		public String getRoleHandlerSuperTypeQName() {
			if ("List".equals(valueType.getSimpleName())) {
				return "spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler";
			} else if ("Set".equals(valueType.getSimpleName())) {
				return "spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler";
			} else if ("Map".equals(valueType.getSimpleName())) {
				return "spoon.reflect.meta.impl.AbstractRoleHandler.MapHandler";
			} else {
				return "spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler";
			}
		}
	}

	// Class[] helperIfaces = new Class[]{CtBodyHolder.class,
	// CtTypeInformation.class, CtNamedElement.class};
	PrinterHelper problems;
	Map<String, CtMethod<?>> allUnhandledMethodSignatures = new HashMap<>();
	Map<String, RoleIntefaceMethods> methodsByTypeRoleHandler = new HashMap<>();
	CtTypeReference<?> iterableRef;
	CtTypeReference<?> mapRef;
	CtTypeReference<?> statementRef;

	@Override
	public void process() {
		iterableRef = getFactory().createCtTypeReference(Iterable.class);
		mapRef = getFactory().createCtTypeReference(Map.class);
		statementRef = getFactory().createCtTypeReference(CtStatement.class);
		problems = new PrinterHelper(getFactory().getEnvironment());
		Map<CtType, Map<CtRole, RoleIntefaceMethods>> loadedInterfaces = new HashMap<>();
		//process all types
		getFactory().getModel().getRootPackage().filterChildren(new TypeFilter<>(CtInterface.class))
			.forEach((CtType t) -> {
				collectMethodsOfType(loadedInterfaces, t);
			});

		if (allUnhandledMethodSignatures.size() > 0) {
			problems.write("Unhandled method signatures:").writeln();
			problems.incTab();
			forEachSortedEntry(allUnhandledMethodSignatures, (k, v) -> problems.write(getDeclaringTypeSignature(v)).writeln());
			problems.decTab();
		}


		Set<CtRole> unhandledRoles = new HashSet<>(Arrays.asList(CtRole.values()));
		loadedInterfaces.values().forEach(map -> unhandledRoles.removeAll(map.keySet()));

		List<String> unhandledRoleNames = new ArrayList();
		unhandledRoles.forEach(it -> unhandledRoleNames.add(it.name()));
		forEachSorted(unhandledRoleNames, role -> {
			problems.write("Unused CtRole." + role + " found").writeln();
		});
		String report = problems.toString();
		try (Writer w = new OutputStreamWriter(new FileOutputStream(file("target/report/problems.txt")))) {
			w.write(report);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		List<RoleIntefaceMethods> superRIMs = new ArrayList<>();

		loadedInterfaces.forEach((type, roleToRIM) -> {
			roleToRIM.forEach((role, rim) -> {
				RoleIntefaceMethods superRIM = getSuperRIM(rim);
				addUniqueObject(superRIMs, superRIM);
			});
		});

		superRIMs.sort((a, b) -> {
			int d = a.role.ordinal() - b.role.ordinal();
			if (d != 0) {
				return d;
			}
			return a.iface.getSimpleName().compareTo(b.iface.getSimpleName());
		});
		PrinterHelper concept = new PrinterHelper(getFactory().getEnvironment());
		superRIMs.forEach(rim -> {
			concept.write(rim.iface.getSimpleName() + " CtRole." + rim.role.name()).writeln().incTab()
			.write("ItemType: ").write(rim.valueType.toString()).writeln();
			if (rim.get != null) {
				concept.write("get: ").write(rim.get.getSignature()).write(" : ").write(rim.get.getType().toString()).writeln();
			}
			if (rim.set != null) {
				concept.write("set: ").write(rim.set.getSignature()).writeln();
			}
			if (rim.add != null) {
				concept.write("add: ").write(rim.add.getSignature()).writeln();
			}
			if (rim.addFirst != null) {
				concept.write("addFirst: ").write(rim.addFirst.getSignature()).writeln();
			}
			if (rim.addLast != null) {
				concept.write("addLast: ").write(rim.addLast.getSignature()).writeln();
			}
			if (rim.addOn != null) {
				concept.write("addOn: ").write(rim.addOn.getSignature()).writeln();
			}
			if (rim.remove != null) {
				concept.write("remove: ").write(rim.remove.getSignature()).writeln();
			}
			concept.decTab();
			concept.write("----------------------------------------------------------").writeln();
		});
		try (Writer w = new OutputStreamWriter(new FileOutputStream(file("target/report/concept.txt")))) {
			w.write(concept.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		CtType<?> template = getTemplate("spoon.generating.meta.ModelRoleHandlerTemplate");

		CtClass<?> modelRoleHandlersClass = Substitution.createTypeFromTemplate(
				TARGET_PACKAGE + ".ModelRoleHandlers",
				template,
				new HashMap<>());
		CtNewArray<?> roleHandlersFieldExpr = (CtNewArray<?>) modelRoleHandlersClass.getField("roleHandlers").getDefaultExpression();
		superRIMs.forEach(rim -> {
			Map<String, Object> params = new HashMap<>();
			params.put("$getterName$", rim.get.getSimpleName());
			if (rim.set != null) {
				params.put("$setterName$", rim.set.getSimpleName());
			}
			params.put("$Role$", getFactory().Type().createReference(CtRole.class));
			params.put("ROLE", rim.role.name());
			params.put("$TargetType$", rim.iface.getReference());
//			params.put("AbstractHandler", getFactory().Type().createReference("spoon.reflect.meta.impl.AbstractRoleHandler"));
			params.put("AbstractHandler", rim.getRoleHandlerSuperTypeQName());
			params.put("Node", rim.iface.getReference());
			params.put("ValueType", fixMainValueType(rim.getRoleHandlerSuperTypeQName().endsWith("SingleHandler") ? rim.valueType : rim.itemValueType));
			CtClass<?> modelRoleHandlerClass = Substitution.createTypeFromTemplate(
					rim.getHandlerName(),
					getTemplate("spoon.generating.meta.RoleHandlerTemplate"),
					params);
			if (rim.set == null) {
				modelRoleHandlerClass.getMethodsByName("setValue").forEach(m -> m.delete());
			}
			modelRoleHandlerClass.addModifier(ModifierKind.STATIC);
			modelRoleHandlersClass.addNestedType(modelRoleHandlerClass);
			roleHandlersFieldExpr.addElement(getFactory().createCodeSnippetExpression("new " + modelRoleHandlerClass.getSimpleName() + "()"));
		});
	}

	private CtTypeReference<?> fixMainValueType(CtTypeReference<?> valueType) {
		valueType = fixValueType(valueType);
		if (valueType instanceof CtWildcardReference) {
			return getFactory().Type().OBJECT;
		}
		return valueType;
	}
	private CtTypeReference<?> fixValueType(CtTypeReference<?> valueType) {
		if (valueType == null) {
			this.getClass();
		}
		valueType = valueType.clone();
		if (valueType instanceof CtTypeParameterReference) {
			if (valueType instanceof CtWildcardReference) {
				CtTypeReference<?> boundingType = ((CtTypeParameterReference) valueType).getBoundingType();
				if (boundingType instanceof CtTypeParameterReference) {
					((CtTypeParameterReference) valueType).setBoundingType(null);
				}
				return valueType;
			}
			CtTypeParameterReference tpr = (CtTypeParameterReference) valueType;
			return getFactory().createWildcardReference();
		}
		for (int i = 0; i < valueType.getActualTypeArguments().size(); i++) {
			valueType.getActualTypeArguments().set(i, fixValueType(valueType.getActualTypeArguments().get(i)));
		}
		valueType = valueType.box();
		return valueType;
	}

	private CtType<?> getTemplate(String templateQName) {
		CtType<?> template = getFactory().Class().get(templateQName);
		return template;
	}

	private File file(String name) {
		File f = new File(name);
		f.getParentFile().mkdirs();
		return f;
	}

	private RoleIntefaceMethods getSuperRIM(RoleIntefaceMethods rim) {
		List<RoleIntefaceMethods> superRIMs = new ArrayList<>();
		if (rim.roleMethods.size() > 0) {
			superRIMs.add(rim);
		}
		rim.superTypes.forEach(superRim -> {
			addUniqueObject(superRIMs, getSuperRIM(superRim));
		});
		int idx = 0;
		if (superRIMs.size() > 1) {
			CtTypeReference<?> requiredValueType = superRIMs.get(0).valueType;
			for (int i = 1; i < superRIMs.size(); i++) {
				RoleIntefaceMethods superRIM = superRIMs.get(i);
				if (superRIM.valueType.equals(requiredValueType) == false) {
					break;
				}
				idx = i;
			}
		}
		return superRIMs.get(idx);
	}

	private Set<String> propertyGetterAndSetter = new HashSet<>(
			Arrays.asList(PropertyGetter.class.getName(), PropertySetter.class.getName()));

	/**
	 * @param type
	 *            the analuzed `type`
	 * @return Map of RoleIntefaceMethods of `type` for each method annotated
	 *         with PropertyGetter or PropertySetter in this type or any super
	 *         interface
	 */
	private Map<CtRole, RoleIntefaceMethods> collectMethodsOfType(
			Map<CtType, Map<CtRole, RoleIntefaceMethods>> loadedInterfaces, CtType type) {
		// get cached or create new map of RoleIntefaceMethods of `type`
		return getOrCreate(loadedInterfaces, type, () -> {
			// if ("CtCatch".equals(type.getSimpleName())) {
			// this.getClass();
			// }
			// collect all RoleIntefaceMethods of `type`
			Map<CtRole, RoleIntefaceMethods> rimByRole = new HashMap<>();
			type.filterChildren(new TypeFilter<>(CtAnnotation.class))
					.select((CtAnnotation a) -> propertyGetterAndSetter.contains(a.getType().getQualifiedName()))
					.map((CtAnnotation<?> a, CtConsumer<Object> out) -> {
						CtRole role;
						Annotation annotation = a.getActualAnnotation();
						if (annotation instanceof PropertyGetter) {
							role = ((PropertyGetter) annotation).role();
						} else {
							role = ((PropertySetter) annotation).role();
						}
						CtMethod m = (CtMethod) a.getParent();
						// get or create method holder for the type and role
						RoleIntefaceMethods rim = getOrCreate(rimByRole, role,
								() -> new RoleIntefaceMethods(role, type));
						rim.addRoleMethod(m);
					}).list();
			// for each super interface, collect all RoleIntefaceMethods and
			// link them to RoleIntefaceMethods of `type`
			type.getSuperInterfaces().forEach(superIfaceRef -> {
				CtType superIFace = superIfaceRef.getTypeDeclaration();
				Map<CtRole, RoleIntefaceMethods> superRimsByRole = collectMethodsOfType(loadedInterfaces, superIFace);
				for (RoleIntefaceMethods superRim : superRimsByRole.values()) {
					// get or create RoleIntefaceMethods in this type for the
					// super type role
					RoleIntefaceMethods rim = getOrCreate(rimByRole, superRim.role,
							() -> new RoleIntefaceMethods(superRim.role, type));
					// and add it
					rim.addSuperType(superRim);
				}
			});
			// collect getters, setters, ...
			for (RoleIntefaceMethods rim : rimByRole.values()) {
				Set<String> unhandledSignatures = new HashSet<>(rim.allRoleMethodsBySignature.keySet());
				// look for getter
				forEachValueWithKeyPrefix(rim.allRoleMethodsBySignature, new String[] { "get", "is" }, (k, v) -> {
					if (v.get(0).getParameters().isEmpty() == false) {
						// ignore getters with some parameters
						return;
					}
					createFieldHandler(unhandledSignatures, rim, null, -1, () -> rim.get, (m) -> rim.get = m, "get()")
							.accept(k, v);
				});

				rim.valueType = rim.get == null ? null : rim.get.getType();
				if (rim.valueType instanceof CtTypeParameterReference) {
					rim.valueType = ((CtTypeParameterReference) rim.valueType).getBoundingType();
				}
				if (rim.valueType == null) {
					problems.write("Missing getter for " + rim.iface.getSimpleName() + " and CtRole." + rim.role)
							.writeln();
				}
				// look for setter
				forEachValueWithKeyPrefix(rim.allRoleMethodsBySignature, "set", (k, v) -> {
					if (v.get(0).getParameters().size() == 1) {
						// setters with 1 parameter equal to getter return value
						createFieldHandler(unhandledSignatures, rim, rim.valueType, 0, () -> rim.set,
								(m) -> rim.set = m, "set(value)").accept(k, v);
						return;
					}
				});
				if (rim.set == null) {
					problems.write("Missing setter for " + rim.iface.getSimpleName() + " and CtRole." + rim.role)
							.writeln();
				}

				rim.itemValueType = getItemValueType(rim.valueType);
				if (rim.itemValueType != null) {
					forEachValueWithKeyPrefix(rim.allRoleMethodsBySignature, new String[] { "add", "insert" },
							(k, v) -> {
								if (v.get(0).getParameters().size() == 1) {
									if (v.get(0).getSimpleName().endsWith("AtTop")
											|| v.get(0).getSimpleName().endsWith("Begin")) {
										// adders with 1 parameter and fitting
										// value type
										createFieldHandler(unhandledSignatures, rim, rim.itemValueType, 0,
												() -> rim.addFirst, (m) -> rim.addFirst = m, "addFirst(value)")
														.accept(k, v);
										return;
									} else if (v.get(0).getSimpleName().endsWith("AtBottom")
											|| v.get(0).getSimpleName().endsWith("End")) {
										// adders with 1 parameter and fitting
										// value type
										createFieldHandler(unhandledSignatures, rim, rim.itemValueType, 0,
												() -> rim.addLast, (m) -> rim.addLast = m, "addLast(value)").accept(k,
														v);
										return;
									} else {
										// adders with 1 parameter and fitting
										// value type
										createFieldHandler(unhandledSignatures, rim, rim.itemValueType, 0,
												() -> rim.add, (m) -> rim.add = m, "add(value)").accept(k, v);
										return;
									}
								}
								if (v.get(0).getParameters().size() == 2) {
									// adders with 2 parameters. First int
									if (v.get(0).getParameters().get(0).getType().getSimpleName().equals("int")) {
										createFieldHandler(unhandledSignatures, rim, rim.itemValueType, 1,
												() -> rim.addOn, (m) -> rim.addOn = m, "addOn(int, value)").accept(k,
														v);
									}
									return;
								}
							});
					forEachValueWithKeyPrefix(rim.allRoleMethodsBySignature, "remove", (k, v) -> {
						if (v.get(0).getParameters().size() == 1) {
							createFieldHandler(unhandledSignatures, rim, rim.itemValueType, 0, () -> rim.remove,
									(m) -> rim.remove = m, "remove(value)").accept(k, v);
						}
					});
				}
				unhandledSignatures.forEach(key -> {
					CtMethod<?> representant = rim.allRoleMethodsBySignature.get(key).get(0);
					getOrCreate(allUnhandledMethodSignatures, getDeclaringTypeSignature(representant),
							() -> representant);
				});
			}
			return rimByRole;
		});
	}

	private BiConsumer<String, List<CtMethod<?>>> createFieldHandler(Set<String> unhandledSignatures,
			RoleIntefaceMethods rim, CtTypeReference valueType, int valueParamIdx, Supplier<CtMethod<?>> fieldGetter,
			Consumer<CtMethod<?>> fieldSetter, String fieldName) {
		return (k, v) -> {
			// use getTypeErasure() comparison, because some getters and setters
			// does not fit exactly
			List<CtParameter<?>> params = v.get(0).getParameters();
			boolean checkParameterType = params.size() > 0 || valueType != null;
			CtTypeReference<?> newValueType = typeMatches(valueType,
					checkParameterType ? params.get(valueParamIdx).getType() : null);
			if (newValueType != null || checkParameterType == false) {
				// the method parameter matches with expected valueType
				if (newValueType != valueType) {
					// there is new value type, we have to update it in rim
					if (rim.valueType == valueType) {
						rim.valueType = newValueType;
					} else if (rim.itemValueType == valueType) {
						rim.itemValueType = newValueType;
					} else {
						throw new SpoonException("Cannot update valueType");
					}
				}
				unhandledSignatures.remove(k);
				if (fieldGetter.get() == null) {
					// we do not have method yet for this field
					fieldSetter.accept(v.get(0));
				} else {
					// there is already a method for this field
					if (valueType != null) {
						// 1) choose the method with more fitting valueType
						boolean oldMatches = valueType
								.equals(fieldGetter.get().getParameters().get(valueParamIdx).getType());
						boolean newMatches = valueType.equals(v.get(0).getParameters().get(valueParamIdx).getType());
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
								fieldSetter.accept(v.get(0));
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
						CtTypeReference<?> newReturnType = v.get(0).getType();
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
									fieldSetter.accept(v.get(0));
									// OK - no conflict. Finish
									return;
								}
							}
							// else report ambiguity
						}
					}

					problems.write(rim.iface.getSimpleName() + " has ambiguous " + fieldName + " :").writeln().incTab()
							.write(fieldGetter.get().getType() + "#" + getDeclaringTypeSignature(fieldGetter.get()))
							.writeln().write(v.get(0).getType() + "#" + getDeclaringTypeSignature(v.get(0))).writeln()
							.decTab();
				}
			}
		};
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

	private String getDeclaringTypeSignature(CtMethod m) {
		return m.getDeclaringType().getSimpleName() + "#" + m.getSignature();
	}

	private <K, V> V getOrCreate(Map<K, V> map, K key, Supplier<V> valueCreator) {
		V value = map.get(key);
		if (value == null) {
			value = valueCreator.get();
			map.put(key, value);
		}
		return value;
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

	private <V> void forEachSortedEntry(Map<String, V> map, BiConsumer<String, V> consumer) {
		List<String> sortedKeys = new ArrayList<>(map.keySet());
		sortedKeys.sort((a, b) -> a.compareTo(b));
		for (String key : sortedKeys) {
			consumer.accept(key, map.get(key));
		}
	}

	private <V> void forEachSorted(Collection<String> iter, Consumer<String> consumer) {
		List<String> sortedKeys = new ArrayList<>(iter);
		sortedKeys.sort((a, b) -> a.compareTo(b));
		for (String key : sortedKeys) {
			consumer.accept(key);
		}
	}

	private static boolean containsObject(Iterable<? extends Object> iter, Object o) {
		for (Object object : iter) {
			if (object == o) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param col
	 * @param o
	 * @return true if added
	 */
	private <T> boolean addUniqueObject(Collection<T> col, T o) {
		if (containsObject(col, o)) {
			return false;
		}
		col.add(o);
		return true;
	}
}
