package com.economizate.servicios;

public interface DataSource {

	public void writeData(String data);
	
    public String readData();
}
