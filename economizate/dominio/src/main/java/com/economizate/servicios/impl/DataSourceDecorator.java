package com.economizate.servicios.impl;

import com.economizate.servicios.DataSource;

public class DataSourceDecorator implements DataSource {

	protected DataSource wrappee;
	
	public DataSourceDecorator(DataSource source) {
	    wrappee = source;
	}
	
	@Override
	public void writeData(String data) {
		wrappee.writeData(data);
	}

	@Override
	public String readData() {
		return wrappee.readData();
	}

		
}
