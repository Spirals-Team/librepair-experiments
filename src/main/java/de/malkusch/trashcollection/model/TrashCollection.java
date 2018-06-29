package de.malkusch.trashcollection.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Arrays;

import de.malkusch.trashcollection.infrastructure.TrashCollectionUtils;

public final class TrashCollection {

	private final LocalDate date;
	private final TrashCan[] trashCans;

	public TrashCollection(LocalDate date, TrashCan[] trashCans) {
		this.date = requireNonNull(date);
		this.trashCans = requireNonNull(trashCans);
	}

	public LocalDate collectionDate() {
		return date;
	}

	public TrashCan[] trashCans() {
		return trashCans.clone();
	}

	@Override
	public int hashCode() {
		return TrashCollectionUtils.hashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return TrashCollectionUtils.equals(this, obj);
	}

	@Override
	public String toString() {
		return String.format("%s %s", date, Arrays.toString(trashCans));
	}

}
