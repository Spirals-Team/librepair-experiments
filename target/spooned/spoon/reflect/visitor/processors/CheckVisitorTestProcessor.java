package spoon.reflect.visitor.processors;


public class CheckVisitorTestProcessor<T extends spoon.reflect.visitor.CtVisitor> extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtClass<?>> {
    private java.lang.Class<T> visitor;

    private final java.util.List<java.lang.String> excludingClasses = java.util.Arrays.asList("CompilationUnitVirtualImpl", "CtWildcardStaticTypeMemberReferenceImpl");

    private boolean hasScanners;

    private boolean hasVisitors;

    public CheckVisitorTestProcessor(java.lang.Class<T> visitor) {
        this.visitor = visitor;
    }

    @java.lang.Override
    public boolean isToBeProcessed(spoon.reflect.declaration.CtClass<?> candidate) {
        return ((((super.isToBeProcessed(candidate)) && (!(excludingClasses.contains(candidate.getSimpleName())))) && (candidate.getSimpleName().endsWith("Impl"))) && (candidate.getPackage().getQualifiedName().startsWith("spoon.support.reflect"))) && (candidate.isTopLevel());
    }

    @java.lang.Override
    public void process(spoon.reflect.declaration.CtClass<?> element) {
        final spoon.reflect.declaration.CtType<spoon.reflect.visitor.CtVisitor> visitor = getFactory().Type().get(this.visitor);
        final java.lang.String qualifiedName = element.getQualifiedName().replace(".support.", ".");
        final java.lang.String interfaceName = qualifiedName.substring(0, qualifiedName.lastIndexOf("Impl"));
        final spoon.reflect.declaration.CtType<java.lang.Object> theInterface = getFactory().Type().get(interfaceName);
        if (hasScanners) {
            checkPresenceScanMethods(visitor, theInterface, element.getModifiers().contains(spoon.reflect.declaration.ModifierKind.ABSTRACT));
        }
        if (hasVisitors) {
            checkPresenceVisitMethods(visitor, theInterface, element.getModifiers().contains(spoon.reflect.declaration.ModifierKind.ABSTRACT));
        }
    }

    public spoon.reflect.visitor.processors.CheckVisitorTestProcessor withScanners() {
        hasScanners = true;
        return this;
    }

    public spoon.reflect.visitor.processors.CheckVisitorTestProcessor withVisitors() {
        hasVisitors = true;
        return this;
    }

    private void checkPresenceScanMethods(spoon.reflect.declaration.CtType<spoon.reflect.visitor.CtVisitor> visitorType, spoon.reflect.declaration.CtType<java.lang.Object> element, boolean isAbstract) {
        int nbScanner = (isAbstract) ? 1 : 0;
        final java.util.List<spoon.reflect.declaration.CtMethod<?>> scanners = visitorType.getMethodsByName(("scan" + (element.getSimpleName())));
        if ((scanners.size()) != nbScanner) {
            if (!(((scanners.size()) > 0) && ((scanners.get(0).getAnnotation(java.lang.Deprecated.class)) != null))) {
                throw new java.lang.AssertionError((((("You should have " + nbScanner) + " scanner methods for the element ") + (element.getSimpleName())) + " in the CtInheritanceScanner."));
            }
        }
    }

    private void checkPresenceVisitMethods(spoon.reflect.declaration.CtType<spoon.reflect.visitor.CtVisitor> visitorType, spoon.reflect.declaration.CtType<java.lang.Object> element, boolean isAbstract) {
        int nbVisit = (isAbstract) ? 0 : 1;
        final java.util.List<spoon.reflect.declaration.CtMethod<?>> visits = visitorType.getMethodsByName(("visit" + (element.getSimpleName())));
        if ((visits.size()) != nbVisit) {
            if (!(((visits.size()) > 0) && ((visits.get(0).getAnnotation(java.lang.Deprecated.class)) != null))) {
                throw new java.lang.AssertionError((((("You should have " + nbVisit) + " visit methods for the element ") + (element.getSimpleName())) + " in the CtInheritanceScanner."));
            }
        }
    }
}

