package zuryanov.servlets;

public interface Action {
    Type type();
    enum Type {
        ADD,
        UPDATE,
        DELETE
    }
}
