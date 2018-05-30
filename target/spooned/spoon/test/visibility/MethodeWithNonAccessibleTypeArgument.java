package spoon.test.visibility;


public class MethodeWithNonAccessibleTypeArgument {
    public void method() {
        new spoon.test.visibility.packageprotected.AccessibleClassFromNonAccessibleInterf().method(new spoon.test.visibility.packageprotected.AccessibleClassFromNonAccessibleInterf());
    }
}

