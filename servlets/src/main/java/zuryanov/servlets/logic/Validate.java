package zuryanov.servlets.logic;

import java.util.List;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Validate {

    String add(String name);
    String update(int id, String name);
    String delete(int id);
    List<String> findAll();
    String findById(int id);
    boolean isCredentional(String login, String password);
}
