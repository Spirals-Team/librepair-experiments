package spoon.test.position.testclasses;

public class Expressions {
	void method() {
		System.out.print("x");
		System.out.print(("x"));
		System.out.print((String)null);
		System.out.print(((String)null));
	};
}