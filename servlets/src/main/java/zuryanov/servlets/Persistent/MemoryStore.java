package zuryanov.servlets.Persistent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MemoryStore implements Store {

    private static final MemoryStore memoryStore = new MemoryStore();
    private MemoryStore() {}

    public static MemoryStore getInstance() {
        return memoryStore;
    }

    private List<String> userStore = new CopyOnWriteArrayList<>();
    @Override
    public String add(String name) {
        userStore.add(name);
        System.out.println(name);
        return name;
    }

    @Override
    public String update(int id, String name) {
        return userStore.set(id, name);
    }

    @Override
    public String delete(int id) {
        return userStore.remove(id);
    }

    @Override
    public List<String> findAll(String name) {
        List<String> result = new CopyOnWriteArrayList<>();
        for (String user : userStore) {
            if (name.equals(user)) {
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public String findById(int id) {
        return userStore.get(id);
    }

    @Override
    public int sizeStore() {
        return userStore.size();
    }
}
