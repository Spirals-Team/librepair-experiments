package com.github.powerlibraries.primitive.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.github.powerlibraries.primitive.common.BooleanPointer;

public abstract class AbstractBooleanCollection implements BooleanCollection {
	
	protected AbstractBooleanCollection() {}
	
	@Override @Deprecated
	public boolean contains(Object o) {
		if(!(o instanceof Boolean)) {
			return false;
		}
		return containsBoolean((Boolean)o);
	}

	@Override @Deprecated
	public boolean add(Boolean e) {
		return addBoolean(e);
	}

	@Override @Deprecated
	public boolean remove(Object o) {
		if(!(o instanceof Boolean)) {
			return false;
		}
		return removeBoolean((Boolean)o);
	}

	@Override @Deprecated
	public Boolean[] toArray(IntFunction<Boolean[]> p) {
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
	public boolean containsAllBooleans(BooleanCollection c) {
		for(BooleanPointer o:c.primitiveIterable()) {
			if(!this.containsBoolean(o.get())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean removeAllBooleans(BooleanCollection c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator<BooleanPointer> iterator = primitiveIterable().iterator();
		while (iterator.hasNext()) {
			if (c.containsBoolean(iterator.next().get())) {
				iterator.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAllBooleans(BooleanCollection c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator<BooleanPointer> iterator = primitiveIterable().iterator();
		while (iterator.hasNext()) {
			if (!c.containsBoolean(iterator.next().get())) {
				iterator.remove();
				modified = true;
			}
		}
		return modified;
	}
	
	
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
		Iterator<BooleanPointer> iterator = primitiveIterable().iterator();
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
	
	@Override
	public boolean removeIf(Predicate<? super Boolean> filter) {
		Objects.requireNonNull(filter);
		Iterator<Boolean> it = this.iterator();
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