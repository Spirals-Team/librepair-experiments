package reader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
	Properties p = new Properties();
	

	public String getFileName(String path) {
		try {
			p.load(new FileReader("config.properties"));
			return p.getProperty(path);

		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public String getSheetName(String sheet) {
		try {
			p.load(new FileReader("config.properties"));
			return p.getProperty(sheet);

		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}

}