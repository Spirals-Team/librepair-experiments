package spoon.reflect.factory;


public class EnumFactory extends spoon.reflect.factory.TypeFactory {
    public EnumFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public spoon.reflect.declaration.CtEnum<?> create(spoon.reflect.declaration.CtPackage owner, java.lang.String simpleName) {
        spoon.reflect.declaration.CtEnum<?> e = factory.Core().createEnum();
        e.setSimpleName(simpleName);
        owner.addType(e);
        return e;
    }

    public spoon.reflect.declaration.CtEnum<?> create(java.lang.String qualifiedName) {
        return create(factory.Package().getOrCreate(getPackageName(qualifiedName)), getSimpleName(qualifiedName));
    }

    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.declaration.CtEnum<?> get(java.lang.String qualifiedName) {
        try {
            return ((spoon.reflect.declaration.CtEnum<?>) (super.get(qualifiedName)));
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public <T extends java.lang.Enum<?>> spoon.reflect.declaration.CtEnum<T> getEnum(java.lang.Class<T> cl) {
        try {
            spoon.reflect.declaration.CtType<T> t = super.get(cl);
            return ((spoon.reflect.declaration.CtEnum<T>) (t));
        } catch (java.lang.Exception e) {
            return null;
        }
    }
}

