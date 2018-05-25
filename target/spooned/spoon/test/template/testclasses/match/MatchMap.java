package spoon.test.template.testclasses.match;


public class MatchMap {
    @spoon.test.template.testclasses.match.Check
    void matcher1() {
    }

    @spoon.test.template.testclasses.match.Check("xyz")
    void m1() {
    }

    @spoon.test.template.testclasses.match.Check(timeout = 123, value = "abc")
    void m2() {
    }

    @java.lang.Deprecated
    void notATestAnnotation() {
    }

    @java.lang.Deprecated
    @spoon.test.template.testclasses.match.Check(timeout = 456)
    void deprecatedTestAnnotation1() {
    }

    @spoon.test.template.testclasses.match.Check(timeout = 4567)
    @java.lang.Deprecated
    void deprecatedTestAnnotation2() {
    }
}

