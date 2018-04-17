package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import evaluator.Expression;
import fileReader.FileLineReader;
import model.RawPackage;

public class ParserTest {
	
	
	@Test
	public void parseAffirmativeTest(){
		Reader reader = new StringReader("00 08\n01 09");
		RawPackage frame = new RawPackage(new byte[]{0x08, 0x09});
		Parser parser = new Parser();
		
		PackageRulesFile packageRulesFile = new PackageRulesFile(new FileLineReader(reader));
		
		Expression<RawPackage> expression = parser.parse(packageRulesFile);
		
		Assert.assertTrue(expression.interpret(frame));
		
	}
	
	
	@Test
	public void parseNegativeTest(){
		Reader reader = new StringReader("00 08\n01 09");
		RawPackage frame = new RawPackage(new byte[]{0x07, 0x09});
		Parser parser = new Parser();
		
		PackageRulesFile packageRulesFile = new PackageRulesFile(new FileLineReader(reader));
		
		Expression<RawPackage> expression = parser.parse(packageRulesFile);
		
		Assert.assertFalse(expression.interpret(frame));
		
	}
	
	
	@Test
	public void parseWithRealFileTest(){
		File file = new File("test-files" + File.separator + "toParseFile.txt");
		RawPackage frame = new RawPackage(new byte[]{0x07, 0x09});
		Parser parser = new Parser();
		
		PackageRulesFile packageRulesFile = null;
		try {
			packageRulesFile = new PackageRulesFile(new FileLineReader(new FileReader(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Expression<RawPackage> expression = parser.parse(packageRulesFile);
		
		Assert.assertTrue(expression.interpret(frame));
		
	}
	
	
}
