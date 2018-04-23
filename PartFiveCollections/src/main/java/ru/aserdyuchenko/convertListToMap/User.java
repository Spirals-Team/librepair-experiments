package ru.aserdyuchenko.convertListToMap;
/**
 * @author Anton Serdyuchenko (anton415@gmail.com   +7 (916) 154-92-00)
 */
import java.util.*;

class User {
  int id;
  String name;
  String city;
/**
 * Конструктор, для создания нового пользователя.
 */
  public User(int id, String name, String city) {
    this.id = id;
    this.name = name;
    this.city = city;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }
}
