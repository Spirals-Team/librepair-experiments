package guru.bonacci.oogway.sannyas.service.steps;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import guru.bonacci.oogway.sannyas.service.steps.DuplicateRemover;

public class DuplicateRemoverTests extends AbstractStepTest<DuplicateRemover>{

	public DuplicateRemoverTests(String in, String out) {
		super(in, out, DuplicateRemover.class);
	}

    @Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
         {"hello hello I am going home hello home", "hello I am going home"}
        });
    }
}
