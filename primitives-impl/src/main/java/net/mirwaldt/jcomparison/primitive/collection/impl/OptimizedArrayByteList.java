package net.mirwaldt.jcomparison.primitive.collection.impl;


import org.apache.commons.collections.primitives.ByteCollection;
import org.apache.commons.collections.primitives.ByteList;
import org.apache.commons.collections.primitives.RandomAccessByteList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class OptimizedArrayByteList extends RandomAccessByteList implements ByteList, Serializable {
    private transient byte[] _data;
    private int _size;

    public OptimizedArrayByteList() {
        this(8);
    }

    public OptimizedArrayByteList(int initialCapacity) {
        this._data = null;
        this._size = 0;
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("capacity " + initialCapacity);
        } else {
            this._data = new byte[initialCapacity];
            this._size = 0;
        }
    }

    public OptimizedArrayByteList(ByteCollection that) {
        this(that.size());
        this.addAll(that);
    }

    public byte get(int index) {
        this.checkRange(index);
        return this._data[index];
    }

    public int size() {
        return this._size;
    }

    public byte removeElementAt(int index) {
        this.checkRange(index);
        this.incrModCount();
        byte oldval = this._data[index];
        int numtomove = this._size - index - 1;
        if (numtomove > 0) {
            System.arraycopy(this._data, index + 1, this._data, index, numtomove);
        }

        --this._size;
        return oldval;
    }

    public byte[] removeElementsFromTo(int fromIndex, int toIndex, boolean returnArray) {
        if (fromIndex < 0 || this._size < fromIndex) {
            throw new IndexOutOfBoundsException("From-index should be at least 0 and less than " + this._size + ", found " + fromIndex);
        }
        if (toIndex < 1 || this._size <= toIndex) {
            throw new IndexOutOfBoundsException("To-index should be at least 1 and less than or equal to " + this._size + ", found " + toIndex);
        }
        if (toIndex <= fromIndex) {
            throw new IndexOutOfBoundsException("From-index must lie before to-index."
                    + "from-index=" + fromIndex + ", to-index=" + toIndex);
        }

        int length = toIndex - fromIndex;
        byte[] removedBytes = null;
        
        this.incrModCount();
        if (0 < length) {
            if (returnArray) {
                removedBytes = new byte[length];
                System.arraycopy(this._data, fromIndex, removedBytes, 0, length);
            }

            System.arraycopy(this._data, fromIndex + length, this._data, fromIndex, length);
            this._size -= length;
        } else {
            if (returnArray) {
                removedBytes = new byte[0];
            }
        }

        return removedBytes;
    }

    public byte set(int index, byte element) {
        this.checkRange(index);
        this.incrModCount();
        byte oldval = this._data[index];
        this._data[index] = element;
        return oldval;
    }

    public void add(int index, byte element) {
        this.checkRangeIncludingEndpoint(index);
        this.incrModCount();
        this.ensureCapacity(this._size + 1);
        int numtomove = this._size - index;
        System.arraycopy(this._data, index, this._data, index + 1, numtomove);
        this._data[index] = element;
        ++this._size;
    }
    
    public void addElementsAt(int index, byte[] elements) {
        this.checkRangeIncludingEndpoint(index);
        this.incrModCount();
        this.ensureCapacity(this._size + elements.length);
        int numtomove = this._size - index - elements.length;
        System.arraycopy(this._data, index, this._data, index + elements.length, numtomove);
        System.arraycopy(elements, 0, this._data, index, elements.length);
        this._size+=elements.length;
    }
    
    public void ensureCapacity(int mincap) {
        this.incrModCount();
        if (mincap > this._data.length) {
            int newcap = this._data.length * 3 / 2 + 1;
            byte[] olddata = this._data;
            this._data = new byte[newcap < mincap ? mincap : newcap];
            System.arraycopy(olddata, 0, this._data, 0, this._size);
        }

    }

    public void trimToSize() {
        this.incrModCount();
        if (this._size < this._data.length) {
            byte[] olddata = this._data;
            this._data = new byte[this._size];
            System.arraycopy(olddata, 0, this._data, 0, this._size);
        }

    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(this._data.length);

        for (int i = 0; i < this._size; ++i) {
            out.writeByte(this._data[i]);
        }

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this._data = new byte[in.readInt()];

        for (int i = 0; i < this._size; ++i) {
            this._data[i] = in.readByte();
        }

    }

    private final void checkRange(int index) {
        if (index < 0 || index >= this._size) {
            throw new IndexOutOfBoundsException("Should be at least 0 and less than " + this._size + ", found " + index);
        }
    }

    private final void checkRangeIncludingEndpoint(int index) {
        if (index < 0 || index > this._size) {
            throw new IndexOutOfBoundsException("Should be at least 0 and at most " + this._size + ", found " + index);
        }
    }
}
