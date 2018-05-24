package spoon.reflect.visitor;


public class MinimalImportScanner extends spoon.reflect.visitor.ImportScannerImpl implements spoon.reflect.visitor.ImportScanner {
    private boolean shouldTypeBeImported(spoon.reflect.reference.CtReference ref) {
        if (ref.equals(targetType)) {
            return true;
        }
        return isTypeInCollision(ref, true);
    }

    @java.lang.Override
    protected boolean addClassImport(spoon.reflect.reference.CtTypeReference<?> ref) {
        boolean shouldTypeBeImported = this.shouldTypeBeImported(ref);
        if (shouldTypeBeImported) {
            return super.addClassImport(ref);
        }else {
            return false;
        }
    }

    @java.lang.Override
    protected boolean addFieldImport(spoon.reflect.reference.CtFieldReference ref) {
        if ((ref.getDeclaringType()) != null) {
            if (isImportedInClassImports(ref.getDeclaringType())) {
                return false;
            }
        }
        boolean shouldTypeBeImported = this.shouldTypeBeImported(ref);
        if (shouldTypeBeImported) {
            if (this.fieldImports.containsKey(ref.getSimpleName())) {
                return isImportedInFieldImports(ref);
            }
            fieldImports.put(ref.getSimpleName(), ref);
            return true;
        }else {
            return false;
        }
    }

    @java.lang.Override
    protected boolean addMethodImport(spoon.reflect.reference.CtExecutableReference ref) {
        if ((ref.getDeclaringType()) != null) {
            if (isImportedInClassImports(ref.getDeclaringType())) {
                return false;
            }
        }
        boolean shouldTypeBeImported = this.shouldTypeBeImported(ref);
        if (shouldTypeBeImported) {
            if (this.methodImports.containsKey(ref.getSimpleName())) {
                return isImportedInMethodImports(ref);
            }
            methodImports.put(ref.getSimpleName(), ref);
            if ((ref.getDeclaringType()) != null) {
                if ((ref.getDeclaringType().getPackage()) != null) {
                    if (ref.getDeclaringType().getPackage().equals(this.targetType.getPackage())) {
                        addClassImport(ref.getDeclaringType());
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }

    @java.lang.Override
    protected boolean isImportedInClassImports(spoon.reflect.reference.CtTypeReference<?> ref) {
        if ((!(ref.isImplicit())) && (classImports.containsKey(ref.getSimpleName()))) {
            spoon.reflect.reference.CtTypeReference<?> exist = classImports.get(ref.getSimpleName());
            if (exist.getQualifiedName().equals(ref.getQualifiedName())) {
                return true;
            }
        }
        return false;
    }
}

