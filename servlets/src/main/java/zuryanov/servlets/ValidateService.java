package zuryanov.servlets;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ValidateService implements Validate {

    private final Store logic = MemoryStore.getInstance();


    private static final ValidateService validateService = new ValidateService();
    private ValidateService() {
    }

    public static ValidateService getInstance() {
        return validateService;
    }

    @Override
    public String add(String name) {
        logic.add(name);
        return name;
    }

    @Override
    public String update(int id, String name) {
        String result;
        if (logic.sizeStore() > id) {
            result = logic.update(id, name);
        } else result = "This id is absent";
        return result;
    }

    @Override
    public String delete(int id) {
        String result;
        if (logic.sizeStore() > id) {
            result = logic.delete(id);
        } else result = "This id is absent";
        return result;
    }

    @Override
    public List<String> findAll(String name) {
        List<String> result = new CopyOnWriteArrayList<>();
        if (name.length() > 0) {
            result = logic.findAll(name);
        }
        return result;
    }

    @Override
    public String findById(int id) {
        String result;
        if (logic.sizeStore() > id) {
            result = logic.findById(id);
        } else result = "This id is absent";
        return result;
    }

}
