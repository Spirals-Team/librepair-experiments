package net.mirwaldt.jcomparison.primitive.collection.impl;

import net.mirwaldt.jcomparison.primitive.collection.api.NonBoxingList;
import net.mirwaldt.jcomparison.primitive.collection.api.NonBoxingMap;
import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableSupplier;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

import java.util.*;

public class BackedByNonBoxingListsNonBoxingMap implements NonBoxingMap {
    private final NonBoxingList keys;
    private final NonBoxingList values;
    private final MutableSupplier mutableSupplier;
    private final MutableSupplier versionedMutableSupplier;
    private final VersionCounter versionCounter;

    public BackedByNonBoxingListsNonBoxingMap(NonBoxingList keys, 
                                              NonBoxingList values, 
                                              MutableSupplier mutableSupplier, 
                                              MutableSupplier versionedMutableSupplier, 
                                              VersionCounter versionCounter) {
        this.keys = keys;
        this.values = values;
        this.mutableSupplier = mutableSupplier;
        this.versionedMutableSupplier = versionedMutableSupplier;
        this.versionCounter = versionCounter;
    }

    @Override
    public boolean containsKey(Mutable<?, ?> key) {
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Mutable<?, ?> value) {
        return values.contains(value);
    }

    @Override
    public Mutable<?, ?> get(Mutable<?, ?> key) {
        final int index = keys.indexOf(key);
        if(-1 < index) {
            return values.get(index);
        } else {
            return null;
        }
    }

    @Override
    public Mutable<?, ?> put(Mutable<?, ?> key, Mutable<?, ?> value) {
        final int index = keys.indexOf(key);
        if(-1 < index) {
            return values.set(index, value);
        } else {
            final int newIndex = keys.add(key);
            return values.add(newIndex, value);
        }
    }

