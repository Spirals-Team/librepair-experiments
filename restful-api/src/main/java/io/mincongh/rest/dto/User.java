package io.mincongh.rest.dto;

/**
 * User DTO.
 *
 * @author Mincong Huang
 */
public class User {

  private int id;

  private String name;

  private int age;

  public User(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return this.id == user.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
