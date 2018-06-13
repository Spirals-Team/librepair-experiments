package org.simpleflatmapper.converter.impl;

import org.simpleflatmapper.converter.Converter;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberBigDecimalConverter implements Converter<Number, BigDecimal> {
    @Override
    public BigDecimal convert(Number in) {
        if (in == null) return null;
        if (in instanceof BigInteger) {
            return new BigDecimal((BigInteger) in);
        }
        return new BigDecimal(in.doubleValue());
    }

    public String toString() {
        return "NumberToBigDecimal";
    }
}
