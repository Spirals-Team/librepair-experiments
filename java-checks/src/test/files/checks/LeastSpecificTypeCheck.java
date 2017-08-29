import java.util.*;


class A {

  public void test1(ArrayList<Object> list) { // Noncompliant {{Use 'java.util.Collection' here; it is a more general type than 'ArrayList'.}}
    System.out.println(list.size());
    list.equals(null);
    list = new ArrayList<>();
  }

  void test1(ArrayList<Object> list) { // Compliant - not public
    System.out.println(list.size());
    list.equals(null);
    list = new ArrayList<>();
  }

  public void test2(ArrayList<Object> list) { // Noncompliant {{Use 'java.util.List' here; it is a more general type than 'ArrayList'.}}
    list.sort(Comparator.comparingInt(Object::hashCode));
  }

  public void test3(ArrayList<Object> list) { // Compliant
    list.trimToSize();
  }

  public void dontSuggestObject(String s) { // Compliant
    s.equals("Test");
  }

  public static void staticMethod(List list) { // Noncompliant {{Use 'java.util.Collection' here; it is a more general type than 'List'.}}
    list.size();
  }

  public static List returnArg(List list) { // Compliant
    if (list.size() > 0) {
      return list;
    }
    return null;
  }

  interface IA {
    default void a() {}
  }

  interface IB {
    default void b() {}
  }

  class S implements IA, IB {}

  class C extends S {}

  public void test4(C arg) { // Noncompliant {{Use 'A.S' here; it is a more general type than 'C'.}}
    arg.a();
    arg.b();
  }

  interface ITest {
    int doIt(List<Object> list);
  }

  class Test implements ITest {

    @Override
    public int doIt(List<Object> list) { // Compliant - overrides are ignored
      return list.size();
    }
  }
}
