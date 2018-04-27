package com.economizate.servicios;

import java.text.ParseException;
import java.util.List;

public interface ParserListaRegistros <T> {
	
	public List<T> parse() throws ParseException;

}
