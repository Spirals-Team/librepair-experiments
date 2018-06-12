package ru.job4j.last;

import java.util.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class CompareSet<T> {
	private Map<T, Integer> set1 = new HashMap<>();
	private Map<T, Integer> set2 = new HashMap<>();

	public boolean equalSet(T[] firstSet, T[] secondSet) {
		fillSet(firstSet, set1);
		fillSet(secondSet, set2);
		return set1.equals(set2);
	}

	private void fillSet(T[] list, Map<T, Integer> set) {
		for (T item : list) {
			set.put(item, set.getOrDefault(item, 0) + 1);
		}
	}
}
