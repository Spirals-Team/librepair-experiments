package reader;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//TODO: Cambiar el nombre, es poco claro y se puede confundir con el patron.
public class WorkbookCreator implements Closeable {

	private Workbook workbook;
		
	public WorkbookCreator(String path, String sheet) throws EncryptedDocumentException, InvalidFormatException, IOException {
		PropertiesReader pr = new PropertiesReader();
		workbook = WorkbookFactory.create(new File(pr.getFileName(path)));
	}
	
	public Workbook getWorkbook() {
		return this.workbook;
	}
	
	@Override
	public void close() throws IOException {
		workbook.close();
	}
}
