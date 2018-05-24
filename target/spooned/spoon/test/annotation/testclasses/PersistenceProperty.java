package spoon.test.annotation.testclasses;


@java.lang.annotation.Target({  })
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface PersistenceProperty {
    java.lang.String name();

    java.lang.String value();
}

