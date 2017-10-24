/*
 * Copyright (C) 2006-2015 INRIA and contributors
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

package spoon.reflect.visitor;

import org.junit.Test;
import spoon.Launcher;
import spoon.metamodel.MMType;
import spoon.metamodel.MMTypeKind;
import spoon.metamodel.SpoonMetaModel;
import spoon.reflect.annotations.PropertyGetter;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.AllTypeMembersFunction;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.reflect.visitor.processors.CheckScannerTestProcessor;
import spoon.support.visitor.ClassTypingContext;
import spoon.test.SpoonTestHelpers;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static spoon.test.SpoonTestHelpers.isMetamodelProperty;

public class CtScannerTest {
	@Test
	public void testScannerContract() throws Exception {
		// contract: CtScanner must call enter and exit methods in each visit methods.
		final Launcher launcher = new Launcher();
		launcher.setArgs(new String[] {"--output-type", "nooutput" });
		launcher.getEnvironment().setNoClasspath(true);
		// interfaces.
		launcher.addInputResource("./src/main/java/spoon/reflect/code");
		launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
		launcher.addInputResource("./src/main/java/spoon/reflect/reference");
		// implementations.
		launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
		launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
		launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
		launcher.addInputResource("./src/main/java/spoon/reflect/visitor/CtScanner.java");
		launcher.buildModel();

		launcher.getModel().processWith(new CheckScannerTestProcessor());
	}

	class SimpleSignature extends CtScanner {
		String signature = "";
		@Override
		public <T> void visitCtParameter(CtParameter<T> parameter) {
			signature += parameter.getType().getQualifiedName()+", ";
			super.visitCtParameter(parameter);
		}

		@Override
		public <T> void visitCtMethod(CtMethod<T> m) {
			signature += m.getSimpleName()+"(";
			super.visitCtMethod(m);
			signature += ")";
		}
	}


	class SimpleSignatureComparator implements Comparator<CtMethod<?>> {
		@Override
		public int compare(CtMethod<?> o1, CtMethod<?> o2) {
			return computeSimpleSignature(o1).compareTo(computeSimpleSignature(o2));
		}
	}

	private String computeSimpleSignature(CtMethod<?> m) {
		SimpleSignature sc1 = new SimpleSignature();
		sc1.visitCtMethod(m);
		return sc1.signature;
	}

	@Test
	public void testScannerCallsAllProperties() throws Exception {
		// contract: CtScanner must visit all metamodel properties and use correct CtRole
		final Launcher launcher = new Launcher();
		launcher.addInputResource("./src/main/java/spoon/reflect/");
		launcher.run();

		CtTypeReference<?> ctElementRef = launcher.getFactory().createCtTypeReference(CtElement.class);

		CtClass<?> scanner = (CtClass<?>)launcher.getFactory().Type().get(CtScanner.class);

		List<String> problems = new ArrayList<>();
		Set<String> ignoredInvocations = new HashSet(Arrays.asList("scan", "enter", "exit"));

		SpoonMetaModel metaModel = new SpoonMetaModel();

		Map<String, CtMethod<?>> scannerVisitMethodsByName = new HashMap<>();
		scanner.getAllMethods().forEach(m -> {
			if(m.getSimpleName().startsWith("visit")) {
				scannerVisitMethodsByName.put(m.getSimpleName(), m);
			}
		});

		for (MMType mmType : metaModel.getMMTypes()) {
			if (mmType.getKind() != MMTypeKind.LEAF) {
				continue;
			}
			//check only LEAF Classes
			CtMethod<?> visitMethod = scannerVisitMethodsByName.remove("visit"+mmType.getName());
			assertNotNull("CtScanner#" + "visit"+mmType.getName() + "(...) not found", visitMethod);
			Set<String> calledMethods = new HashSet<>();
			Set<String> checkedMethods = new HashSet<>();
			mmType.getRole2field().forEach((role, mmField) -> {
				if (mmField.isDerived()) {
					//ignore derived fields
					return;
				}
				if (mmField.getItemValueType().isSubtypeOf(ctElementRef) == false) {
					//ignore fields, which doesn't return CtElement
					return;
				}
				CtMethod<?> m = mmField.get;
				checkedMethods.add(m.getSignature());
				//System.out.println("checking "+m.getSignature() +" in "+visitMethod.getSignature());
				CtInvocation invocation = visitMethod.filterChildren(new TypeFilter<CtInvocation>(CtInvocation.class) {
					@Override
					public boolean matches(CtInvocation element) {
						if(ignoredInvocations.contains(element.getExecutable().getSimpleName())) {
							return false;
						}
						calledMethods.add(element.getExecutable().getSignature());
						return super.matches(element) && element.getExecutable().getSimpleName().equals(m.getSimpleName());
					}
				}).first();
				if(m.getSimpleName().equals("getComments")) {
					//ignore missing getComments ... until discussion about how to do it is finished
					return;
				}
				//check the invocation of that method is there
				if (invocation == null) {
					problems.add("no "+m.getSignature() +" in "+visitMethod);
				}
			});
			calledMethods.removeAll(checkedMethods);
			if (calledMethods.size() > 0) {
				problems.add("CtScanner " + visitMethod.getPosition() + " calls unexpected methods: "+calledMethods);
			}
		}
		if(scannerVisitMethodsByName.isEmpty() == false) {
			problems.add("These CtScanner visit methods were not checked: " + scannerVisitMethodsByName.keySet());
		}
		if(problems.size()>0) {
			fail(String.join("\n", problems));
		}
	}

	private String getInterfaceName(String simpleName) {
		if (simpleName.endsWith("Impl")) {
			simpleName = simpleName.substring(0, simpleName.length()-4);
		}
		return simpleName;
	}

	private CtFieldRead<?> getRoleOfInvocation(CtInvocation<?> inv) {
		Factory f = inv.getFactory();
		CtAnnotation<PropertyGetter> annotation = getInheritedAnnotation((CtMethod<?>) inv.getExecutable().getDeclaration(), f.createCtTypeReference(PropertyGetter.class));

		if (annotation == null) {
			this.getClass();
		}
		CtFieldRead<?> role = annotation.getValue("role");
		return role;
	}

	/**
	 * Looks for method in superClass and superInterface hierarchy for the method with required annotationType
	 * @param method
	 * @param annotationType
	 * @return
	 */
	private <A extends Annotation> CtAnnotation<A> getInheritedAnnotation(CtMethod<?> method, CtTypeReference<A> annotationType) {
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

}
