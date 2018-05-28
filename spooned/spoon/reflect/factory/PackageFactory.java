package spoon.reflect.factory;


public class PackageFactory extends spoon.reflect.factory.SubFactory implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public PackageFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public spoon.reflect.reference.CtPackageReference createReference(spoon.reflect.declaration.CtPackage pack) {
        if (pack == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return createReference(pack.getQualifiedName());
    }

    public spoon.reflect.reference.CtPackageReference createReference(java.lang.Package pack) {
        return createReference(pack.getName());
    }

    public spoon.reflect.reference.CtPackageReference topLevel() {
        return factory.getModel().getRootPackage().getReference();
    }

    public spoon.reflect.reference.CtPackageReference createReference(java.lang.String name) {
        spoon.reflect.reference.CtPackageReference ref = factory.Core().createPackageReference();
        ref.setSimpleName(name);
        return ref;
    }

    public spoon.reflect.declaration.CtPackage create(spoon.reflect.declaration.CtPackage parent, java.lang.String simpleName) {
        return getOrCreate((((parent.toString()) + (spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR)) + simpleName));
    }

    public spoon.reflect.declaration.CtPackage getOrCreate(java.lang.String qualifiedName) {
        if (qualifiedName.isEmpty()) {
            return factory.getModel().getRootPackage();
        }
        java.util.StringTokenizer token = new java.util.StringTokenizer(qualifiedName, spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR);
        spoon.reflect.declaration.CtPackage last = factory.getModel().getRootPackage();
        while (token.hasMoreElements()) {
            java.lang.String name = token.nextToken();
            spoon.reflect.declaration.CtPackage next = last.getPackage(name);
            if (next == null) {
                next = factory.Core().createPackage();
                next.setSimpleName(name);
                last.addPackage(next);
            }
            last = next;
        } 
        return last;
    }

    public spoon.reflect.declaration.CtPackage get(java.lang.String qualifiedName) {
        if (qualifiedName.contains(spoon.reflect.declaration.CtType.INNERTTYPE_SEPARATOR)) {
            throw new java.lang.RuntimeException(("Invalid package name " + qualifiedName));
        }
        java.util.StringTokenizer token = new java.util.StringTokenizer(qualifiedName, spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR);
        spoon.reflect.declaration.CtPackage current = factory.getModel().getRootPackage();
        if (token.hasMoreElements()) {
            current = current.getPackage(token.nextToken());
            while ((token.hasMoreElements()) && (current != null)) {
                current = current.getPackage(token.nextToken());
            } 
        }
        return current;
    }

    public java.util.Collection<spoon.reflect.declaration.CtPackage> getAll() {
        return factory.getModel().getAllPackages();
    }

    public spoon.reflect.declaration.CtPackage getRootPackage() {
        return factory.getModel().getRootPackage();
    }

    private java.util.List<spoon.reflect.declaration.CtPackage> getSubPackageList(spoon.reflect.declaration.CtPackage pack) {
        java.util.List<spoon.reflect.declaration.CtPackage> packs = new java.util.ArrayList<>();
        packs.add(pack);
        for (spoon.reflect.declaration.CtPackage p : pack.getPackages()) {
            packs.addAll(getSubPackageList(p));
        }
        return packs;
    }
}

