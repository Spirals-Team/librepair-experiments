package guru.bonacci.oogway.sannyas.service.steps;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import guru.bonacci.oogway.sannyas.service.steps.KeyPhraser;

public class KeyPhraserTests extends AbstractStepTest<KeyPhraser> {

    public KeyPhraserTests(String in, String out) {
    	super(in, out, KeyPhraser.class);
    }
    
    @Parameters
    public static Collection<Object[]> data() {
    	return asList(new Object[][]{
	         {"If I have seen further it is by standing on the shoulders of Giants.", "have seen standing shoulders of Giants"},
	         {"I can calculate the motion of heavenly bodies but not the madness of people.", "calculate motion of heavenly bodies madness of people"},
	         {"Tact is the knack of making a point without making an enemy.", "Tact knack making point making enemy"},
	         {"Nature is pleased with simplicity. And nature is no dummy" , "Nature pleased simplicity nature dummy"},
	         {"Hello, My Name Is Doris" , "Name Doris"}
        });
    }
}
