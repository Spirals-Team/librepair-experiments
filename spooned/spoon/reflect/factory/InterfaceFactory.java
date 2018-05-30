package spoon.reflect.factory;


public class InterfaceFactory extends spoon.reflect.factory.TypeFactory {
    public InterfaceFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public <T> spoon.reflect.declaration.CtInterface<T> create(spoon.reflect.declaration.CtPackage owner, java.lang.String simpleName) {
        spoon.reflect.declaration.CtInterface<T> i = factory.Core().createInterface();
        i.setSimpleName(simpleName);
        owner.addType(i);
        return i;
    }

    public <T> spoon.reflect.declaration.CtInterface<T> create(spoon.reflect.declaration.CtType<T> owner, java.lang.String simpleName) {
        spoon.reflect.declaration.CtInterface<T> ctInterface = factory.Core().createInterface();
        ctInterface.setSimpleName(simpleName);
        owner.addNestedType(ctInterface);
        return ctInterface;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtInterface<T> create(java.lang.String qualifiedName) {
        if ((hasInnerType(qualifiedName)) > 0) {
            return create(((spoon.reflect.declaration.CtInterface<T>) (create(getDeclaringTypeName(qualifiedName)))), getSimpleName(qualifiedName));
        }
        return create(factory.Package().getOrCreate(getPackageName(qualifiedName)), getSimpleName(qualifiedName));
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtInterface<T> get(java.lang.String qualifiedName) {
        try {
            return ((spoon.reflect.declaration.CtInterface<T>) (super.get(qualifiedName)));
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtInterface<T> get(java.lang.Class<?> cl) {
        try {
            return ((spoon.reflect.declaration.CtInterface<T>) (super.get(cl)));
        } catch (java.lang.Exception e) {
            return null;
        }
    }
}

