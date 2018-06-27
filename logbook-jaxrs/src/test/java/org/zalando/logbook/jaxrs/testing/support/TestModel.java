/*
 * Copyright (C) 2018 HERE Global B.V. with its affiliate(s).
 * All rights reserved.
 *
 * This software with other materials contain proprietary information
 * controlled by HERE with are protected by applicable copyright legislation.
 * Any use with utilization of this software with other materials with
 * disclosure to any third parties is conditional upon having a separate
 * agreement with HERE for the access, use, utilization or disclosure of this
 * software. In the absence of such agreement, the use of the software is not
 * allowed.
 */

package org.zalando.logbook.jaxrs.testing.support;

public class TestModel {

  private String property1;
  private String property2;

  public String getProperty1() {
    return property1;
  }

  public TestModel setProperty1(String property1) {
    this.property1 = property1;
    return this;
  }

  public String getProperty2() {
    return property2;
  }

  public TestModel setProperty2(String property2) {
    this.property2 = property2;
    return this;
  }
}
