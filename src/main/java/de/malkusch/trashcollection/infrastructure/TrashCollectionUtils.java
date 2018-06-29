package de.malkusch.trashcollection.infrastructure;

import java.util.Arrays;
import java.util.stream.Stream;

import de.malkusch.trashcollection.model.TrashCan;
import de.malkusch.trashcollection.model.TrashCollection;

public final class TrashCollectionUtils {

	private TrashCollectionUtils() {
	}

	public static int hashCode(TrashCollection collection) {
		return collection.collectionDate().hashCode() + Stream.of(collection.trashCans()).mapToInt(TrashCan::hashCode).sum();
	}

	public static boolean equals(TrashCollection obj1, Object obj2) {
		if (obj2 instanceof TrashCollection) {
			TrashCollection other = (TrashCollection) obj2;

			TrashCan[] mine = sorted(obj1.trashCans());
			TrashCan[] others = sorted(other.trashCans());

			return obj1.collectionDate().equals(other.collectionDate()) && Arrays.equals(mine, others);

		} else {
			return false;
		}
	}

	private static TrashCan[] sorted(TrashCan[] trashCans) {
		TrashCan[] sorted = trashCans.clone();
		Arrays.sort(sorted, (a, b) -> a.type().compareTo(b.type()));
		return sorted;
	}

}
