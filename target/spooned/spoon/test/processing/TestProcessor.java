package spoon.test.processing;


public class TestProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtElement> {
    @java.lang.Override
    public void init() {
        super.init();
        getEnvironment().debugMessage(("MAIN METHODS: " + (getFactory().Method().getMainMethods())));
    }

    public void process(spoon.reflect.declaration.CtElement element) {
        if ((!(element instanceof spoon.reflect.declaration.CtPackage)) && (!(element.isParentInitialized()))) {
            getEnvironment().report(this, org.apache.log4j.Level.ERROR, element, (("Element's parent is null (" + element) + ")"));
            throw new java.lang.RuntimeException("uninitialized parent detected");
        }
        if (element instanceof spoon.reflect.declaration.CtTypedElement) {
            if ((((spoon.reflect.declaration.CtTypedElement<?>) (element)).getType()) == null) {
                getEnvironment().report(this, org.apache.log4j.Level.WARN, element, (("Element's type is null (" + element) + ")"));
            }
        }
        if (element instanceof spoon.reflect.declaration.CtClass) {
            spoon.reflect.declaration.CtClass<?> c = ((spoon.reflect.declaration.CtClass<?>) (element));
            if (c.getSimpleName().equals("Secondary")) {
                @java.lang.SuppressWarnings("unused")
                spoon.reflect.cu.CompilationUnit cu = c.getPosition().getCompilationUnit();
            }
            if (c.getSimpleName().equals("C1")) {
                spoon.template.Substitution.insertAll(c, new spoon.test.template.testclasses.constructors.TemplateWithConstructor(getFactory().Type().createReference(java.util.Date.class)));
            }
        }
    }
}

