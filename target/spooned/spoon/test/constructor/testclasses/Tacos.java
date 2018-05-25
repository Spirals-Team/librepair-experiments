package spoon.test.constructor.testclasses;


public class Tacos<C> {
    public Tacos() {
        super();
    }

    public <@spoon.test.constructor.testclasses.Tacos.TypeAnnotation(integer = 2)
    T extends spoon.test.constructor.testclasses.@spoon.test.constructor.testclasses.Tacos.TypeAnnotation(integer = 3)
    Tacos<@spoon.test.constructor.testclasses.Tacos.TypeAnnotation(integer = 4)
    ? super @spoon.test.constructor.testclasses.Tacos.TypeAnnotation(integer = 5)
    C> & java.io.@spoon.test.constructor.testclasses.Tacos.TypeAnnotation(integer = 6)
    Serializable> Tacos(java.lang.Object o) throws java.lang.@spoon.test.constructor.testclasses.Tacos.TypeAnnotation(integer = 1)
    Exception {
    }

    @java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE_USE })
    public @interface TypeAnnotation {
        int integer() default 1;
    }
}

