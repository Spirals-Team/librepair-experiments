package spoon.reflect.factory;


public class ClassFactory extends spoon.reflect.factory.TypeFactory {
    public ClassFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public <T> spoon.reflect.declaration.CtClass<T> create(spoon.reflect.declaration.CtClass<?> declaringClass, java.lang.String simpleName) {
        spoon.reflect.declaration.CtClass<T> c = factory.Core().createClass();
        c.setSimpleName(simpleName);
        declaringClass.addNestedType(c);
        return c;
    }

    public <T> spoon.reflect.declaration.CtClass<T> create(spoon.reflect.declaration.CtPackage owner, java.lang.String simpleName) {
        spoon.reflect.declaration.CtClass<T> c = factory.Core().createClass();
        c.setSimpleName(simpleName);
        owner.addType(c);
        return c;
    }

    public <T> spoon.reflect.declaration.CtClass<T> create(java.lang.String qualifiedName) {
        if ((hasInnerType(qualifiedName)) > 0) {
            spoon.reflect.declaration.CtClass<?> declaringClass = create(getDeclaringTypeName(qualifiedName));
            return create(declaringClass, getSimpleName(qualifiedName));
        }
        return create(factory.Package().getOrCreate(getPackageName(qualifiedName)), getSimpleName(qualifiedName));
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtClass<T> get(java.lang.Class<?> cl) {
        try {
            return ((spoon.reflect.declaration.CtClass<T>) (super.get(cl)));
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtClass<T> get(java.lang.String qualifiedName) {
        try {
            return ((spoon.reflect.declaration.CtClass<T>) (super.get(qualifiedName)));
        } catch (java.lang.Exception e) {
            return null;
        }
    }
}

