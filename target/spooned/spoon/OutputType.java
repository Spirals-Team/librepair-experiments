package spoon;


public enum OutputType {
    NO_OUTPUT, CLASSES, COMPILATION_UNITS;
    @java.lang.Override
    public java.lang.String toString() {
        return this.name().toLowerCase(java.util.Locale.US).replaceAll("_", "");
    }

    public static spoon.OutputType fromString(java.lang.String string) {
        for (spoon.OutputType outputType : spoon.OutputType.values()) {
            if (outputType.toString().equals(string)) {
                return outputType;
            }
        }
        return null;
    }
}

