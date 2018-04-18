package com.economizate.servicios.impl;

import java.io.IOException;

import com.economizate.servicios.BaseReader;

public class CSVReader extends BaseReader {

	public CSVReader(String FileName) {
		super(FileName);
	}

	@Override
	public String read() throws IOException {
		Delimiter = ",";
		return super.read();
	}

	@Override
	public String toString() {
		return ParserType.CSV.toString();
	}

}
