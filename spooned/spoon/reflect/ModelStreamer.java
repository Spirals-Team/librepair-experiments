package spoon.reflect;


public interface ModelStreamer {
    void save(spoon.reflect.factory.Factory f, java.io.OutputStream out) throws java.io.IOException;

    spoon.reflect.factory.Factory load(java.io.InputStream in) throws java.io.IOException;
}

