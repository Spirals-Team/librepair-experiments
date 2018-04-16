package reader;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class SpreadSheetReaderGatewayImpl implements ReaderGateway {

	SpreadSheetReader ssr;
	
	public SpreadSheetReaderGatewayImpl(String path) throws EncryptedDocumentException, InvalidFormatException, IOException {
		WorkbookCreator wbc = new WorkbookCreator(path);
		SheetReader sr = new SheetReader(wbc.getWorkbook());
		ssr = new SpreadSheetReader(sr.getSheet(path));
	}

	@Override
	public List<String> readLine() {
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
