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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.zalando.logbook.jaxrs.Utils.copy;
import static org.zalando.logbook.jaxrs.Utils.getPortOptional;
import static org.zalando.logbook.jaxrs.Utils.toByteArray;

public class UtilsTest {

  @Test
  public void copy_shouldThrowRequestResponseExceptionOnOoException() throws Exception {
    IOException toThrow = new IOException();
    InputStream is = mock(InputStream.class);
    OutputStream os = mock(OutputStream.class);
    when(is.read(any())).thenThrow(toThrow);

    RequestResponseLogProcessingException thrown = assertThrows(
        RequestResponseLogProcessingException.class,
        () -> copy(is, os)
    );
    assertEquals(toThrow, thrown.getCause());
  }

  @Test
  public void toByteArray_shouldConvertInputStream() {
    String input = "This is an input string";
    ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes(UTF_8));
    byte[] result = toByteArray(is);
    assertArrayEquals(input.getBytes(UTF_8), result);
  }

  @Test
  public void getPortOptional_shouldReturnPositivePort() throws Exception {
    URI uri = new URI("http://localhost:99999");
    assertEquals(Optional.of(99999), getPortOptional(uri));
  }

  @Test
  public void getPortOptional_shouldReturnEmptyForNegativePort() throws Exception {
    URI uri = new URI("http://localhost:-99999");
    assertEquals(Optional.empty(), getPortOptional(uri));
  }
}
