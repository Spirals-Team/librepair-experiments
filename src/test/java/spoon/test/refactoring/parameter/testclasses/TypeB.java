package spoon.test.refactoring.parameter.testclasses;

public class TypeB extends TypeA implements IFaceB<Exception> {

	public TypeB() {
	}
	
	private void anMethodWithAnonymousClass() {
		new TypeB() {
			@Override
			@TestHierarchy("A_method1")
			public void method1(Exception p1) {
				super.method1(p1);
			}
		};
	}

	private void anMethodWithLambdaByParam(IFaceB ifaceB) {
		//this lambda is an implementation IFaceB#method1
		anMethodWithLambdaByParam(/*A_method1*/p->{});
	}
	private void anMethodWithLambda() {
		//this lambda is an implementation IFaceB#method1
		@TestHierarchy("A_method1")
		IFaceB ifaceB = p->{};
		ifaceB.method1(1);
	}
	private void anMethodWithLocalClass() {
		class Local extends TypeL {
			@Override
			@TestHierarchy("A_method1")
			public void method1(Double p1) {
				super.method1(p1);
			}
		}
	}
}
