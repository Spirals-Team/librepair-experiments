package com.weaverplatform.sdk;

/**
 * @author bastbijl, Sysunite 2017
 */
public class TestSuite {

  private static Weaver instance;

  static Weaver getInstance() {

    if(instance != null) {
      return instance;
    }

    instance = new Weaver();
    instance.setUsername("admin");
    instance.setPassword("admin");
    instance.login();

    try {
      instance.createProject("test");
    } catch(RuntimeException e) {}

    instance.setProject("test");
    instance.wipe();

    return instance;
  }
}
