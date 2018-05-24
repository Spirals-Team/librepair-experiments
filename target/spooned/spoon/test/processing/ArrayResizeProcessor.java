package spoon.test.processing;


public class ArrayResizeProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtField<?>> {
    public void process(spoon.reflect.declaration.CtField<?> field) {
        if (((field.getDeclaringType()) instanceof spoon.reflect.declaration.CtClass) && ((field.getType()) instanceof spoon.reflect.reference.CtArrayTypeReference)) {
            spoon.template.Template<?> t = new spoon.test.template.ArrayResizeTemplate(field, 10);
            t.apply(field.getDeclaringType());
        }
    }
}

