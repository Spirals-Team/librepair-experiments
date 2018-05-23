package com.economizate.servicios.impl;

import com.economizate.servicios.DataSource;

public class CompressionDecorator extends DataSourceDecorator {

	public CompressionDecorator(DataSource source) {
		super(source);
	}

	@Override
	public void writeData(String data){
		
	}
	
	@Override
	public String readData(){
		return null;
	}
}
