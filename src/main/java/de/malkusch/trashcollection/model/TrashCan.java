package de.malkusch.trashcollection.model;

import java.util.Objects;

public final class TrashCan {

	public enum Type {
		PAPER, PLASTIC, ORGANIC, RESIDUAL
	}

	private final Type type;

	public TrashCan(Type type) {
		this.type = Objects.requireNonNull(type);
	}

	public Type type() {
		return type;
	}

	@Override
	public String toString() {
		return type.toString();
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrashCan) {
			TrashCan other = (TrashCan) obj;
			return type.equals(other.type);

		} else {
			return false;
		}
	}

}
