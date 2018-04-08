package es.uniovi.asw.parser.lettergenerators;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import es.uniovi.asw.parser.agents.AbstractAgent;

public class WordLetterGenerator implements LetterGenerator {
	
	/**
	 * @author Adrian
	 * 
	 * Generates a new word document into which it writes
	 * the data of a given citizen.
	 */
	@Override
	public void generatePersonalLetter(AbstractAgent c) {
		// Blank Document
		XWPFDocument document = new XWPFDocument();

		// Write the Document in file system
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File(c.getId()+".docx"));
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun runNames = paragraph.createRun();
			
			//Add text to the document 
			runNames.setText("Mr/Mrs " + c.getName());
			runNames.addBreak();
			
			XWPFRun runLogin = paragraph.createRun();
			runLogin.setText("Your login data has been generated:");
			runLogin.addBreak();
			
			XWPFRun runUser = paragraph.createRun();
			runUser.setText("Username: "+ c.getEmail());
			runUser.addBreak();
			
			XWPFRun runPass = paragraph.createRun();
			runPass.setText("Password: " + c.getPassword());

			document.write(out);
			out.close();
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
