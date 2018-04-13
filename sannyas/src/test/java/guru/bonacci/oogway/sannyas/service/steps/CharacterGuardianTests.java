package guru.bonacci.oogway.sannyas.service.steps;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import guru.bonacci.oogway.sannyas.service.steps.CharacterGuardian;

public class CharacterGuardianTests extends AbstractStepTest<CharacterGuardian>{

	public CharacterGuardianTests(String in, String out) {
		super(in, out, CharacterGuardian.class);
	}

    @Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
         {"bla bla", "bla bla"},
         {"!hi &^&^there &", "hi there "},
         {"ab 123c.", "ab 123c"},
         {" 123 " , " 123 "},
         {"" , ""}
        });
    }
}
