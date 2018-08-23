package ru.job4j.personaltasks;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * Class SearchCycle.
 */
public class SearchCycle {
    /**
     * Method search.
     * @param data where search cycle.
     * @return List of cycle
     */
    public List<List<Integer>> search(Map<Integer, Integer> data) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Integer> copy = new HashMap<>(data);
        Iterator iterator = copy.keySet().iterator();
        while (iterator.hasNext()) {
            List<Integer> list = new ArrayList<>();
            Integer value = (Integer) iterator.next();
            Integer key = copy.get(value);
            list.add(value);
            int count = 0;
            while (copy.containsKey(key) && key != value && count < copy.size()) {
                list.add(key);
                key = copy.get(key);
                if (key == value) {
                    list.add(key);
                }
                count++;
            }
            if (list.get(list.size() - 1) == value && list.size() > 1) {
                result.add(list);
                iterator.remove();
            }
        }
        return result;
    }
}
