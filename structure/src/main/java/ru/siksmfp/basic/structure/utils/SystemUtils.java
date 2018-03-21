package ru.siksmfp.basic.structure.utils;

import ru.siksmfp.basic.structure.utils.cloning.Cloner;

/**
 * @author Artem Karnov @date 10/31/2017.
 * artem.karnov@t-systems.com
 */
public class SystemUtils {
    public static <T> T clone(T t) {
        Cloner cloner = new Cloner();
        return cloner.deepClone(t);
    }
}
