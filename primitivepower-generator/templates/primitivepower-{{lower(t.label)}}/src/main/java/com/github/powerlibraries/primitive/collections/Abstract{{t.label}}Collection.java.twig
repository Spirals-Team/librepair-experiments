package com.github.powerlibraries.primitive.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.github.powerlibraries.primitive.common.{{t.label}}Pointer;

public abstract class Abstract{{t.label}}Collection{{t.generic}} implements {{t.label}}Collection{{t.generic}} {
	
	protected Abstract{{t.label}}Collection() {}
	
	@Override @Deprecated
	public boolean contains(Object o) {
		{{t.returnOnInvalidValue('o','false',2)}}
		return contains{{t.label}}(({{t.boxed}})o);
	}

	@Override @Deprecated
	public boolean add({{t.boxed}} e) {
		return add{{t.label}}(e);
	}

	@Override @Deprecated
	public boolean remove(Object o) {
		{{t.returnOnInvalidValue('o','false',2)}}
		return remove{{t.label}}(({{t.boxed}})o);
	}

	@Override{% if t.primitive %} @Deprecated{% endif %}
	public {{t.boxed}}[] toArray(IntFunction<{{t.boxed}}[]> p) {
		return toArray(p.apply(this.size()));
	}
	
	@Override @Deprecated
	public boolean containsAll(Collection<?> c) {
		for(Object o:c) {
			if(!this.contains(o)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean containsAll{{t.label}}s({{t.label}}Collection{{t.extendedGeneric}} c) {
		for({{t.label}}Pointer{{t.extendedGeneric}} o:c.primitiveIterable()) {
			if(!this.contains{{t.label}}(o.get())) {
				return false;
			}
		}
		return true;
	}
	{% if t.primitive %}
	@Override
	public boolean removeAll{{t.label}}s({{t.label}}Collection{{t.extendedGeneric}} c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator<{{t.label}}Pointer{{t.generic}}> iterator = primitiveIterable().iterator();
		while (iterator.hasNext()) {
			if (c.contains{{t.label}}(iterator.next().get())) {
				iterator.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAll{{t.label}}s({{t.label}}Collection{{t.extendedGeneric}} c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator<{{t.label}}Pointer{{t.generic}}> iterator = primitiveIterable().iterator();
		while (iterator.hasNext()) {
			if (!c.contains{{t.label}}(iterator.next().get())) {
				iterator.remove();
				modified = true;
			}
		}
		return modified;
	}
	{% else %}
	@Override
	public boolean removeAll{{t.label}}s({{t.label}}Collection{{t.extendedGeneric}} c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator{{t.generic}} iterator = iterator();
		while (iterator.hasNext()) {
			if (c.contains(iterator.next())) {
				iterator.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAll{{t.label}}s({{t.label}}Collection{{t.extendedGeneric}} c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator{{t.generic}} iterator = iterator();
		while (iterator.hasNext()) {
			if (!c.contains(iterator.next())) {
				iterator.remove();
				modified = true;
			}
		}
		return modified;
	}
	{% endif %}
	
	/**
	 * Returns a string representation of this collection.  The string
	 * representation consists of a list of the collection's elements in the
	 * order they are returned by its iterator, enclosed in square brackets
	 * (<tt>"[]"</tt>).  Adjacent elements are separated by the characters
	 * <tt>", "</tt> (comma and space).  Elements are converted to strings as
	 * by {@link String#valueOf(Object)}.
	 *
	 * @return a string representation of this collection
	 */
	public String toString() {
		Iterator<{{t.label}}Pointer{{t.generic}}> iterator = primitiveIterable().iterator();
		if (! iterator.hasNext()) {
			return "[]";
		}

		StringBuilder result = new StringBuilder();
		result.append('[');
		for (;;) {
			result.append(iterator.next().get());
			if (! iterator.hasNext()) {
				return result.append(']').toString();
			}
			result.append(", ");
		}
	}
	{% if t.streamSupport %}
	@Override @Deprecated
	public Stream<{{t.boxed}}> stream() {
		return this.stream{{t.label}}s().boxed();
	}

	@Override @Deprecated
	public Stream<{{t.boxed}}> parallelStream() {
		return this.parallelStream{{t.label}}s().boxed();
	}
	{% endif %}
	@Override{% if t.streamSupport %} @Deprecated{% endif %}
	public boolean removeIf(Predicate<? super {{t.boxed}}> filter) {
		Objects.requireNonNull(filter);
		Iterator<{{t.boxed}}> it = this.iterator();
		boolean changed = false;
		while(it.hasNext()) {
			if(filter.test(it.next())) {
				changed = true;
				it.remove();
			}
		}
		return changed;
	}
}