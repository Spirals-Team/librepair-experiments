package spoon.compiler.builder;


public class AnnotationProcessingOptions<T extends spoon.compiler.builder.AnnotationProcessingOptions<T>> extends spoon.compiler.builder.Options<T> {
    public AnnotationProcessingOptions() {
        super(spoon.compiler.builder.AnnotationProcessingOptions.class);
    }

    public T processors(java.lang.String processors) {
        if ((processors == null) || (processors.isEmpty())) {
            return myself;
        }
        args.add("-processor");
        args.add(processors);
        return myself;
    }

    public T processors(java.lang.String... processors) {
        if ((processors == null) || ((processors.length) == 0)) {
            return myself;
        }
        return processors(join(COMMA_DELIMITER, processors));
    }

    public T runProcessors() {
        args.add("-proc:only");
        return myself;
    }

    public T compileProcessors() {
        args.add("-proc:none");
        return myself;
    }
}

