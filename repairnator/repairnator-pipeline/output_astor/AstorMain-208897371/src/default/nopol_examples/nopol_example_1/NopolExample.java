package nopol_examples.nopol_example_1;


public class NopolExample {
	private int index = 419382;

	private static java.lang.String s = "Overloading field name with parameter name";

	public char charAt(java.lang.String s, int index) {
		if (index == 0)
			return s.charAt(0);

		if (index < (s.length()))
			return s.charAt(index);

		return s.charAt(((s.length()) - 1));
	}

	public NopolExample() {
		int variableInsideConstructor;
		variableInsideConstructor = 15;
		index = 2 * variableInsideConstructor;
	}
}

