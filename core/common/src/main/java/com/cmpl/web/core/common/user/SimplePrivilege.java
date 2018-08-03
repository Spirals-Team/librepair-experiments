package com.cmpl.web.core.common.user;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimplePrivilege implements Privilege {
  private final String namespace;
  private final String feature;
  private final String right;
  private final String privilege;

  public SimplePrivilege(String namespace, String feature, String right) {

    this.namespace = namespace;
    this.feature = feature;
    this.right = right;
    this.privilege = namespace + ":" + feature + ":" + right;
  }

  public SimplePrivilege(String privilege) {
    List<String> splitted = Stream.of(privilege.split(":")).collect(Collectors.toList());

    this.namespace = splitted.get(0);
    this.feature = splitted.get(1);
    this.right = splitted.get(2);
    this.privilege = privilege;
  }

  @Override
  public String namespace() {
    return this.namespace;
  }

  @Override
  public String feature() {
    return this.feature;
  }

  @Override
  public String right() {
    return this.right;
  }

  @Override
  public String privilege() {
    return this.privilege;
  }

  @Override
  public boolean supports(String privilege) {
    return this.privilege.equals(privilege);
  }
}
