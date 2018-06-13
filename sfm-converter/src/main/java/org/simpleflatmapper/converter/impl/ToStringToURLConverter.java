package org.simpleflatmapper.converter.impl;

import org.simpleflatmapper.converter.ConversionException;
import org.simpleflatmapper.converter.Converter;

import java.net.MalformedURLException;
import java.net.URL;

public class ToStringToURLConverter implements Converter<Object, URL> {

	@Override
	public URL convert(Object in) {
		try {
			return new URL(String.valueOf(in));
		} catch (MalformedURLException e) {
			throw new ConversionException(e);
		}
	}

}
