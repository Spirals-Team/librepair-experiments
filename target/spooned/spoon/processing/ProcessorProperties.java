package spoon.processing;


public interface ProcessorProperties {
    <T> T get(java.lang.Class<T> type, java.lang.String name);

    void set(java.lang.String name, java.lang.Object o);

    java.lang.String getProcessorName();
}

