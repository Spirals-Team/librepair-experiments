package zuryanov.servlets;

import java.util.List;

public interface Store {
    String add(String name);
    String update(int id, String name);
    String delete(int id);
    List<String> findAll(String name);
    String findById(int id);
    int sizeStore();
}
