package spoon.test.parent;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import spoon.SpoonException;
import spoon.reflect.CtModelImpl;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ParentNotInitializedException;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtVisitable;
import spoon.support.UnsettableProperty;
import spoon.test.SpoonTestHelpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static spoon.test.parent.ParentContractTest.createCompatibleObject;
import static spoon.test.parent.ParentContractTest.createReceiverList;
import static spoon.testing.utils.ModelUtils.createFactory;

// contract: setParent does not modifiy the state of the parent
@RunWith(Parameterized.class)
public class SetParentTest<T extends CtVisitable> {

	private static Factory factory = createFactory();

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() throws Exception {
		return createReceiverList();
	}

	@Parameterized.Parameter(0)
	public CtType<?> toTest;

	@Test
	public void testContract() throws Throwable {
		Object o = factory.Core().create((Class<? extends CtElement>) toTest.getActualClass());
		CtMethod<?> setter = factory.Type().get(CtElement.class).getMethodsByName("setParent").get(0);

		Object argument = createCompatibleObject(setter.getParameters().get(0).getType());

		if (!(argument instanceof CtElement)) {
			// is a primitive type or a list
			throw new AssertionError("impossible, setParent always takes an element");
		}
		// we create a fresh object
		CtElement receiver = ((CtElement) o).clone();

		if ("CtClass".equals(toTest.getSimpleName())
				|| "CtInterface".equals(toTest.getSimpleName())
				|| "CtEnum".equals(toTest.getSimpleName())
				|| "CtAnnotationType".equals(toTest.getSimpleName())
				|| "CtPackage".equals(toTest.getSimpleName())
				) {
			// contract: root package is the parent for those classes
			assertTrue(receiver.getParent() instanceof CtModelImpl.CtRootPackage);
		} else {
			// contract: there is no parent before
			try {
				receiver.getParent().hashCode();
				fail(receiver.getParent().getClass().getSimpleName());
			} catch (ParentNotInitializedException normal) {
			}
		}

		Method actualMethod = setter.getReference().getActualMethod();
		CtElement argumentClone = ((CtElement) argument).clone();
		actualMethod.invoke(receiver, new Object[]{argument});

		// contract: the parent has not been changed by a call to setParent on an elemnt
		assertTrue(argument.equals(argumentClone));
		assertFalse(argument == argumentClone);

	}

}
