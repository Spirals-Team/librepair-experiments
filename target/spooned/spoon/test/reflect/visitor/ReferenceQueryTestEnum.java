package spoon.test.reflect.visitor;


public enum ReferenceQueryTestEnum {
    E0(new java.lang.Integer(0)), E1(new java.lang.Long(1));
    java.lang.Boolean bool;

    ReferenceQueryTestEnum(java.lang.Number throwable) {
        java.lang.String t = "true";
        bool = java.lang.Boolean.valueOf(t);
    }

    public java.lang.Void getNumber() {
        return null;
    }
}

