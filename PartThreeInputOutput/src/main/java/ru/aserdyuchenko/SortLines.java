package ru.aserdyuchenko;

import java.io.File;
/**
 * Interface SortLines. Task 860.
 * @author Anton Serdyuchenko (anton415@gmail.com).
 * @version 1.
 * @since 17.12.2016.
 */
public interface SortLines {
/**
 * Sort lines in file.
 * @param source			Unsorted file.
 * @param dist				Sorted file.
 */
	void sort(File source, File dist);
}
