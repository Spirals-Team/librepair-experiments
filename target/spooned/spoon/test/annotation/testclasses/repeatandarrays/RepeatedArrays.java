package spoon.test.annotation.testclasses.repeatandarrays;


public class RepeatedArrays {
    @spoon.test.annotation.testclasses.repeatandarrays.TagArrays({ "machin", "truc" })
    @spoon.test.annotation.testclasses.repeatandarrays.TagArrays({ "truc", "bidule" })
    public void method() {
    }

    public void withoutAnnotation() {
    }
}

