package lettergeneratorstests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.assertj.core.util.Files;
import org.junit.Test;

import es.uniovi.asw.parser.agents.PersonAgent;
import es.uniovi.asw.parser.lettergenerators.WordLetterGenerator;

public class WordGeneratorTest {

	@Test
	public void test() {
		WordLetterGenerator wordg = new WordLetterGenerator();
		PersonAgent a = new PersonAgent("Sergio", "43,-5", "sergiosantano96@gmail.com", "71681354Z", "1");
		wordg.generatePersonalLetter(a);
		File f = new File(a.getId() + ".docx");
		assertTrue(f.exists());
		Files.delete(f);
		
	}

}
