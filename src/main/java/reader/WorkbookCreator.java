package reader;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//TODO: Cambiar el nombre, es poco claro y se puede confundir con el patrón.
public class WorkbookCreator {

	private Workbook workbook;
	
	public WorkbookCreator(String path) throws EncryptedDocumentException, InvalidFormatException, IOException {
		workbook = WorkbookFactory.create(new File(path));
	}
	
	public Workbook getWorkbook() {
		return this.workbook;
	}
	
	public void close() throws IOException {
		workbook.close();
	}
}
