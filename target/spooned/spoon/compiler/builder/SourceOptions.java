package spoon.compiler.builder;


public class SourceOptions<T extends spoon.compiler.builder.SourceOptions<T>> extends spoon.compiler.builder.Options<T> {
    public SourceOptions() {
        super(spoon.compiler.builder.SourceOptions.class);
    }

    public T sources(java.lang.String sources) {
        if ((sources == null) || (sources.isEmpty())) {
            return myself;
        }
        return sources(sources.split(java.io.File.pathSeparator));
    }

    public T sources(java.lang.String... sources) {
        if ((sources == null) || ((sources.length) == 0)) {
            args.add(".");
            return myself;
        }
        args.addAll(java.util.Arrays.asList(sources));
        return myself;
    }

    public T sources(java.util.List<spoon.compiler.SpoonFile> sources) {
        if ((sources == null) || ((sources.size()) == 0)) {
            args.add(".");
            return myself;
        }
        for (spoon.compiler.SpoonFile source : sources) {
            if (source.isActualFile()) {
                args.add(source.toString());
            }else {
                try {
                    java.io.File file = java.io.File.createTempFile(source.getName(), ".java");
                    file.deleteOnExit();
                    org.apache.commons.io.IOUtils.copy(source.getContent(), new java.io.FileOutputStream(file));
                    args.add(file.toString());
                } catch (java.io.IOException e) {
                    throw new java.lang.RuntimeException(e.getMessage(), e);
                }
            }
        }
        return myself;
    }
}

