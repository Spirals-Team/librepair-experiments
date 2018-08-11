package net.coljate.map.impl;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

/**
 *
 * @author Ollie
 */
public class EclipseUnifiedMap<K, V> extends MutableWrappedMap<K, V> {

    public EclipseUnifiedMap() {
        this(new UnifiedMap<>());
    }

    public EclipseUnifiedMap(final UnifiedMap<K, V> delegate) {
        super(delegate);
    }

    @Override
    public UnifiedMap<K, V> mutableJavaMapCopy() {
        return this.javaMapCopy(UnifiedMap::new);
    }

}
