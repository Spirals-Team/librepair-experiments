package com.github.powerlibraries.primitive.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.github.powerlibraries.primitive.common.BytePointer;

public abstract class AbstractByteCollection implements ByteCollection {
	
	protected AbstractByteCollection() {}
	
	@Override @Deprecated
	public boolean contains(Object o) {
		if(!(o instanceof Byte)) {
			return false;
		}
		return containsByte((Byte)o);
	}

	@Override @Deprecated
	public boolean add(Byte e) {
		return addByte(e);
	}

	@Override @Deprecated
	public boolean remove(Object o) {
		if(!(o instanceof Byte)) {
			return false;
		}
		return removeByte((Byte)o);
	}

	@Override @Deprecated
	public Byte[] toArray(IntFunction<Byte[]> p) {
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
	public boolean containsAllBytes(ByteCollection c) {
		for(BytePointer o:c.primitiveIterable()) {
			if(!this.containsByte(o.get())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean removeAllBytes(ByteCollection c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator<BytePointer> iterator = primitiveIterable().iterator();
		while (iterator.hasNext()) {
			if (c.containsByte(iterator.next().get())) {
				iterator.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAllBytes(ByteCollection c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator<BytePointer> iterator = primitiveIterable().iterator();
		while (iterator.hasNext()) {
			if (!c.containsByte(iterator.next().get())) {
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
		Iterator<BytePointer> iterator = primitiveIterable().iterator();
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
	public boolean removeIf(Predicate<? super Byte> filter) {
		Objects.requireNonNull(filter);
		Iterator<Byte> it = this.iterator();
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