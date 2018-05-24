package spoon.support.compiler.jdt;


class CompilationUnitWrapper extends org.eclipse.jdt.internal.compiler.batch.CompilationUnit {
    private spoon.reflect.declaration.CtType type;

    CompilationUnitWrapper(spoon.reflect.declaration.CtType type) {
        super(null, ((type.getSimpleName()) + ".java"), type.getFactory().getEnvironment().getEncoding().displayName(), type.getFactory().getEnvironment().getBinaryOutputDirectory(), false, null);
        this.type = type;
    }

    @java.lang.Override
    public char[] getContents() {
        spoon.reflect.visitor.DefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(type.getFactory().getEnvironment());
        java.util.List<spoon.reflect.declaration.CtType<?>> types = new java.util.ArrayList<>();
        types.add(type);
        printer.calculate(type.getPosition().getCompilationUnit(), types);
        java.lang.String result = printer.getResult();
        char[] content = result.toCharArray();
        return content;
    }
}

