package spoon.test.api;


public class AwesomeProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtClass<spoon.test.api.testclasses.Bar>> {
    final java.util.List<spoon.reflect.declaration.CtClass<spoon.test.api.testclasses.Bar>> elements = new java.util.ArrayList<spoon.reflect.declaration.CtClass<spoon.test.api.testclasses.Bar>>();

    @java.lang.Override
    public void process(spoon.reflect.declaration.CtClass<spoon.test.api.testclasses.Bar> element) {
        final spoon.reflect.declaration.CtMethod prepareMojito = element.getMethodsByName("doSomething").get(0);
        prepareMojito.setSimpleName("prepareMojito");
        prepareMojito.setType(getFactory().Type().VOID_PRIMITIVE);
        final spoon.reflect.code.CtBlock<java.lang.Object> block = getFactory().Core().createBlock();
        block.addStatement(getFactory().Code().createCodeSnippetStatement("System.out.println(\"Prepare mojito\")"));
        prepareMojito.setBody(block);
        final spoon.reflect.declaration.CtMethod makeMojito = prepareMojito.clone();
        makeMojito.setSimpleName("makeMojito");
        final spoon.reflect.code.CtBlock<java.lang.Object> blockMake = getFactory().Core().createBlock();
        blockMake.addStatement(getFactory().Code().createCodeSnippetStatement("System.out.println(\"Make mojito!\")"));
        makeMojito.setBody(blockMake);
        element.addMethod(makeMojito);
        elements.add(element);
    }

    public java.util.List<spoon.reflect.declaration.CtClass<spoon.test.api.testclasses.Bar>> getElements() {
        return java.util.Collections.unmodifiableList(elements);
    }
}

