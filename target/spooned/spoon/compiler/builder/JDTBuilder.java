/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.compiler.builder;


/**
 * Helper to build arguments for the JDT compiler.
 */
public interface JDTBuilder {
    /**
     * Classpath options for the compiler.
     */
    spoon.compiler.builder.JDTBuilder classpathOptions(spoon.compiler.builder.ClasspathOptions<?> options);

    /**
     * Compliance options for the compiler.
     */
    spoon.compiler.builder.JDTBuilder complianceOptions(spoon.compiler.builder.ComplianceOptions<?> options);

    /**
     * Annotation processing options for the compiler.
     */
    spoon.compiler.builder.JDTBuilder annotationProcessingOptions(spoon.compiler.builder.AnnotationProcessingOptions<?> options);

    /**
     * Advanced options for the compiler.
     */
    spoon.compiler.builder.JDTBuilder advancedOptions(spoon.compiler.builder.AdvancedOptions<?> options);

    /**
     * Sources for the compiler.
     */
    spoon.compiler.builder.JDTBuilder sources(spoon.compiler.builder.SourceOptions<?> options);

    /**
     * Builds arguments.
     */
    java.lang.String[] build();
}

