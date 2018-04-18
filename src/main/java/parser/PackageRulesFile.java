package parser;

import java.util.Iterator;

import fileReader.FileLineReader;

public class PackageRulesFile implements Iterable<PackageRule>, Iterator<PackageRule>{

	FileLineReader lineReader;
	String line = "";
	
	public PackageRulesFile(FileLineReader lineReader){
	
		this.lineReader = lineReader;
	}

	@Override
	public boolean hasNext() {
		line = this.lineReader.readLine();
		return line != null;
	}


	@Override
	public PackageRule next() {
		try{
			String[] rawRule = line.split(" ");

			int offset = Integer.parseInt(rawRule[0]);
			byte byteToCompare= Byte.parseByte(rawRule[1]);
			
			return new PackageRule(offset, byteToCompare);
			
		}catch(NumberFormatException e){
			throw new RuntimeException("FileFormat");
		
		}
		
	}

	@Override
	public Iterator<PackageRule> iterator() {
		return this;
	}

	
	
	
}
