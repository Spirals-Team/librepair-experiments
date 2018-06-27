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

package org.zalando.logbook.jaxrs;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.zalando.logbook.Logbook;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class ServerLoggingFilterTest {

  @Mock
  private ContainerRequestContext requestContext;

  @Mock
  private ContainerResponseContext responseContext;

  @Mock
  private Logbook logbook;

  private ServerLoggingFilter unit;

  @BeforeEach
  public void setUp() {
    initMocks(this);
    unit = new ServerLoggingFilter(logbook);
  }

  @Test
  public void filter_shouldDoNothingIfCorrelatorIsNotPresent() throws Exception {
    unit.filter(requestContext, responseContext);
    verifyZeroInteractions(logbook);
  }
}