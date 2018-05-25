package spoon.test.annotation.testclasses.repeatable;


@java.lang.annotation.Repeatable(spoon.test.annotation.testclasses.repeatable.Tags.class)
public @interface Tag {
    java.lang.String value() default "";
}

