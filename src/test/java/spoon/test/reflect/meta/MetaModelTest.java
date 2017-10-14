package spoon.test.reflect.meta;

import org.junit.Test;

import spoon.Launcher;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import spoon.reflect.meta.CtRoleHandler;
import spoon.reflect.meta.impl.RoleHandlerProvider;
import spoon.reflect.path.CtRole;
import spoon.template.Parameter;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

public class MetaModelTest {

	@Test
	public void elementAnnotationRoleTest() {
		Launcher launcher = new Launcher();
		Factory factory = launcher.getFactory();
		CtClass<?> type = (CtClass) factory.Core().create(CtClass.class);
		CtAnnotation<?> annotation = factory.Annotation().annotate(type, Parameter.class, "value", "abc");
		
		CtRoleHandler roleHandler = RoleHandlerProvider.getRoleHandler(type.getClass(), CtRole.ANNOTATION);
		assertNotNull(roleHandler);
		assertEquals(CtElement.class, roleHandler.getTargetType());
		assertSame(CtRole.ANNOTATION, roleHandler.getRole());
		assertTrue(roleHandler.isValueList());
		assertEquals(CtAnnotation.class, roleHandler.getValueClass());
		
		Object value = roleHandler.getValue(type);
		assertTrue(value instanceof List<?>);
		assertEquals(1, ((List) value).size());
		assertSame(annotation, ((List) value).get(0));
		
		roleHandler.setValue(type, Collections.emptyList());
		value = roleHandler.getValue(type);
		assertTrue(value instanceof List<?>);
		assertEquals(0, ((List) value).size());

		roleHandler.setValue(type, Collections.singletonList(annotation));
		value = roleHandler.getValue(type);
		assertTrue(value instanceof List<?>);
		assertEquals(1, ((List) value).size());
		assertSame(annotation, ((List) value).get(0));
		
		try {
			roleHandler.setValue(type, annotation);
			fail();
		} catch (ClassCastException e) {
			//OK
		}
	}
}
