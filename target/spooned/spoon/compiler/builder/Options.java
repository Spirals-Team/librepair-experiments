package spoon.compiler.builder;


public abstract class Options<T extends spoon.compiler.builder.Options<T>> {
    final java.lang.String COMMA_DELIMITER = ",";

    final java.util.List<java.lang.String> args = new java.util.ArrayList<>();

    final T myself;

    public Options(java.lang.Class<?> type) {
        this.myself = ((T) (type.cast(this)));
    }

    public java.lang.String[] build() {
        return args.toArray(new java.lang.String[args.size()]);
    }

    protected java.lang.String join(java.lang.String delimiter, java.lang.String[] classpath) {
        if ((classpath == null) || ((classpath.length) == 0)) {
            return "";
        }
        final java.lang.StringBuilder builder = new java.lang.StringBuilder();
        for (java.lang.String entry : classpath) {
            builder.append(entry);
            builder.append(delimiter);
        }
        return builder.toString();
    }
}

