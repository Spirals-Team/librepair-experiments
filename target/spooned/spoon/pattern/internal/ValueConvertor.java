package spoon.pattern.internal;


public interface ValueConvertor {
    <T> T getValueAs(spoon.reflect.factory.Factory factory, java.lang.Object value, java.lang.Class<T> valueClass);
}

