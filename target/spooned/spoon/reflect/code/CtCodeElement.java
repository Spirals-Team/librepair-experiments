package spoon.reflect.code;


public interface CtCodeElement extends spoon.reflect.declaration.CtElement {
    <R extends spoon.reflect.code.CtCodeElement> R partiallyEvaluate();

    @java.lang.Override
    spoon.reflect.code.CtCodeElement clone();
}

