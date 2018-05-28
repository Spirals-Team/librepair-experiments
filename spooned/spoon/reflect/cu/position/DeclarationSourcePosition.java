package spoon.reflect.cu.position;


public interface DeclarationSourcePosition extends spoon.reflect.cu.SourcePosition {
    int getModifierSourceStart();

    int getModifierSourceEnd();

    int getNameStart();

    int getNameEnd();
}

