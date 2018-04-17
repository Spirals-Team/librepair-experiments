package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

import reader.SpreadSheetReaderGatewayImpl;

public class SpreadSheetReaderGatewayImplTest {

	SpreadSheetReaderGatewayImpl ssrg;

	@Before
	public void setUp() throws Exception {
		try {
			ssrg = new SpreadSheetReaderGatewayImpl("path2","sheet1");
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			assert(false);
		}
	}

	@Test
	public void readLineTest1() {
		assertEquals("A1,B1,C1,D1", ssrg.readLine());
	}

	@Test
	public void readLineTest2() {
		ssrg.setLineNumber(1);
		assertEquals("A2,B2,C2,D2", ssrg.readLine());
	}

	@Test
	public void setLineNumberTest1() {
		ssrg.setLineNumber(1);
		assertEquals("A2,B2,C2,D2", ssrg.readLine());
		ssrg.setLineNumber(0);
		assertEquals("A1,B1,C1,D1", ssrg.readLine());
		ssrg.setLineNumber(1);
		assertEquals("A2,B2,C2,D2", ssrg.readLine());
	}

	@Test
	public void getLineNumberTest1() {
		ssrg.readLine();
		ssrg.readLine();
		ssrg.readLine();
		assertEquals(ssrg.getLineNumber(), 3);
	}
	
	@Test
	public void getLineNumberTest2() {
		ssrg.readLine();
		ssrg.setLineNumber(2);
		ssrg.readLine();
		assertEquals(ssrg.getLineNumber(), 3);
	}

	@Test
	public void constructorExceptionTest1() throws EncryptedDocumentException, InvalidFormatException, IOException {
		try {
			ssrg = new SpreadSheetReaderGatewayImpl("path2","sheet1");
			assert(false);
		} catch(FileNotFoundException err) {
			assert(true);
		}

	}
}
