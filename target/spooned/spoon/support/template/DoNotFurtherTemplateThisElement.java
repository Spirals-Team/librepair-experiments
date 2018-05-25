package spoon.support.template;


class DoNotFurtherTemplateThisElement extends spoon.SpoonException {
    private static final long serialVersionUID = 1L;

    java.lang.Object skipped;

    DoNotFurtherTemplateThisElement(spoon.reflect.declaration.CtElement e) {
        super(("Skipping " + (e.getClass().getName())));
        skipped = e;
    }
}

