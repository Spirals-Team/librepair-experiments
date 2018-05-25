package spoon.refactoring;


public class RefactoringException extends spoon.SpoonException {
    private static final long serialVersionUID = 1L;

    public RefactoringException() {
    }

    public RefactoringException(java.lang.String msg) {
        super(msg);
    }

    public RefactoringException(java.lang.Throwable e) {
        super(e);
    }

    public RefactoringException(java.lang.String msg, java.lang.Throwable e) {
        super(msg, e);
    }
}

