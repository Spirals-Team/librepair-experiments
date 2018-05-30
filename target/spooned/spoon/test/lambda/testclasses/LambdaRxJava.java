package spoon.test.lambda.testclasses;


public class LambdaRxJava {
    public interface NbpOperator extends java.util.function.Function<java.lang.String, java.lang.Integer> {}

    public java.lang.Integer bla(spoon.test.lambda.testclasses.LambdaRxJava.NbpOperator toto) {
        return toto.apply("truc");
    }

    public void toto() {
        bla(((spoon.test.lambda.testclasses.LambdaRxJava.NbpOperator) (( t) -> {
            return t.length();
        })));
    }
}

