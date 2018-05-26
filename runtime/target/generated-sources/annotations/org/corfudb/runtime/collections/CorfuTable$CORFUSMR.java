package org.corfudb.runtime.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.lang.Comparable;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import org.corfudb.annotations.Accessor;
import org.corfudb.annotations.ConflictParameter;
import org.corfudb.annotations.InstrumentedCorfuObject;
import org.corfudb.annotations.Mutator;
import org.corfudb.annotations.MutatorAccessor;
import org.corfudb.annotations.TransactionalMethod;
import org.corfudb.runtime.object.ICorfuSMR;
import org.corfudb.runtime.object.ICorfuSMRProxy;
import org.corfudb.runtime.object.ICorfuSMRUpcallTarget;
import org.corfudb.runtime.object.IUndoFunction;
import org.corfudb.runtime.object.IUndoRecordFunction;

@InstrumentedCorfuObject
public class CorfuTable$CORFUSMR<K, V> extends CorfuTable<K, V> implements ICorfuSMR<CorfuTable<K, V>> {
  public ICorfuSMRProxy<CorfuTable<K, V>> proxy_CORFUSMR;

  public final Map<String, ICorfuSMRUpcallTarget<CorfuTable<K, V>>> upcallMap_CORFUSMR = new ImmutableMap.Builder<String, ICorfuSMRUpcallTarget<CorfuTable<K, V>>>()
  .put("remove", (obj, args) -> { return obj.remove((java.lang.Object) args[0]);})
  .put("putAll", (obj, args) -> { obj.putAll((java.util.Map<? extends K,? extends V>) args[0]);return null;})
  .put("clear", (obj, args) -> { obj.clear();return null;})
  .put("put", (obj, args) -> { return obj.put((K) args[0], (V) args[1]);}).build();

  public final Map<String, IUndoRecordFunction<CorfuTable<K, V>>> undoRecordMap_CORFUSMR = new ImmutableMap.Builder<String, IUndoRecordFunction<CorfuTable<K, V>>>()
  .put("remove", (obj, args) -> { return this.undoRemoveRecord(obj,(K) args[0]);})
  .put("putAll", (obj, args) -> { return this.undoPutAllRecord(obj,(java.util.Map<? extends K,? extends V>) args[0]);})
  .put("put", (obj, args) -> { return this.undoPutRecord(obj,(K) args[0], (V) args[1]);}).build();

  public final Map<String, IUndoFunction<CorfuTable<K, V>>> undoMap_CORFUSMR = new ImmutableMap.Builder<String, IUndoFunction<CorfuTable<K, V>>>()
  .put("remove", (obj, undoRecord, args) -> {this.undoRemove(obj, (V) undoRecord, (K) args[0]);})
  .put("putAll", (obj, undoRecord, args) -> {this.undoPutAll(obj, (java.util.Map<K, V>) undoRecord, (java.util.Map<? extends K,? extends V>) args[0]);})
  .put("put", (obj, undoRecord, args) -> {this.undoPut(obj, (V) undoRecord, (K) args[0], (V) args[1]);}).build();

  public final Set<String> resetSet_CORFUSMR = new ImmutableSet.Builder<String>()
  .add("clear").build();

  public CorfuTable$CORFUSMR(CorfuTable.IndexRegistry<K, V> indices) {
    super(indices);
  }

  public CorfuTable$CORFUSMR() {
    super();
  }

  public ICorfuSMRProxy<CorfuTable<K, V>> getCorfuSMRProxy() {
    return proxy_CORFUSMR;
  }

  public void setCorfuSMRProxy(ICorfuSMRProxy<CorfuTable<K, V>> proxy) {
    this.proxy_CORFUSMR = proxy;
  }

