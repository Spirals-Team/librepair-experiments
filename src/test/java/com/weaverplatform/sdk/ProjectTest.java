package com.weaverplatform.sdk;

import org.junit.Test;


public class ProjectTest {

  @Test
  public void test() {

    Weaver w = TestSuite.getInstance();

    // List
    Project[] list = w.getProjects();
    for(Project existing : list) {
      System.out.println(existing.getId() + ": " + existing.getName());
    }
  }


}
