package spoon.compiler.builder;


public interface JDTBuilder {
    spoon.compiler.builder.JDTBuilder classpathOptions(spoon.compiler.builder.ClasspathOptions<?> options);

    spoon.compiler.builder.JDTBuilder complianceOptions(spoon.compiler.builder.ComplianceOptions<?> options);

    spoon.compiler.builder.JDTBuilder annotationProcessingOptions(spoon.compiler.builder.AnnotationProcessingOptions<?> options);

    spoon.compiler.builder.JDTBuilder advancedOptions(spoon.compiler.builder.AdvancedOptions<?> options);

    spoon.compiler.builder.JDTBuilder sources(spoon.compiler.builder.SourceOptions<?> options);

    java.lang.String[] build();
}

