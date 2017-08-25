import java.util.*;


class A {

  void test(List<Object> list) { // Noncompliant {{Use 'Collection' here; it is more general type than 'List'}}
    System.out.println(list.size());
    list = new ArrayList<>();
  }

}
