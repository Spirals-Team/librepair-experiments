package com.github.powerlibraries.primitive.collections;

import java.util.ListIterator;

public interface ByteListIterator extends ListIterator<Byte> {
	@Override @Deprecated
	default Byte next() {
		return nextByte();
	}

	@Override @Deprecated
	default Byte previous() {
		return previousByte();
	}

	@Override @Deprecated
	default void set(Byte e) {
		setByte(e);
	}

	@Override @Deprecated
	default void add(Byte e) {
		addByte(e);
	}
	
	byte nextByte();

	byte previousByte();

	void setByte(byte e);

	void addByte(byte e);
}