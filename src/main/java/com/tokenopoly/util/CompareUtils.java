/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.util;

import java.util.Comparator;
import java.util.Date;

// TODO : move to a public "foundation" package

/**
 * Helper methods and objects.
 * <p>Declared abstract since there's no need to instantiate this.</p>
 */
@SuppressWarnings("unused")
public abstract class CompareUtils {

    /**
     * Helper instance that defaults to ordering nulls last when ordering Strings by their natural ordering.
     */
    public static final Comparator<String> StringNaturalNullsLast = Comparator.nullsLast(Comparator.naturalOrder());

    /**
     * Helper instance that defaults to ordering nulls last when ordering Dates by their natural ordering.
     */
    public static final Comparator<Date> DateNaturalNullsLast = Comparator.nullsLast(Comparator.naturalOrder());

    /**
     * Helper instance that defaults to ordering nulls last when ordering Longs by their natural ordering.
     */
    public static final Comparator<Long> LongNaturalNullsLast = Comparator.nullsLast(Comparator.naturalOrder());

    public static final Comparator<Boolean> BooleanNaturalNullsLast = Comparator.nullsLast(Comparator.naturalOrder());

}
