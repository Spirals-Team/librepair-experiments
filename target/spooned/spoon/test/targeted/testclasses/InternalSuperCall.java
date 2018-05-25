package spoon.test.targeted.testclasses;


public class InternalSuperCall {
    public void methode() {
        spoon.test.targeted.testclasses.InternalSuperCall.super.toString();
    }

    @java.lang.Override
    public java.lang.String toString() {
        return super.toString();
    }
}

