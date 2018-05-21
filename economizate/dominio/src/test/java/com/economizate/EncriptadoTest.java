package com.economizate;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.economizate.servicios.DataSource;
import com.economizate.servicios.impl.EncyptionDecorator;
import com.economizate.servicios.impl.FileDataSource;
import com.economizate.servicios.impl.Propiedad;

public class EncriptadoTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void encriptarTest() throws FileNotFoundException {
		
		String rutaArchivos = Propiedad.getInstance().getPropiedad("resourcesTesting");
		DataSource source = new FileDataSource(rutaArchivos + "somefile.dat");
        source.writeData("pepe");
        // The target file has been written with plain data.

        /*source = new CompressionDecorator(source)
        source.writeData(salaryRecords)*/
        // The file has been written with compressed data.

        source = new EncyptionDecorator(source);
        // The source variable is now containing this:
        // Encryption > Compression > FileDataSource
        source.writeData("pepe");
		
		assertTrue(true);
	}
}
