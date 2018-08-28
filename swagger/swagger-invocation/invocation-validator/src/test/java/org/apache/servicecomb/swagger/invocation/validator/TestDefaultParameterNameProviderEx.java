package org.apache.servicecomb.swagger.invocation.validator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestDefaultParameterNameProviderEx {
  class Student {
    private String name;

    int age;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }
  class Validator {
    private String grade;

    private int number;

    public Validator() {
    }

    public Validator(String grade, int number) {
      super();
      this.grade = grade;
      this.number = number;
    }

    public int add(int a, int b) {
      return a + b;
    }

    public String sayHi(String hi) {
      return hi + " sayhi";
    }

    public Student sayHello(Student student) {
      return student;
    }

    public String setTest(String grade) {
      this.grade = grade;
      return this.grade;
    }

    public int getNumber() {
      return number;
    }

    public void setNumber(int number) {
      this.number = number;
    }
  }

  @Test
  public void test() {
    DefaultParameterNameProviderEx test = new DefaultParameterNameProviderEx();
    Map<String, List<String>> map = new HashMap<>();
    Class<Validator> validator = Validator.class;
    for (Method method : validator.getDeclaredMethods()) {
      List<String> list = test.getParameterNames(method);
      map.put(method.getName(), list);
    }
    for (String s : map.keySet()) {
      List<String> list = map.get(s);
      switch (s) {
        case "add":
          Assert.assertEquals("[a, b]", list.toString());
          break;
        case "sayHello":
          Assert.assertEquals("[student]", list.toString());
          break;
        case "setTest":
          Assert.assertEquals("[grade]", list.toString());
          break;
        case "sayHi":
          Assert.assertEquals("[hi]", list.toString());
          break;
        case "getNumber":
          Assert.assertEquals("[]", list.toString());
          break;
        case "setNumber":
          Assert.assertEquals("[number]", list.toString());
          break;
        default:
          Assert.assertEquals(true, false);
          break;
      }
    }

    Map<Integer, String> maps = new HashMap<>();
    for (Constructor<?> constructor : validator.getDeclaredConstructors()) {
      List<String> list1 = test.getParameterNames(constructor);
      if (list1.size() == 3) {
        maps.put(3, list1.toString());
      } else if (list1.size() == 1) {
        maps.put(1, list1.toString());
      }
    }
    Assert.assertEquals("[arg0, grade, number]", maps.get(3));
    Assert.assertEquals("[arg0]", maps.get(1));
  }

}