    @Override
    public Mutable<?, ?> remove(Mutable<?, ?> key) {
        final int index = keys.remove(key);
        if(-1 < index) {
            return values.remove(index);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<Mutable<?, ?>> mutableKeySetIterator() {
        return new Iterator<Mutable<?, ?>>() {
            int version = versionCounter.getAsInt();
            int index = -1;

            @Override
            public boolean hasNext() {
                if(version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                return index++ < size();
            }

            @Override
            public Mutable<?, ?> next() {
                if(version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                return keys.get(index);
            }

            @Override
            public void remove() {
                if(version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                BackedByNonBoxingListsNonBoxingMap.this.remove(index);
                version = versionCounter.getAsInt();
            }
        };
    }

    @Override
    public Iterator<Mutable<?, ?>> mutableValuesIterator() {
        return new Iterator<Mutable<?, ?>>() {
            int version = versionCounter.getAsInt();
            int index = -1;

            @Override
            public boolean hasNext() {
                if(version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                return index++ < size();
            }

            @Override
            public Mutable<?, ?> next() {
                if(version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                return values.get(index);
            }

            @Override
            public void remove() {
                if(version == versionCounter.getAsInt()) {
                    throw new ConcurrentModificationException();
                }
                BackedByNonBoxingListsNonBoxingMap.this.remove(index);
                version = versionCounter.getAsInt();
            }
        };
    }

    @Override
    public Iterator<Map.Entry<Mutable<?, ?>, Mutable<?, ?>>> mutableEntrySetIterator() {
        return null;
    }
//
//    @Override
//    public Iterator<Map.Entry<Mutable<?, ?>, Mutable<?, ?>>> entrySetIterator() {
//        return new Iterator<Map.Entry<Mutable<?, ?>, Mutable<?, ?>>>() {
//            private MutableEntry mutableEntry = new MutableEntry();
//            private int version = versionCounter.getAsInt();
//            private int index = -1;
//            
//            @Override
//            public boolean hasNext() {
//                if(version == versionCounter.getAsInt()) {
//                    throw new ConcurrentModificationException();
//                }
//                return index++ < size();
//            }
//
//            @Override
//            public Map.Entry<Mutable<?, ?>, Mutable<?, ?>> next() {
//                if(version == versionCounter.getAsInt()) {
//                    throw new ConcurrentModificationException();
//                }
//                mutableEntry.setKeyWithoutUpdate(keys.get(index));
//                mutableEntry.setIndex(index);
//                
//                Mutable<?, ?> oldValue = values.get(index, mutableSupplier);
//                mutableEntry.setOldValue(oldValue);
//                
//                VersionedMutable<?, ?> versionedValue = (VersionedMutable<?, ?>) values.get(index, versionedMutableSupplier);
//                mutableEntry.setValueWithoutUpdate(versionedValue);
//                versionedValue.resetVersion();
//                mutableEntry.setVersionOfValue(versionedValue.getVersion());
//                
//                return mutableEntry;
//            }
//
//            @Override
//            public void remove() {
//                if(version == versionCounter.getAsInt()) {
//                    throw new ConcurrentModificationException();
//                }
//                BackedByNonBoxingListsNonBoxingMap.this.remove(index);
//                version = versionCounter.getAsInt();
//            }
//        };
//    }

    @Override
    public void trimToSize() {
        keys.trimToSize();
        values.trimToSize();
    }

//    @Override
    public int size() {
        return keys.size();
    }

//    @Override
    public boolean isEmpty() {
        return size()==0;
    }
//
//    @Override
    public boolean containsKey(Object key) {
        return containsKey(toMutable(key));
    }
//
//    @Override
    public boolean containsValue(Object value) {
        return containsValue(toMutable(value));
    }
//
//    @Override
    public Object get(Object key) {
        final Mutable<?, ?> returned = get(toMutable(key));
        if(returned != null) {
            return returned.get();
        } else {
            return null;
        }
    }
//
//    @Override
    public Object put(Object key, Object value) {
        final Mutable<?, ?> returned = put(toMutable(key), toMutable(value));
        if(returned != null) {
            return returned.get();
        } else {
            return null;
        }
    }
//
//    @Override
    public Object remove(Object key) {
        final Mutable<?, ?> returned = remove(toMutable(key));
        if(returned != null) {
            return returned.get();
        } else {
            return null;
        }
    }
//
//    @Override
//    public void putAll(Map<?, ?> map) {
//        //TODO: avoid boxing if possible
//        for (Map.Entry<?, ?> entry : map.entrySet()) {
//            put(entry.getKey(), entry.getValue());
//        }
//    }
//
//    @Override
//    public void clear() {
//        keys.clear();
//        values.clear();
//    }
//
//    @Override
//    public Set<Object> keySet() {
//        return null;
//    }
//
//    @Override
//    public Collection<Object> values() {
//        return null;
//    }
//
//    @Override
//    public Set<Entry<Object, Object>> entrySet() {
//        return null;
//    }
//    
    private Mutable<?, ?> toMutable(Object key) {
        return null;
    }
    
    class MutableEntry implements Map.Entry<Mutable<?, ?>, Mutable<?, ?>>  {
        private Mutable<?, ?> key;
        private Mutable<?, ?> oldValue;
        private VersionedMutable<?, ?> value;
        private int versionOfValue;
        private int index;
        
        @Override
        public Mutable<?, ?> getKey() {
            return key;
        }

        @Override
        public Mutable<?, ?> getValue() {
            return value;
        }

        public void setOldValue(Mutable<?, ?> oldValue) {
            this.oldValue = oldValue;
        }

        public void setVersionOfValue(int versionOfValue) {
            this.versionOfValue = versionOfValue;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Mutable<?, ?> setKeyWithoutUpdate(Mutable<?, ?> key) {
            Mutable<?, ?> oldKey = key;
            this.key = key;
            return oldKey;
        }

        public void setValueWithoutUpdate(VersionedMutable<?, ?> value) {
            this.value = value;
        }

        @Override
        public Mutable<?, ?> setValue(Mutable<?, ?> value) {
            if(this.value == value && versionOfValue != this.value.getVersion()) {
                values.set(index, value);
                return oldValue;
            } else if(this.value != value && !this.value.equals(value)) {
                values.set(index, value);
            }
            return oldValue;
        }
    }
}
