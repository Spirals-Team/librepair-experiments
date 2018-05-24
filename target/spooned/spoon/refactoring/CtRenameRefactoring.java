package spoon.refactoring;


public interface CtRenameRefactoring<T extends spoon.reflect.declaration.CtNamedElement> extends spoon.refactoring.CtRefactoring {
    T getTarget();

    spoon.refactoring.CtRenameRefactoring<T> setTarget(T target);

    java.lang.String getNewName();

    spoon.refactoring.CtRenameRefactoring<T> setNewName(java.lang.String newName);
}

