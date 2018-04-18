package reader;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class SpreadSheetReaderGatewayImpl implements ReaderGateway {

	SpreadSheetReader ssr;
	
	public SpreadSheetReaderGatewayImpl(String path, String sheet) throws EncryptedDocumentException, InvalidFormatException, IOException {
		WorkbookCreator wbc = new WorkbookCreator(path,sheet);
		
		PropertiesReader pr = new PropertiesReader();

		SheetReader sr = new SheetReader(wbc.getWorkbook());
		if (sr.sheetExists(pr.getSheetName(sheet))) {
			ssr = new SpreadSheetReader(sr.getSheet(pr.getSheetName(sheet)));
			wbc.close();
		} else {
			wbc.close();
			throw new FileNotFoundException();
		}
	}

	@Override
	public String readLine() {
		return ssr.readLine();
	}

	@Override
	public int getLineNumber() {
		return ssr.getLineNumber();
	}

	@Override
	public void setLineNumber(int lineNumber) {
		ssr.setLineNumber(lineNumber);
	}
	
}
