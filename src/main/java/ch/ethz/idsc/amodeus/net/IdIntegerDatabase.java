/* amodeus - Copyright (c) 2018, ETH Zurich, Institute for Dynamic Systems and Control */
package ch.ethz.idsc.amodeus.net;

import java.util.HashMap;
import java.util.Map;

public class IdIntegerDatabase {
    private final Map<String, Integer> map = new HashMap<>();

    public int getId(String string) {
        if (!map.containsKey(string))
            map.put(string, map.size());
        return map.get(string);
    }

    public int size() {
        return map.size();
    }
}
