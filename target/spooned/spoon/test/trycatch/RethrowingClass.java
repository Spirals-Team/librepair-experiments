package spoon.test.trycatch;


public class RethrowingClass {
    static class FirstException extends java.lang.Exception {}

    static class SecondException extends java.lang.Exception {}

    public void rethrowException(java.lang.String exceptionName) throws spoon.test.trycatch.RethrowingClass.FirstException, spoon.test.trycatch.RethrowingClass.SecondException {
        try {
            if (exceptionName.equals("First")) {
                throw new spoon.test.trycatch.RethrowingClass.FirstException();
            }else {
                throw new spoon.test.trycatch.RethrowingClass.SecondException();
            }
        } catch (java.lang.Exception e) {
            throw e;
        }
    }
}

