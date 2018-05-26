package spoon.reflect.annotations;


@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.FIELD })
public @interface MetamodelPropertyField {
    spoon.reflect.path.CtRole[] role();
}

