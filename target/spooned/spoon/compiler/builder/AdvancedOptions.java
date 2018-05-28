package spoon.compiler.builder;


public class AdvancedOptions<T extends spoon.compiler.builder.AdvancedOptions<T>> extends spoon.compiler.builder.Options<T> {
    public AdvancedOptions() {
        super(spoon.compiler.builder.AdvancedOptions.class);
    }

    public T continueExecution() {
        args.add("-noExit");
        return myself;
    }

    public T enableJavadoc() {
        args.add("-enableJavadoc");
        return myself;
    }

    public T preserveUnusedVars() {
        args.add("-preserveAllLocals");
        return myself;
    }
}

