package aserdyuchenko;

/**
 * 5.3. Создание программу удаления дубликатов в массиве.
 * Дано. Массив строк. String[] - нужно убрать дубликаты.
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 12.09.2016
 *
 */
public class WithoutDuplicates {
/**
 * withoutDuplicates(String[] array).
 * @param array - array.
 * @return cleanArray - cleanArray.
 */
    String[] withoutDuplicates(String[] array) {
/**
 * @param count - count.
 */
        int count = 0;
/**
 * Removing duplicates:
 */
        for (int firstIndex = array.length - 1; firstIndex >= 0; firstIndex--) {
            for (int secondIndex = 0; secondIndex < firstIndex; secondIndex++) {
                if (array[firstIndex] == array[secondIndex]) {
                    array[secondIndex] = null;
                }
            }
        }
/**
 * We are looking for the number of elements in the cleanArray:
 */
        for (String s : array) {
            if (s != null && s.length() > 0) {
                count++;
            }
        }
/**
 * Remove empty elements:
 */
        String[] cleanArray = new String[count];
        for (int thirdIndex = 0; thirdIndex <= cleanArray.length - 1; thirdIndex++) {
            for (int fouthIndex = thirdIndex; fouthIndex <= array.length - 1; fouthIndex++) {
                if (array[fouthIndex] != null) {
                    cleanArray[thirdIndex] = array[fouthIndex];
                    thirdIndex++;
                }
            }
        }
        return cleanArray;
   }
}
