package spoon.template;


public class ExtensionTemplate extends spoon.template.AbstractTemplate<spoon.reflect.declaration.CtType<?>> {
    @java.lang.Override
    public spoon.reflect.declaration.CtType<?> apply(spoon.reflect.declaration.CtType<?> target) {
        spoon.template.Substitution.insertAll(target, this);
        return target;
    }
}

