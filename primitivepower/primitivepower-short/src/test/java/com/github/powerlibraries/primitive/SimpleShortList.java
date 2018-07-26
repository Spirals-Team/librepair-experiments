package com.github.powerlibraries.primitive;


import java.nio.ShortBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.powerlibraries.primitive.collections.AbstractShortList;
import com.github.powerlibraries.primitive.collections.ShortCollection;
import com.github.powerlibraries.primitive.collections.ShortList;
import com.github.powerlibraries.primitive.collections.ShortListIterator;
import com.github.powerlibraries.primitive.common.ShortPointer;

public class SimpleShortList extends AbstractShortList {

	private List<Short> l = new ArrayList<>();

	@Override
	public short getShort(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public short setShort(int index, short element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addShort(int index, short element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOfShort(short o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOfShort(short o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ShortList subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ShortListIterator listIterator(int index) {
		ListIterator<Short> it = l.listIterator(index);
		return new ShortListIterator() {
			
			@Override
			public void remove() {
				it.remove();
			}
			
			@Override
			public int previousIndex() {
				return it.previousIndex();
			}
			
			@Override
			public int nextIndex() {
				return it.nextIndex();
			}
			
			@Override
			public boolean hasPrevious() {
				return it.hasPrevious();
			}
			
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}
			
			@Override
			public void setShort(short e) {
				it.set(e);
			}
			
			@Override
			public short previousShort() {
				return it.previous();
			}
			
			@Override
			public short nextShort() {
				return it.next();
			}
			
			@Override
			public void addShort(short e) {
				it.add(e);
			}
		};
	}

	@Override
	public Iterable<ShortPointer> primitiveIterable(int index) {
		ListIterator<Short> it = l.listIterator(index);
		return new Iterable<ShortPointer>() {
			public Iterator<ShortPointer> iterator() {
        		return new Iterator<ShortPointer>() {
        			
        			@Override
        			public void remove() {
        				it.remove();
        			}
        			
        			@Override
        			public boolean hasNext() {
        				return it.hasNext();
        			}
        			
        			@Override
        			public ShortPointer next() {
        				return new ShortPointer() {
        					public short get() {
        						return it.next();
        					}
        				};
        			}
        		};
        	}
		};
	}

	@Override
	public void reverse() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short removeAt(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ShortBuffer asBuffer() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int size() {
		return l.size();
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		return l.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Short> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Short> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsShort(short o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public short[] toShortArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addShort(short e) {
		return l.add(e);
	}

	@Override
	public boolean removeShort(short o) {
		return l.remove((Short)o);
	}

	@Override
	public boolean addAllShorts(ShortCollection c) {
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public void sort() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void parallelSort() {
		throw new UnsupportedOperationException();
	}
	
}