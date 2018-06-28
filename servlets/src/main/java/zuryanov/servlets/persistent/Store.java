package zuryanov.servlets.persistent;

import java.util.List;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Store {
    void add(String name);
    String update(int id, String name);
    String delete(int id);
    List<String> findAll();
    String findById(int id);
    int sizeStore();
}
