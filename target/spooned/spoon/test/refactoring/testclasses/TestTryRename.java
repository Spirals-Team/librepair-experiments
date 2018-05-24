package spoon.test.refactoring.testclasses;


@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.LOCAL_VARIABLE, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.FIELD })
public @interface TestTryRename {
    java.lang.String[] value();
}

