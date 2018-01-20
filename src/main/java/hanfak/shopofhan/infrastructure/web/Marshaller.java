package hanfak.shopofhan.infrastructure.web;

public interface Marshaller<T> {
    String marshall(T object);
    }
