package ru.job4j.collections;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class OverrideHashcode {
    int varA;
    int varB;

    OverrideHashcode(int varA, int varB) {
        this.varA = varA;
        this.varB = varB;
    }

    /**
     * Переопределение метода hashCode(). В качестве начального значения выбирается простое число
     * это сделано для уменьшения вероятности возникновения коллизии, далее хэш умножается
     * сам на себя, чтобы получить максимально отличные хэши для равномерного распределения ключей
     * в хэш таблице, числовые поля типа int просто прибавляются к значению хэша.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + varA;
        result = prime * result + varB;
        return result;
    }
}
