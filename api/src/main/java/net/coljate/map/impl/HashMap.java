package net.coljate.map.impl;

import net.coljate.map.FastGet;
import net.coljate.map.Map;

/**
 *
 * @author Ollie
 * @since 1.0
 */
public interface HashMap<K, V> extends Map<K, V>, FastGet<K, V> {

    @Override
    default java.util.Map<K, V> mutableJavaMapCopy() {
        return this.javaMapCopy(java.util.HashMap::new);
    }

}
