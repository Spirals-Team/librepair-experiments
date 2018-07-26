package com.github.powerlibraries.primitive;


import java.nio.FloatBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.powerlibraries.primitive.collections.AbstractFloatList;
import com.github.powerlibraries.primitive.collections.FloatCollection;
import com.github.powerlibraries.primitive.collections.FloatList;
import com.github.powerlibraries.primitive.collections.FloatListIterator;
import com.github.powerlibraries.primitive.common.FloatPointer;

public class SimpleFloatList extends AbstractFloatList {

	private List<Float> l = new ArrayList<>();

	@Override
	public float getFloat(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float setFloat(int index, float element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addFloat(int index, float element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOfFloat(float o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOfFloat(float o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FloatList subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FloatListIterator listIterator(int index) {
		ListIterator<Float> it = l.listIterator(index);
		return new FloatListIterator() {
			
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
			public void setFloat(float e) {
				it.set(e);
			}
			
			@Override
			public float previousFloat() {
				return it.previous();
			}
			
			@Override
			public float nextFloat() {
				return it.next();
			}
			
			@Override
			public void addFloat(float e) {
				it.add(e);
			}
		};
	}

	@Override
	public Iterable<FloatPointer> primitiveIterable(int index) {
		ListIterator<Float> it = l.listIterator(index);
		return new Iterable<FloatPointer>() {
			public Iterator<FloatPointer> iterator() {
        		return new Iterator<FloatPointer>() {
        			
        			@Override
        			public void remove() {
        				it.remove();
        			}
        			
        			@Override
        			public boolean hasNext() {
        				return it.hasNext();
        			}
        			
        			@Override
        			public FloatPointer next() {
        				return new FloatPointer() {
        					public float get() {
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
	public float removeAt(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public FloatBuffer asBuffer() {
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
	public boolean addAll(Collection<? extends Float> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Float> c) {
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
	public boolean containsFloat(float o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float[] toFloatArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addFloat(float e) {
		return l.add(e);
	}

	@Override
	public boolean removeFloat(float o) {
		return l.remove((Float)o);
	}

	@Override
	public boolean addAllFloats(FloatCollection c) {
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