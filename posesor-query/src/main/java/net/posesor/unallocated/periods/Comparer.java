package net.posesor.unallocated.periods;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Comparer {
    public static <T extends Comparable<T>> T lower(T v1, T v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1.compareTo(v2) > 0
                ? v2
                : v1;
    }

    public static <T extends Comparable<T>> T bigger(T v1, T v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1.compareTo(v2) < 0
                ? v2
                : v1;
    }
}
