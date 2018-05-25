package spoon.support.visitor;


public interface GenericTypeAdapter {
    spoon.reflect.declaration.CtFormalTypeDeclarer getAdaptationScope();

    spoon.reflect.reference.CtTypeReference<?> adaptType(spoon.reflect.declaration.CtTypeInformation type);

    spoon.support.visitor.GenericTypeAdapter getEnclosingGenericTypeAdapter();
}

