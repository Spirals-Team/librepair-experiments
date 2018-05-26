package spoon.experimental.modelobs.context;


public class MapContext<K, V> extends spoon.experimental.modelobs.context.Context {
    private final java.util.Map<K, V> map;

    private K key;

    public MapContext(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.util.Map<K, V> map) {
        super(element, role);
        this.map = map;
    }

    public MapContext(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.util.Map<K, V> map, K key) {
        this(element, role, map);
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public java.util.Map<K, V> getMap() {
        return map;
    }
}

