package spoon.reflect.visitor.processors;


public class CheckScannerTestProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtClass<?>> {
    private final java.util.List<java.lang.String> excludingClasses = java.util.Arrays.asList("CompilationUnitVirtualImpl", "CtWildcardStaticTypeMemberReferenceImpl");

    @java.lang.Override
    public boolean isToBeProcessed(spoon.reflect.declaration.CtClass<?> candidate) {
        return (((((super.isToBeProcessed(candidate)) && (!(excludingClasses.contains(candidate.getSimpleName())))) && (!(candidate.hasModifier(spoon.reflect.declaration.ModifierKind.ABSTRACT)))) && (candidate.getSimpleName().endsWith("Impl"))) && (candidate.getPackage().getQualifiedName().startsWith("spoon.support.reflect"))) && (candidate.isTopLevel());
    }

    @java.lang.Override
    public void process(spoon.reflect.declaration.CtClass<?> element) {
        final spoon.reflect.declaration.CtType<spoon.reflect.visitor.CtVisitor> scanner = getFactory().Type().get(spoon.reflect.visitor.CtScanner.class);
        final java.lang.String qualifiedName = element.getQualifiedName().replace(".support.", ".");
        final java.lang.String interfaceName = qualifiedName.substring(0, qualifiedName.lastIndexOf("Impl"));
        final spoon.reflect.declaration.CtType<java.lang.Object> theInterface = getFactory().Type().get(interfaceName);
        final java.util.List<spoon.reflect.declaration.CtMethod<?>> visits = scanner.getMethodsByName(("visit" + (theInterface.getSimpleName())));
        if ((visits.size()) != 1) {
            throw new java.lang.AssertionError(("You must have only one visitor methods in CtScanner for visit" + (theInterface.getSimpleName())));
        }
        final spoon.reflect.declaration.CtMethod<?> visit = visits.get(0);
        if ((visit.getBody().getStatements().size()) < 2) {
            throw new java.lang.AssertionError(("You must have minimum 2 statements in the visit method to call enter and exit in visit" + (theInterface.getSimpleName())));
        }
        checkInvocation("enter", visit.getBody().getStatement(0));
        checkInvocation("exit", visit.getBody().getLastStatement());
    }

    private void checkInvocation(java.lang.String expected, spoon.reflect.code.CtStatement statement) {
        if (!(statement instanceof spoon.reflect.code.CtInvocation)) {
            throw new java.lang.AssertionError((("The statement must be a call to " + expected) + " method."));
        }
        if (!(expected.equals(((spoon.reflect.code.CtInvocation) (statement)).getExecutable().getSimpleName()))) {
            throw new java.lang.AssertionError((("The statement must be a call to " + expected) + " method."));
        }
    }
}

