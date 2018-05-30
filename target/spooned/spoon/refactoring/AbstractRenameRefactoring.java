package spoon.refactoring;


public abstract class AbstractRenameRefactoring<T extends spoon.reflect.declaration.CtNamedElement> implements spoon.refactoring.CtRenameRefactoring<T> {
    public static final java.util.regex.Pattern javaIdentifierRE = java.util.regex.Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*");

    protected T target;

    protected java.lang.String newName;

    protected java.util.regex.Pattern newNameValidationRE;

    protected AbstractRenameRefactoring(java.util.regex.Pattern newNameValidationRE) {
        this.newNameValidationRE = newNameValidationRE;
    }

    @java.lang.Override
    public void refactor() {
        if ((getTarget()) == null) {
            throw new spoon.SpoonException("The target of refactoring is not defined");
        }
        if ((getNewName()) == null) {
            throw new spoon.SpoonException("The new name of refactoring is not defined");
        }
        detectIssues();
        refactorNoCheck();
    }

    protected abstract void refactorNoCheck();

    protected void detectIssues() {
        checkNewNameIsValid();
        detectNameConflicts();
    }

    protected void checkNewNameIsValid() {
    }

    protected void detectNameConflicts() {
    }

    protected boolean isJavaIdentifier(java.lang.String name) {
        return spoon.refactoring.AbstractRenameRefactoring.javaIdentifierRE.matcher(name).matches();
    }

    @java.lang.Override
    public T getTarget() {
        return target;
    }

    @java.lang.Override
    public spoon.refactoring.AbstractRenameRefactoring<T> setTarget(T target) {
        this.target = target;
        return this;
    }

    @java.lang.Override
    public java.lang.String getNewName() {
        return newName;
    }

    @java.lang.Override
    public spoon.refactoring.AbstractRenameRefactoring<T> setNewName(java.lang.String newName) {
        if (((newNameValidationRE) != null) && ((newNameValidationRE.matcher(newName).matches()) == false)) {
            throw new spoon.SpoonException((("New name \"" + newName) + "\" is not valid name"));
        }
        this.newName = newName;
        return this;
    }
}

