package spoon.compiler.builder;


public class JDTBuilderImpl implements spoon.compiler.builder.JDTBuilder {
    private final java.util.List<java.lang.String> args = new java.util.ArrayList<>();

    private boolean hasSources = false;

    @java.lang.Override
    public spoon.compiler.builder.JDTBuilder classpathOptions(spoon.compiler.builder.ClasspathOptions<?> options) {
        checkSources();
        args.addAll(java.util.Arrays.asList(options.build()));
        return this;
    }

    @java.lang.Override
    public spoon.compiler.builder.JDTBuilder complianceOptions(spoon.compiler.builder.ComplianceOptions<?> options) {
        checkSources();
        args.addAll(java.util.Arrays.asList(options.build()));
        return this;
    }

    @java.lang.Override
    public spoon.compiler.builder.JDTBuilder annotationProcessingOptions(spoon.compiler.builder.AnnotationProcessingOptions<?> options) {
        checkSources();
        args.addAll(java.util.Arrays.asList(options.build()));
        return this;
    }

    @java.lang.Override
    public spoon.compiler.builder.JDTBuilder advancedOptions(spoon.compiler.builder.AdvancedOptions<?> options) {
        checkSources();
        args.addAll(java.util.Arrays.asList(options.build()));
        return this;
    }

    @java.lang.Override
    public spoon.compiler.builder.JDTBuilder sources(spoon.compiler.builder.SourceOptions<?> options) {
        hasSources = true;
        args.addAll(java.util.Arrays.asList(options.build()));
        return this;
    }

    @java.lang.Override
    public java.lang.String[] build() {
        return args.toArray(new java.lang.String[args.size()]);
    }

    private void checkSources() {
        if (hasSources) {
            throw new java.lang.RuntimeException("Please, specify sources at the end.");
        }
    }
}

