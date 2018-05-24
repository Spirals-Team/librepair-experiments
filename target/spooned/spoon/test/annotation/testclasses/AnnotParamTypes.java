package spoon.test.annotation.testclasses;


public @interface AnnotParamTypes {
    int integer();

    int[] integers();

    java.lang.String string();

    java.lang.String[] strings();

    java.lang.Class<?> clazz();

    java.lang.Class<?>[] classes();

    boolean b();

    byte byt();

    char c();

    short s();

    long l();

    float f();

    double d();

    spoon.test.annotation.testclasses.AnnotParamTypeEnum e();

    spoon.test.annotation.testclasses.InnerAnnot ia();
}

