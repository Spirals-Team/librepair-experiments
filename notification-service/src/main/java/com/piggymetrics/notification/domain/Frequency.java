package com.piggymetrics.notification.domain;

import java.util.stream.Stream;

/**
 * @author yibo
 */

public enum Frequency {

    WEEKLY(7), MONTHLY(30), QUARTERLY(90);

    private int days;

    Frequency(int days) {
        this.days = days;
    }

    public static Frequency withDays(int days) {
        return Stream.of(Frequency.values())
                .filter(f -> f.getDays() == days)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getDays() {
        return days;
    }
}
