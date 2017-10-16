package spoon.test.reflect.meta;

import org.junit.Test;

import spoon.Launcher;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import spoon.reflect.meta.RoleHandler;
import spoon.reflect.meta.impl.RoleHandlerProvider;
import spoon.reflect.path.CtRole;
import spoon.template.Parameter;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

public class MetaModelTest {

	@Test
	public void elementAnnotationRoleTest() {
		Launcher launcher = new Launcher();
		Factory factory = launcher.getFactory();
		CtClass<?> type = (CtClass) factory.Core().create(CtClass.class);
		CtAnnotation<?> annotation = factory.Annotation().annotate(type, Parameter.class, "value", "abc");
		
		//check contract of low level RoleHandler
		RoleHandler roleHandler = RoleHandlerProvider.getRoleHandler(type.getClass(), CtRole.ANNOTATION);
		assertNotNull(roleHandler);
		assertEquals(CtElement.class, roleHandler.getTargetType());
		assertSame(CtRole.ANNOTATION, roleHandler.getRole());
		assertTrue(roleHandler.isValueList());
		assertEquals(CtAnnotation.class, roleHandler.getValueClass());

		//check getValueByRole
		List<CtAnnotation<?>> value = type.getValueByRole(CtRole.ANNOTATION);
		assertEquals(value, roleHandler.getValue(type));
		assertEquals(value, type.getValueByRole(CtRole.ANNOTATION));
		assertEquals(1, value.size());
		assertSame(annotation, value.get(0));
		
		//check setValueByRole
		type.setValueByRole(CtRole.ANNOTATION, Collections.emptyList());
		value = type.getValueByRole(CtRole.ANNOTATION);
		assertEquals(0, value.size());

		type.setValueByRole(CtRole.ANNOTATION, Collections.singletonList(annotation));
		value = type.getValueByRole(CtRole.ANNOTATION);
		assertEquals(1, value.size());
		assertSame(annotation, value.get(0));
		
		try {
			//contract value must be a list of annotation. One annotation is not actually OK. This contract might be changed in future
			type.setValueByRole(CtRole.ANNOTATION, annotation);
			fail();
		} catch (ClassCastException e) {
			//OK
		}
	}
}
