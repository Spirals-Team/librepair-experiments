/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicecomb.samples.common.schema.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {

  private String name;

  private boolean sucess;

  private int age = 0;

  private double price;

  private Word word;

  private List<Word> words = new ArrayList<>();

  private Map<String, String> map1 = new HashMap<>();

  private Map<String, Double> map2 = new HashMap<>();

  private Map<String, Word> map3 = new HashMap<>();

  private List<Integer> phones = new ArrayList<>();

  private List<List<Integer>> allPhones = new ArrayList<>();

  private List<List<Word>> allWords = new ArrayList<>();

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Word getWord() {
    return word;
  }

  public void setWord(Word word) {
    this.word = word;
  }

  public List<Word> getWords() {
    return words;
  }

  public boolean isSucess() {
    return sucess;
  }

  public Map<String, String> getMap1() {
    return map1;
  }

  public void setMap1(Map<String, String> map1) {
    this.map1 = map1;
  }

  public Map<String, Double> getMap2() {
    return map2;
  }

  public void setMap2(Map<String, Double> map2) {
    this.map2 = map2;
  }

  public Map<String, Word> getMap3() {
    return map3;
  }

  public void setMap3(Map<String, Word> map3) {
    this.map3 = map3;
  }

  public void setSucess(boolean sucess) {
    this.sucess = sucess;
  }

  public void setWords(List<Word> words) {
    this.words = words;
  }

  public List<Integer> getPhones() {
    return phones;
  }

  public void setPhones(List<Integer> phones) {
    this.phones = phones;
  }

  public List<List<Integer>> getAllPhones() {
    return allPhones;
  }

  public List<List<Word>> getAllWords() {
    return allWords;
  }

  public void setAllWords(List<List<Word>> allWords) {
    this.allWords = allWords;
  }

  public void setAllPhones(List<List<Integer>> allPhones) {
    this.allPhones = allPhones;
  }
}