  @Override
  @Accessor
  public int size() {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.size();},null);
  }

  @Override
  @TransactionalMethod
  public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
    return proxy_CORFUSMR.TXExecute(() -> {return super.computeIfAbsent(key, mappingFunction);
    });}

  @Override
  @Accessor
  public V getOrDefault(Object key, V defaultValue) {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.getOrDefault(key, defaultValue);},null);
  }

  @Override
  @Mutator(
      name = "remove",
      noUpcall = true
  )
  public void delete(@ConflictParameter K key) {
    Object[] conflictField_CORFUSMR = new Object[]{key};
    proxy_CORFUSMR.logUpdate("remove",false,conflictField_CORFUSMR,key);
  }

  @Override
  @Accessor
  @Nonnull
  public Set<K> keySet() {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.keySet();},null);
  }

  @Override
  @Accessor
  public boolean hasSecondaryIndices() {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.hasSecondaryIndices();},null);
  }

  @Override
  @TransactionalMethod
  public boolean remove(Object key, Object value) {
    return proxy_CORFUSMR.TXExecute(() -> {return super.remove(key, value);
    });}

  @Override
  @SuppressWarnings("unchecked")
  @Accessor
  @Nonnull
  public <I extends Comparable<I>> Collection<Map.Entry<K, V>> getByIndex(@Nonnull CorfuTable.IndexName indexName, I indexKey) {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.getByIndex(indexName, indexKey);},null);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void unmapSecondaryIndexes(K key, V value) {
    proxy_CORFUSMR.access(o_CORFUSMR -> {o_CORFUSMR.unmapSecondaryIndexes(key, value);return null;},null);
  }

  @Override
  @TransactionalMethod
  public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    return proxy_CORFUSMR.TXExecute(() -> {return super.computeIfPresent(key, remappingFunction);
    });}

  @Override
  @Accessor
  public V get(@ConflictParameter Object key) {
    Object[] conflictField_CORFUSMR = new Object[]{key};
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.get(key);},conflictField_CORFUSMR);
  }

  @Override
  @TransactionalMethod
  public V replace(K key, V value) {
    return proxy_CORFUSMR.TXExecute(() -> {return super.replace(key, value);
    });}

  @Override
  @Accessor
  public void forEach(BiConsumer<? super K, ? super V> action) {
    proxy_CORFUSMR.access(o_CORFUSMR -> {o_CORFUSMR.forEach(action);return null;},null);
  }

  @Override
  public int hashCode() {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.hashCode();},null);
  }

  @Override
  public String toString() {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.toString();},null);
  }

  @Override
  @Accessor
  public boolean containsKey(@ConflictParameter Object key) {
    Object[] conflictField_CORFUSMR = new Object[]{key};
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.containsKey(key);},conflictField_CORFUSMR);
  }

  @Override
  @Accessor
  public boolean containsValue(Object value) {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.containsValue(value);},null);
  }

  @Override
  @Accessor
  @Nonnull
  public Set<Map.Entry<K, V>> entrySet() {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.entrySet();},null);
  }

  @Override
  @Accessor
  public List<V> scanAndFilter(Predicate<? super V> p) {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.scanAndFilter(p);},null);
  }

  @Override
  @MutatorAccessor(
      name = "remove",
      undoFunction = "undoRemove",
      undoRecordFunction = "undoRemoveRecord"
  )
  @SuppressWarnings("unchecked")
  public V remove(@ConflictParameter Object key) {
    Object[] conflictField_CORFUSMR = new Object[]{key};
    long address_CORFUSMR = proxy_CORFUSMR.logUpdate("remove",true,conflictField_CORFUSMR,key);
    return (V) proxy_CORFUSMR.getUpcallResult(address_CORFUSMR, conflictField_CORFUSMR);
  }

  @Override
  @Accessor
  public boolean isEmpty() {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.isEmpty();},null);
  }

  @Override
  @Mutator(
      name = "putAll",
      undoFunction = "undoPutAll",
      undoRecordFunction = "undoPutAllRecord",
      conflictParameterFunction = "putAllConflictFunction"
  )
  public void putAll(@Nonnull Map<? extends K, ? extends V> m) {
    Object[] conflictField_CORFUSMR = putAllConflictFunction(m);
    proxy_CORFUSMR.logUpdate("putAll",false,conflictField_CORFUSMR,m);
  }

  @Override
  @TransactionalMethod
  public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
    proxy_CORFUSMR.TXExecute(() -> {super.replaceAll(function);
    return null; });}

  @Override
  @Mutator(
      name = "clear",
      reset = true
  )
  public void clear() {
    proxy_CORFUSMR.logUpdate("clear",false,null);
  }

  @Override
  @TransactionalMethod
  public V putIfAbsent(K key, V value) {
    return proxy_CORFUSMR.TXExecute(() -> {return super.putIfAbsent(key, value);
    });}

  @Override
  public boolean equals(Object arg0) {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.equals(arg0);},null);
  }

  @Override
  @Accessor
  public Collection<Map.Entry<K, V>> scanAndFilterByEntry(Predicate<? super Map.Entry<K, V>> entryPredicate) {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.scanAndFilterByEntry(entryPredicate);},null);
  }

  @Override
  @Accessor
  @Nonnull
  public Collection<V> values() {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.values();},null);
  }

  @Override
  @MutatorAccessor(
      name = "put",
      undoFunction = "undoPut",
      undoRecordFunction = "undoPutRecord"
  )
  public V put(@ConflictParameter K key, V value) {
    Object[] conflictField_CORFUSMR = new Object[]{key};
    long address_CORFUSMR = proxy_CORFUSMR.logUpdate("put",true,conflictField_CORFUSMR,key, value);
    return (V) proxy_CORFUSMR.getUpcallResult(address_CORFUSMR, conflictField_CORFUSMR);
  }

  @Override
  @Accessor
  @Nonnull
  public <I extends Comparable<I>> Collection<Map.Entry<K, V>> getByIndexAndFilter(@Nonnull CorfuTable.IndexName indexName, @Nonnull Predicate<? super Map.Entry<K, V>> entryPredicate, I indexKey) {
    return proxy_CORFUSMR.access(o_CORFUSMR -> {return o_CORFUSMR.getByIndexAndFilter(indexName, entryPredicate, indexKey);},null);
  }

  @Override
  @TransactionalMethod
  public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
    return proxy_CORFUSMR.TXExecute(() -> {return super.merge(key, value, remappingFunction);
    });}

  @Override
  @SuppressWarnings("unchecked")
  protected void mapSecondaryIndexes(K key, V value) {
    proxy_CORFUSMR.access(o_CORFUSMR -> {o_CORFUSMR.mapSecondaryIndexes(key, value);return null;},null);
  }

  @Override
  @Mutator(
      name = "put",
      noUpcall = true
  )
  public void insert(@ConflictParameter K key, V value) {
    Object[] conflictField_CORFUSMR = new Object[]{key};
    proxy_CORFUSMR.logUpdate("put",false,conflictField_CORFUSMR,key, value);
  }

  public Map<String, ICorfuSMRUpcallTarget<CorfuTable<K, V>>> getCorfuSMRUpcallMap() {
    return upcallMap_CORFUSMR;
  }

  public Map<String, IUndoRecordFunction<CorfuTable<K, V>>> getCorfuUndoRecordMap() {
    return undoRecordMap_CORFUSMR;
  }

  public Map<String, IUndoFunction<CorfuTable<K, V>>> getCorfuUndoMap() {
    return undoMap_CORFUSMR;
  }

  public Set<String> getCorfuResetSet() {
    return resetSet_CORFUSMR;
  }
}
