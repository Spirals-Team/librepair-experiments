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

