package org.apache.servicecomb.samples.springmvc.provider;

import java.util.List;
import java.util.Map;

import org.apache.servicecomb.samples.common.schema.models.Person;
import org.apache.servicecomb.samples.common.schema.models.Word;

public interface Hello {

  String sayHi(String name);

  String sayHello(Person person);

  String sayGG(int age);

  String sayPrice(Double price);

  String sayGGList(List<Integer> age);

  String sayPersonList(List<Person> personList);

  String justSay();

  String sayMap(Map<String, String> map1);

  String sayMap1(Map<String, Double> map11);

  String sayMap2(Map<String, Word> map12);
}
