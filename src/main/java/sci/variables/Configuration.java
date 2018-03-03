package sci.variables;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import scilib.utilities.FileConverter;

/**
 * Configuration file which serves as a compiler-readable source of variables from the config.txt file.
 * @author Auxiliatrix
 *
 */
public class Configuration {
	
	private FileConverter fc;
	
	public String dataFile;
	public String decimalFormat;
	public ArrayList<String> dataTypes;
	public String motd;
	public String team;
	
	/**
	 * Creates a Configuration object, and automatically parses data from the config.txt file.
	 */
	public Configuration() {
		fc = new FileConverter();
		
		dataFile = "";
		decimalFormat = "";
		dataTypes = new ArrayList<String>();
		motd = "";
		team = "";
		
		update();
	}
	
	/**
	 * Parses data from the config.txt file, overwriting previous values.
	 */
	public void update() {
		ArrayList<String> lines = fc.convert("config.txt");
		for( String line : lines ) {
			if( line.startsWith("data_file: ") ) {
				line = line.substring(11);
				dataFile = line;
			} else if( line.startsWith("decimal_format: ") ) {
				line = line.substring(16);
				decimalFormat = line;
			} else if( line.startsWith("data_types: ") ) {
				line = line.substring(12);
				StringTokenizer sc_dt = new StringTokenizer(line);
				while( sc_dt.hasMoreTokens() ) {
					String next = fc.compress(sc_dt.nextToken(","));
					if( dataTypes.contains(next) ) {
						System.out.println("WARNING: Duplicate data_type: \"" + next + "\"");
					} else if( !next.equals("") ) {
						dataTypes.add(next);
					}
				}
			} else if( line.startsWith("motd: ") ) {
				line = line.substring(6);
				motd = line;
			} else if( line.startsWith("team: ") ) {
				line = line.substring(6);
				team = line;
			}
		}
	}
	
}
