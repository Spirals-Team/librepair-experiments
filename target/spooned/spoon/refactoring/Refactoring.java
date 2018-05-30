package spoon.refactoring;


public final class Refactoring {
    private Refactoring() {
    }

    public static void changeTypeName(final spoon.reflect.declaration.CtType<?> type, java.lang.String name) {
        final java.lang.String typeQFN = type.getQualifiedName();
        final java.util.List<spoon.reflect.reference.CtTypeReference<?>> references = spoon.reflect.visitor.Query.getElements(type.getFactory(), new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtTypeReference<?>>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference<?> reference) {
                java.lang.String refFQN = reference.getQualifiedName();
                return typeQFN.equals(refFQN);
            }
        });
        type.setSimpleName(name);
        for (spoon.reflect.reference.CtTypeReference<?> reference : references) {
            reference.setSimpleName(name);
        }
    }

    public static void changeMethodName(final spoon.reflect.declaration.CtMethod<?> method, java.lang.String newName) {
        final java.util.List<spoon.reflect.reference.CtExecutableReference<?>> references = spoon.reflect.visitor.Query.getElements(method.getFactory(), new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtExecutableReference<?>>(spoon.reflect.reference.CtExecutableReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtExecutableReference<?> reference) {
                return (reference.getDeclaration()) == method;
            }
        });
        method.setSimpleName(newName);
        for (spoon.reflect.reference.CtExecutableReference<?> reference : references) {
            reference.setSimpleName(newName);
        }
    }

    public static spoon.reflect.declaration.CtMethod<?> copyMethod(final spoon.reflect.declaration.CtMethod<?> method) {
        spoon.reflect.declaration.CtMethod<?> clone = method.clone();
        java.lang.String tentativeTypeName = (method.getSimpleName()) + "Copy";
        spoon.reflect.declaration.CtType parent = method.getParent(spoon.reflect.declaration.CtType.class);
        while ((parent.getMethodsByName(tentativeTypeName).size()) > 0) {
            tentativeTypeName += "X";
        } 
        final java.lang.String cloneMethodName = tentativeTypeName;
        clone.setSimpleName(cloneMethodName);
        parent.addMethod(clone);
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
                spoon.reflect.declaration.CtExecutable<T> declaration = reference.getDeclaration();
                if (declaration == null) {
                    return;
                }
                if (declaration == method) {
                    reference.setSimpleName(cloneMethodName);
                }
                if ((reference.getDeclaration()) != clone) {
                    throw new spoon.SpoonException(("post condition broken " + reference));
                }
                super.visitCtExecutableReference(reference);
            }
        }.scan(clone);
        return clone;
    }

    public static spoon.reflect.declaration.CtType<?> copyType(final spoon.reflect.declaration.CtType<?> type) {
        spoon.reflect.declaration.CtType<?> clone = type.clone();
        java.lang.String tentativeTypeName = (type.getSimpleName()) + "Copy";
        while ((type.getFactory().Type().get((((type.getPackage().getQualifiedName()) + ".") + tentativeTypeName))) != null) {
            tentativeTypeName += "X";
        } 
        final java.lang.String cloneTypeName = tentativeTypeName;
        clone.setSimpleName(cloneTypeName);
        type.getPackage().addType(clone);
        new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference) {
                if ((reference.getDeclaration()) == null) {
                    return;
                }
                if ((reference.getDeclaration()) == type) {
                    reference.setSimpleName(cloneTypeName);
                }
                if ((reference.getDeclaration()) != clone) {
                    throw new spoon.SpoonException(("post condition broken " + reference));
                }
                super.visitCtTypeReference(reference);
            }

            @java.lang.Override
            public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
                spoon.reflect.declaration.CtExecutable<T> declaration = reference.getDeclaration();
                if (declaration == null) {
                    return;
                }
                if (declaration.hasParent(type)) {
                    reference.getDeclaringType().setSimpleName(cloneTypeName);
                }
                if (!(reference.getDeclaration().hasParent(clone))) {
                    throw new spoon.SpoonException(("post condition broken " + reference));
                }
                super.visitCtExecutableReference(reference);
            }

            @java.lang.Override
            public <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> reference) {
                spoon.reflect.declaration.CtField<T> declaration = reference.getDeclaration();
                if (declaration == null) {
                    return;
                }
                if (declaration.hasParent(type)) {
                    reference.getDeclaringType().setSimpleName(cloneTypeName);
                }
                if (((reference.getDeclaration()) == null) || (!(reference.getDeclaration().hasParent(clone)))) {
                    throw new spoon.SpoonException(("post condition broken " + reference));
                }
                super.visitCtFieldReference(reference);
            }
        }.scan(clone);
        return clone;
    }

    public static void changeLocalVariableName(spoon.reflect.code.CtLocalVariable<?> localVariable, java.lang.String newName) throws spoon.refactoring.RefactoringException {
        new spoon.refactoring.CtRenameLocalVariableRefactoring().setTarget(localVariable).setNewName(newName).refactor();
    }
}

