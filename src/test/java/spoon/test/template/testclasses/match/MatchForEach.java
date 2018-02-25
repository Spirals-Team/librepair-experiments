package spoon.test.template.testclasses.match;

import java.util.List;

import spoon.pattern.Pattern;
import spoon.pattern.PatternBuilder;
import spoon.reflect.factory.Factory;
import static java.lang.System.out;

public class MatchForEach {

	public static Pattern createPattern(Factory factory) {
		return PatternBuilder.create(factory, MatchForEach.class, tmb -> tmb.setBodyOfMethod("matcher1"))
			.configureParameters(pb -> {
				pb.parameter("values").byVariable("values").setMultiple(true);
			})
			.configureLiveStatements(lsb -> lsb.byVariableName("values"))
			.build();
	}
	
	public void matcher1(List<String> values) {
		for (String value : values) {
			System.out.println(value);
		}
	}
	
	public void testMatch1() {
		
		System.out.println("a");
		out.println("Xxxx");
		System.out.println((String) null);
		System.out.println(Long.class.toString());
	}

}
