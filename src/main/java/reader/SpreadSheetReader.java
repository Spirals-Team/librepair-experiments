package reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

//TODO: Decidir donde cerrar y abrir el workbook (Closeable)
public class SpreadSheetReader implements ReaderGateway {
	//Basada en LineNumberReader
	
	private int lineNumber = 0;
	private Sheet sheet;

	public SpreadSheetReader(Sheet sheet) throws FileNotFoundException, IOException {
		this.sheet = sheet;
	}

	public List<String> readLine() {
		List<String> ret = new ArrayList<>();
		//TODO: Sheet hardcodeada
		int aux = 0;
		for (Row row : sheet) {
			if (aux == lineNumber) {
				lineNumber++;
				// TODO: Refactor, llevar a un método privado aislado
				for (Cell cell : row) {
					ret.add(readCell(cell) + ";");
				}
				return ret;
			}
		}
		//Si no existen más lineas, debería retornar null o enviar un error?
		return null;
	}

	@SuppressWarnings("deprecation")
	private String readCell(Cell cell) {
		switch (cell.getCellTypeEnum()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell))
				return String.valueOf(cell.getDateCellValue());
			else
				return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case BLANK:
			return "";
		default:
			return "";
		}
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}