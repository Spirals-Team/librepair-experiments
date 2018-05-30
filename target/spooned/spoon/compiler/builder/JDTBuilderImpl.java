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

