package fr.inria.lille.localization;

import fr.inria.lille.commons.spoon.SpoonedProject;
import fr.inria.lille.repair.common.config.NopolContext;
import fr.inria.lille.repair.nopol.SourceLocation;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtIf;
import spoon.reflect.cu.SourcePosition;
import xxl.java.junit.TestCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Super dumb! simply says that all if conditions are suspicious, even if they are not executed!
 */
public class DumbFaultLocalizerImpl implements FaultLocalizer {

	private NopolContext nopolContext = null;
	public DumbFaultLocalizerImpl(NopolContext nopolContext) {
		this.nopolContext = nopolContext;
	}


	/**
	 * using reflection to build the name of all test methods to be run
	 *
	 * @param classOfTestCase
	 * @return
	 */
	protected List<String> getTestMethods(Class classOfTestCase) {
		List<String> methodsNames = new ArrayList<>();
		for (Method method : classOfTestCase.getMethods()) {
			for (Annotation a : method.getAnnotations()) {
				// clever, but requires that junit is in the nopol classpath, similar problem
				//if (m.isAnnotationPresent((Class<? extends Annotation>) urlClassLoader.loadClass("org.junit.Test"))) {

				// so we do name-based
				if (a.annotationType().getCanonicalName().equals("org.junit.Test")) {
					methodsNames.add(method.getName());
				}
			}

			if (isJunit3TestMethod(method) && !methodsNames.contains(method.getName())) {
				methodsNames.add(method.getName());
			}
		}

		return methodsNames;
	}

	private boolean isJunit3TestMethod(Method m) {
		return m.getParameterTypes().length == 0 && m.getName().startsWith("test") && m.getReturnType().equals(Void.TYPE) && Modifier.isPublic(m.getModifiers());
	}


	@Override
	public Map<SourceLocation, List<TestResult>> getTestListPerStatement() {
		SpoonedProject spooner = new SpoonedProject(nopolContext.getProjectSources(), nopolContext);
		final List<SourcePosition> l = new ArrayList<>();
		spooner.process(new AbstractProcessor<CtIf>(){
			@Override
			public void process(CtIf ctIf) {
				l.add(ctIf.getCondition().getPosition());
			}
		});

		Map<SourceLocation, List<TestResult>> countPerSourceLocation = new HashMap<>();

		List<TestResult> res = new ArrayList<>();

		for (String test : nopolContext.getProjectTests()) {
			try {
				URLClassLoader urlClassLoader = new URLClassLoader(nopolContext.getProjectClasspath(), this.getClass().getClassLoader());
				Class klass = urlClassLoader.loadClass(test);

				// does not work, see https://stackoverflow.com/a/29865611
				//for (FrameworkMethod desc : new BlockJUnit4ClassRunner(klass).getChildren()) {

				// so we get the methods ourselves
				// only support basic Junit4
				for (String m : getTestMethods(klass)) {
							res.add(new TestResultImpl(TestCase.from(test, m), false));
				}
		} catch (Exception e) {
				System.out.println(test);
			}
		}
		for(SourcePosition pos : l) {
			SourceLocation loc = new SourceLocation(pos.getCompilationUnit().getMainType().getQualifiedName(), pos.getLine());
			countPerSourceLocation.put(loc, Collections.unmodifiableList(res));
		}
		return countPerSourceLocation;
	}

	@Override
	public List<? extends StatementSourceLocation> getStatements() {
		throw new UnsupportedOperationException();
	}
}
