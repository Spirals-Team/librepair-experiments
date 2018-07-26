package com.github.powerlibraries.primitive;


import java.nio.IntBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.powerlibraries.primitive.collections.AbstractIntList;
import com.github.powerlibraries.primitive.collections.IntCollection;
import com.github.powerlibraries.primitive.collections.IntList;
import com.github.powerlibraries.primitive.collections.IntListIterator;
import com.github.powerlibraries.primitive.common.IntPointer;

public class SimpleIntList extends AbstractIntList {

	private List<Integer> l = new ArrayList<>();

	@Override
	public int getInt(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int setInt(int index, int element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addInt(int index, int element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOfInt(int o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOfInt(int o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IntList subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IntListIterator listIterator(int index) {
		ListIterator<Integer> it = l.listIterator(index);
		return new IntListIterator() {
			
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
			public void setInt(int e) {
				it.set(e);
			}
			
			@Override
			public int previousInt() {
				return it.previous();
			}
			
			@Override
			public int nextInt() {
				return it.next();
			}
			
			@Override
			public void addInt(int e) {
				it.add(e);
			}
		};
	}

	@Override
	public Iterable<IntPointer> primitiveIterable(int index) {
		ListIterator<Integer> it = l.listIterator(index);
		return new Iterable<IntPointer>() {
			public Iterator<IntPointer> iterator() {
        		return new Iterator<IntPointer>() {
        			
        			@Override
        			public void remove() {
        				it.remove();
        			}
        			
        			@Override
        			public boolean hasNext() {
        				return it.hasNext();
        			}
        			
        			@Override
        			public IntPointer next() {
        				return new IntPointer() {
        					public int get() {
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
	public int removeAt(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public IntBuffer asBuffer() {
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
	public boolean addAll(Collection<? extends Integer> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Integer> c) {
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
	public boolean containsInt(int o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int[] toIntArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addInt(int e) {
		return l.add(e);
	}

	@Override
	public boolean removeInt(int o) {
		return l.remove((Integer)o);
	}

	@Override
	public boolean addAllInts(IntCollection c) {
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