package spoon.test.annotation.testclasses;


public class AnnotationsAppliedOnAnyTypeInAClass {
    public void m() throws java.lang.@spoon.test.annotation.testclasses.TypeAnnotation
    Exception {
    }

    public java.lang.String m2() {
        java.lang.Object s = new java.lang.@spoon.test.annotation.testclasses.TypeAnnotation
        String();
        return ((java.lang.@spoon.test.annotation.testclasses.TypeAnnotation
        String) (s));
    }

    public java.lang.@spoon.test.annotation.testclasses.TypeAnnotation
    String m3() {
        return "";
    }

    public <@spoon.test.annotation.testclasses.TypeAnnotation
    T> void m4() {
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation
        T> list = new java.util.ArrayList<>();
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation
        ?> list2 = new java.util.ArrayList<>();
        java.util.List<spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation
        BasicAnnotation> list3 = new java.util.ArrayList<spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation
        BasicAnnotation>();
    }

    public <T> void m5() {
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(integer = 1)
        T> list;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(integers = { 1 })
        T> list2;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(string = "")
        T> list3;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(strings = { "" })
        T> list4;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(clazz = java.lang.String.class)
        T> list5;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(classes = { java.lang.String.class })
        T> list6;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(b = true)
        T> list7;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(e = spoon.test.annotation.testclasses.AnnotParamTypeEnum.R)
        T> list8;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(ia = @spoon.test.annotation.testclasses.InnerAnnot(""))
        T> list9;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(ias = { @spoon.test.annotation.testclasses.InnerAnnot("") })
        T> list10;
        java.util.List<@spoon.test.annotation.testclasses.TypeAnnotation(inceptions = { @spoon.test.annotation.testclasses.Inception(value = @spoon.test.annotation.testclasses.InnerAnnot(""), values = { @spoon.test.annotation.testclasses.InnerAnnot("") }) })
        T> list11;
    }

    public void m6(java.lang.@spoon.test.annotation.testclasses.TypeAnnotation
    String param) {
        java.lang.@spoon.test.annotation.testclasses.TypeAnnotation
        String s = "";
    }

    public enum DummyEnum implements spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation
    BasicAnnotation {
        ;
    }

    public interface DummyInterface extends spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation
    BasicAnnotation {}

    public class DummyClass extends spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation
    AnnotArrayInnerClass implements spoon.test.annotation.testclasses.@spoon.test.annotation.testclasses.TypeAnnotation
    BasicAnnotation {}

    public class DummyGenericClass<@spoon.test.annotation.testclasses.TypeAnnotation
    T, @spoon.test.annotation.testclasses.TypeAnnotation
    K> implements spoon.test.annotation.testclasses.BasicAnnotation<@spoon.test.annotation.testclasses.TypeAnnotation
    T> {}
}

