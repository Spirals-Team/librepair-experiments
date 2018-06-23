package com.piggymetrics.statistics.domain;

/**
 * @author yibo
 */

public enum Currency {

    USD, EUR, RUB;

    public static Currency getBase() {
        return USD;
    }
}
