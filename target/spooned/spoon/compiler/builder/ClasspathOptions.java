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


public class ClasspathOptions<T extends spoon.compiler.builder.ClasspathOptions<T>> extends spoon.compiler.builder.Options<T> {
    public ClasspathOptions() {
        super(spoon.compiler.builder.ClasspathOptions.class);
    }

    public T classpath(java.lang.String classpath) {
        if (classpath == null) {
            return myself;
        }
        args.add("-cp");
        args.add(classpath);
        return myself;
    }

    public T classpath(java.lang.String... classpaths) {
        if ((classpaths == null) || ((classpaths.length) == 0)) {
            return myself;
        }
        return classpath(join(java.io.File.pathSeparator, classpaths));
    }

    public T bootclasspath(java.lang.String bootclasspath) {
        if (bootclasspath == null) {
            return myself;
        }
        args.add("-bootclasspath");
        args.add(bootclasspath);
        return myself;
    }

    public T bootclasspath(java.lang.String... bootclasspaths) {
        if ((bootclasspaths == null) || ((bootclasspaths.length) == 0)) {
            return myself;
        }
        return bootclasspath(join(java.io.File.pathSeparator, bootclasspaths));
    }

    public T binaries(java.lang.String directory) {
        if (directory == null) {
            return binaries(((java.io.File) (null)));
        }
        return binaries(new java.io.File(directory));
    }

    public T binaries(java.io.File directory) {
        if (directory == null) {
            args.add("-d");
            args.add("none");
        }else {
            args.add("-d");
            args.add(directory.getAbsolutePath());
        }
        return myself;
    }

    public T encoding(java.lang.String encoding) {
        if ((encoding == null) || (encoding.isEmpty())) {
            return myself;
        }
        args.add("-encoding");
        args.add(encoding);
        return myself;
    }
}

