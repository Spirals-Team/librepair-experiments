package org.simpleflatmapper.converter.impl;

import org.simpleflatmapper.converter.Converter;

public class CharSequenceShortConverter implements Converter<CharSequence, Short> {
    @Override
    public Short convert(CharSequence in) throws Exception {
        if (in == null) return null;
        return Short.valueOf(in.toString());
    }

    public String toString() {
        return "CharSequenceToShort";
    }
}
