package spoon.test.limits;


public class StaticConstructorAccessOnInstance {
    spoon.test.limits.utils.ContainInternalClass test = new spoon.test.limits.utils.ContainInternalClass();

    public void methode() {
        @java.lang.SuppressWarnings("unused")
        spoon.test.limits.utils.ContainInternalClass.InternalClass testBis = test.new InternalClass();
    }
}

