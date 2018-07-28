package ru.job4j.last;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class CompareSetTest {
	private CompareSet<String> compareSet;

	@Before
	public void setUp() {
		compareSet = new CompareSet<>();
	}

	@Test
	public void whenTwoSetsAreEqualEachOther() {
		String[] array1 = {"A", "B", "C", "D"};
		String[] array2 = {"B", "C", "A", "D"};
		assertTrue(compareSet.equalSet(array1, array2));
	}

	@Test
	public void whenTwoSetsAreNotEqualEachOther() {
		String[] array1 = {"A", "B", "C", "D", "E"};
		String[] array2 = {"B", "C", "A", "D"};
		assertFalse(compareSet.equalSet(array1, array2));
	}

	@Test
	public void whenTwoDozensAreEqualEachOther() {
		String[] array1 = {"A", "A", "B"};
		String[] array2 = {"B", "A", "A"};
		assertTrue(compareSet.equalSet(array1, array2));
	}

	@Test
	public void whenTwoDozensAreNotEqualEachOther() {
		String[] array1 = {"A", "A", "B"};
		String[] array2 = {"B", "B", "A"};
		assertFalse(compareSet.equalSet(array1, array2));
	}
}