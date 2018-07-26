package com.github.powerlibraries.primitive;


import java.nio.ByteBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.github.powerlibraries.primitive.collections.AbstractByteList;
import com.github.powerlibraries.primitive.collections.ByteCollection;
import com.github.powerlibraries.primitive.collections.ByteList;
import com.github.powerlibraries.primitive.collections.ByteListIterator;
import com.github.powerlibraries.primitive.common.BytePointer;

public class SimpleByteList extends AbstractByteList {

	private List<Byte> l = new ArrayList<>();

	@Override
	public byte getByte(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte setByte(int index, byte element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addByte(int index, byte element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOfByte(byte o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOfByte(byte o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteList subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteListIterator listIterator(int index) {
		ListIterator<Byte> it = l.listIterator(index);
		return new ByteListIterator() {
			
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
			public void setByte(byte e) {
				it.set(e);
			}
			
			@Override
			public byte previousByte() {
				return it.previous();
			}
			
			@Override
			public byte nextByte() {
				return it.next();
			}
			
			@Override
			public void addByte(byte e) {
				it.add(e);
			}
		};
	}

	@Override
	public Iterable<BytePointer> primitiveIterable(int index) {
		ListIterator<Byte> it = l.listIterator(index);
		return new Iterable<BytePointer>() {
			public Iterator<BytePointer> iterator() {
        		return new Iterator<BytePointer>() {
        			
        			@Override
        			public void remove() {
        				it.remove();
        			}
        			
        			@Override
        			public boolean hasNext() {
        				return it.hasNext();
        			}
        			
        			@Override
        			public BytePointer next() {
        				return new BytePointer() {
        					public byte get() {
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
	public byte removeAt(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ByteBuffer asBuffer() {
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
	public boolean addAll(Collection<? extends Byte> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Byte> c) {
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
	public boolean containsByte(byte o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] toByteArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addByte(byte e) {
		return l.add(e);
	}

	@Override
	public boolean removeByte(byte o) {
		return l.remove((Byte)o);
	}

	@Override
	public boolean addAllBytes(ByteCollection c) {
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