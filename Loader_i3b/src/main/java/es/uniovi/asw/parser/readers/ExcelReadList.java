package es.uniovi.asw.parser.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import es.uniovi.asw.parser.agents.AbstractAgent;
import es.uniovi.asw.parser.agents.EntityAgent;
import es.uniovi.asw.parser.agents.GeneralAgent;
import es.uniovi.asw.parser.agents.PersonAgent;
import es.uniovi.asw.parser.agents.SensorAgent;
import es.uniovi.asw.parser.lettergenerators.LetterGenerator;

/**
 * @author Jorge. Excel parser.
 */
public class ExcelReadList extends AbstractReadList {

	public ExcelReadList() {
		super();
	}

	public ExcelReadList(LetterGenerator letterGenerator) {
		super(letterGenerator);
	}

	@Override
	public void doParse(String ruta) {

		XSSFWorkbook wb = null;
		XSSFSheet sheet;
		XSSFRow row;

		try {
			FileInputStream file = new FileInputStream(ruta);

			wb = new XSSFWorkbook(OPCPackage.open(file));
			sheet = wb.getSheetAt(0);
			agentsCensus = new HashSet<AbstractAgent>();

			int rows = sheet.getPhysicalNumberOfRows();

			int cols = 5; 

			for (int r = 1; r < rows; r++) {
				row = sheet.getRow(r);

				Object[] data = parseRow(row, cols);

				AbstractAgent agent = null;

				if (data != null) {

					if (data[0] == null) {
						wReport.report("Null name on row number " + r, ruta);
					} else if (data[1] == null && !(masterKinds.get(data[4]).equals("Person"))) {
						wReport.report("Null location on row number " + r, ruta);
					} else if (data[2] == null) {
						wReport.report("Null email on row number " + r, ruta);
					} else if (data[3] == null) {
						wReport.report("Null identifier on row number " + r, ruta);
					} else if (data[4] == null) {
						wReport.report("Null kind name on row number " + r, ruta);
					
					} else {
						
						String kind = masterKinds.get(data[4]);
						switch (kind) {
						case "Person" : agent = new PersonAgent(data); break;
						case "Entity" : agent = new EntityAgent(data); break;
						case "Sensor" : agent = new SensorAgent(data); break;
						default: agent = new GeneralAgent(data); break;
						}
						if (agentsCensus.contains(agent)) {
							wReport.report("Duplicated agent on row number " + r, ruta);
						} else {
							agentsCensus.add(agent);
						}

					}
				} else {
					wReport.report("Empty row nº" + r, ruta);
				}

			}

			wb.close();
		} catch (FileNotFoundException e) {
			wReport.report(e, "File not found");
		}

		catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	private Object[] parseRow(XSSFRow row, int cols) throws ParseException {
		XSSFCell cell;
		Object[] data = new Object[cols];

		if (row != null) {
			for (int c = 0; c < cols; c++) {
				cell = row.getCell((short) c);
				if (cell != null && !cell.toString().equals("")) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC ) {
						data[c] = Double.valueOf(cell.toString()).intValue();
					} else {
						data[c] = cell.toString();
					}
				}
			}
			return data;
		}
		return null;
	}

	@Override
	protected void doParseMaster(String ruta) {
        String line = "";
        String cvsSplitBy = ",";
        masterKinds = new HashMap<Integer,String>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] lineData = line.split(cvsSplitBy);
                String code = lineData[1];
                int kind = Integer.parseInt(lineData[0]);
                masterKinds.put(kind,code);

            }

        }

		

		catch (FileNotFoundException e) {
			wReport.report(e, "No se ha encontrado el archivo solicitado");
		}

		catch (Exception ioe) {
			ioe.printStackTrace();
		}
		
	}


}
