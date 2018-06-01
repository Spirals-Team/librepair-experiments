package com.economizate;


import org.junit.Test;
import com.economizate.servicios.impl.AESEncrypt;
import com.economizate.servicios.impl.CompressionDecorator;
import com.economizate.servicios.impl.DataSourceDecorator;
import com.economizate.servicios.impl.EncryptionDecorator;
import com.economizate.servicios.impl.FileDataSource;
import com.economizate.servicios.impl.Propiedad;

public class ComprimirTest {

	@Test
	public void comprimirTest() {
		
		DataSourceDecorator encoded = new CompressionDecorator(
				new EncryptionDecorator(
                    new FileDataSource(Propiedad.getInstance().getPropiedad("resourcesTesting") + "OutputDemo.txt"), new AESEncrypt()));
		encoded.writeData("Name,Salary\nJohn Smith,100000\nSteven Jobs,912000;Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven JoName,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000bs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000");
		
		System.out.println(encoded.readData());
	}

	@Test
	public void comprimirTest2() {
		
		DataSourceDecorator encoded = new CompressionDecorator(
                    new FileDataSource(Propiedad.getInstance().getPropiedad("resourcesTesting") + "OutputDemo.txt"));
		encoded.writeData("Name,Salary\nJohn Smith,100000\nSteven Jobs,912000;Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven JoName,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000"
				+ "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000bs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000Name,Salary\nJohn Smith,100000\nSteven Jobs,912000");
		
		System.out.println(encoded.readData());
	}
}
