package spoon.test.annotation.testclasses;


@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE_USE })
public @interface TypeAnnotation {
    int integer() default 1;

    int[] integers() default {  };

    java.lang.String string() default "";

    java.lang.String[] strings() default {  };

    java.lang.Class<?> clazz() default java.lang.String.class;

    java.lang.Class<?>[] classes() default {  };

    boolean b() default true;

    spoon.test.annotation.testclasses.AnnotParamTypeEnum e() default spoon.test.annotation.testclasses.AnnotParamTypeEnum.R;

    spoon.test.annotation.testclasses.InnerAnnot ia() default @spoon.test.annotation.testclasses.InnerAnnot("");

    spoon.test.annotation.testclasses.InnerAnnot[] ias() default {  };

    spoon.test.annotation.testclasses.Inception inception() default @spoon.test.annotation.testclasses.Inception(@spoon.test.annotation.testclasses.InnerAnnot(""));

    spoon.test.annotation.testclasses.Inception[] inceptions() default {  };
}

