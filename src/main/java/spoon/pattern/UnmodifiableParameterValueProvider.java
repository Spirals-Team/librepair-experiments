/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import spoon.SpoonException;

/**
 * Provides value of parameter
 */
public class UnmodifiableParameterValueProvider implements ParameterValueProvider {

	public static class Factory implements ParameterValueProviderFactory {
		public static final Factory INSTANCE = new Factory();
		@Override
		public ParameterValueProvider createParameterValueProvider() {
			return new UnmodifiableParameterValueProvider();
		}
	}

	private ParameterInfoProvider parameterInfos;
	protected final Map<String, Object> map;

	public UnmodifiableParameterValueProvider(Map<String, Object> parent) {
		this.map = Collections.unmodifiableMap(parent);
	}

	public UnmodifiableParameterValueProvider(Map<String, Object> parent, String parameterName, Object value) {
		Map<String, Object> copy = new HashMap<>(parent.size() + 1);
		copy.putAll(parent);
		copy.put(parameterName, value);
		this.map = Collections.unmodifiableMap(copy);
	}

	public UnmodifiableParameterValueProvider() {
		this.map = Collections.emptyMap();
	}

	@Override
	public Object get(String parameterName) {
		return map.get(parameterName);
	}

	@Override
	public UnmodifiableParameterValueProvider put(String parameterName, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ParameterValueProvider putIntoCopy(String parameterName, Object value) {
		return new UnmodifiableParameterValueProvider(map, parameterName, value);
	}

	public ParameterInfoProvider getParameterInfos() {
		if (parameterInfos == null) {
			throw new SpoonException("parameterInfos must be set first");
		}
		return parameterInfos;
	}

	public void setParameterInfos(ParameterInfoProvider parameterInfos) {
		this.parameterInfos = parameterInfos;
	}

	@Override
	public void forEach(BiConsumer<? super String, Object> consumer) {
		map.forEach(consumer);
	}

	@Override
	public String toString() {
		List<String> paramNames = new ArrayList<>(keySet());
		paramNames.sort((a, b) -> a.compareTo(b));
		StringBuilder sb = new StringBuilder();
		for (String name : paramNames) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(name).append('=').append(get(name));
		}
		return sb.toString();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return get((String) key);
	}

	/**
	 * we can remove only local parameter values. The entries inherited from parent cannot be removed
	 */
	@Override
	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		throw new UnsupportedOperationException();
	}

	/**
	 * we can remove only local parameter values. The entries inherited from parent cannot be removed
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<Object> values() {
		return map.values();
	}
}
