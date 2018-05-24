package spoon.reflect.visitor;


public interface PrettyPrinter {
    java.lang.String printPackageInfo(spoon.reflect.declaration.CtPackage pack);

    java.lang.String printModuleInfo(spoon.reflect.declaration.CtModule module);

    java.lang.String getResult();

    void calculate(spoon.reflect.cu.CompilationUnit sourceCompilationUnit, java.util.List<spoon.reflect.declaration.CtType<?>> types);

    java.util.Map<java.lang.Integer, java.lang.Integer> getLineNumberMapping();
}

