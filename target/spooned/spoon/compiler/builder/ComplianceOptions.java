package spoon.compiler.builder;


public class ComplianceOptions<T extends spoon.compiler.builder.ComplianceOptions<T>> extends spoon.compiler.builder.Options<T> {
    public ComplianceOptions() {
        super(spoon.compiler.builder.ComplianceOptions.class);
    }

    public T compliance(int version) {
        args.add(("-1." + version));
        return myself;
    }
}

