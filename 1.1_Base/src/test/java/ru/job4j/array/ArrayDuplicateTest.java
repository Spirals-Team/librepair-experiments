package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
* Test Remove duplicates.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class ArrayDuplicateTest {
	/**
	* Array without duplicate.
	*/
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        ArrayDuplicate dupl = new ArrayDuplicate();
		String[] array = {"Sasha", "Dasha", "Dasha", "Igor", "Masha", "Sasha"};
		String[] resultArray = dupl.remove(array);
		String[] expectArray = {"Sasha", "Dasha", "Masha", "Igor"};
		assertThat(resultArray, is(expectArray));
    }
}