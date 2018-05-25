package spoon.test.reference;


public class MyClass2 {
    final spoon.test.reference.MyClass myClass;

    final spoon.test.reference.MyClass3<java.lang.Integer, java.lang.String> myClass3;

    public MyClass2() {
        myClass = new spoon.test.reference.MyClass();
        myClass3 = new spoon.test.reference.MyClass3<java.lang.Integer, java.lang.String>();
    }

    public void methodA() {
        myClass.method1("guyfez");
    }

    public void methodB() {
        myClass.method2();
    }

    public void methodC() {
        myClass3.methodI(1);
        myClass3.methodII(42, "Call method II");
    }

    public void methodD() {
        methodE("Call method B");
    }

    public void methodE(java.lang.String param) {
        methodF(42, "Call method C");
    }

    public void methodF(int param1, java.lang.String param2) {
    }
}

