package guru.bonacci.spectre.spectreshared.collections;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.map.PassiveExpiringMap;

public class CustomPassiveExpiringCollection<E> implements Serializable {

	/** Serialization version */
	private static final long serialVersionUID = 1L;

	/** 
	 * Map used to manage expiration times for the actual map entries. 
	 * 
	 * Out of sheer laziness we simply use the values of PassiveExpiringMap to make it work.
	 * To avoid key conflicts an unused UUID.
	 */
	private PassiveExpiringMap<UUID,E> decoratorMap;

	/**
	 * Never expiring
	 */
	public CustomPassiveExpiringCollection() {
		decoratorMap = new PassiveExpiringMap<>();
	}

	/**
	 * Construct a map decorator using the given time-to-live value measured in
	 * milliseconds
	 */
	public CustomPassiveExpiringCollection(long timeToLiveMillis) {
		decoratorMap = new PassiveExpiringMap<>(timeToLiveMillis);
	}

	/**
	 * Construct a map decorator that decorates the given collection using the given
	 * time-to-live value measured in milliseconds
	 */
	public CustomPassiveExpiringCollection(long timeToLiveMillis, Collection<E> c) {
		this(timeToLiveMillis);
		this.addAll(c);
	}

	/**
	 * Never expiring collection
	 */
	public CustomPassiveExpiringCollection(Collection<E> c) {
		this();
		this.addAll(c);
	}

	public void add(E e) {
		decoratorMap.put(randomUUID(), e);
	}

	public void addAll(Collection<E> c) {
		Map<UUID,E> toCopy = c.stream().collect(toMap(e -> randomUUID(), identity()));
		decoratorMap.putAll(toCopy);
	}

	public void clear() {
		decoratorMap.clear();
	}

	public boolean contains(Object o) {
		return decoratorMap.containsValue(o);
	}

	public boolean isEmpty() {
		return decoratorMap.isEmpty();
	}

	public boolean remove(Object o) {
		return decoratorMap.values().remove(o);
	}

	public int size() {
		return decoratorMap.size();
	}

	public Collection<E> values() {
		return decoratorMap.values();
	}
}
