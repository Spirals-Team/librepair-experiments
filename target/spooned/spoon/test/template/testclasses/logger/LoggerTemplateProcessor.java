package spoon.test.template.testclasses.logger;


public class LoggerTemplateProcessor<T> extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtMethod<T>> {
    @java.lang.Override
    public boolean isToBeProcessed(spoon.reflect.declaration.CtMethod<T> candidate) {
        return ((candidate.getBody()) != null) && (!(isSubOfTemplate(candidate)));
    }

    private boolean isSubOfTemplate(spoon.reflect.declaration.CtMethod<T> candidate) {
        return candidate.getDeclaringType().isSubtypeOf(getFactory().Type().createReference(spoon.template.Template.class));
    }

    @java.lang.Override
    public void process(spoon.reflect.declaration.CtMethod<T> element) {
        final spoon.reflect.code.CtBlock log = new spoon.test.template.testclasses.logger.LoggerTemplate(element.getDeclaringType().getSimpleName(), element.getSimpleName(), element.getBody()).apply(element.getDeclaringType());
        element.setBody(log);
    }
}

