/*
 * Copyright (C) 2018 HERE Global B.V. and its affiliate(s).
 * All rights reserved.
 *
 * This software and other materials contain proprietary information
 * controlled by HERE and are protected by applicable copyright legislation.
 * Any use and utilization of this software and other materials and
 * disclosure to any third parties is conditional upon having a separate
 * agreement with HERE for the access, use, utilization or disclosure of this
 * software. In the absence of such agreement, the use of the software is not
 * allowed.
 */

package org.zalando.logbook.jaxrs;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Copies any bytes written to a stream in an internal buffer for later retrieval.
 */
class TeeOutputStream extends FilterOutputStream {
  private final ByteArrayOutputStream copy;

  TeeOutputStream(OutputStream original) {
    super(original);
    this.copy = new ByteArrayOutputStream();
  }

  @Override
  public void flush() throws IOException {
    super.flush();
    copy.flush();
  }

  @Override
  public void close() throws IOException {
    super.close();
    copy.close();
  }

  @Override
  public void write(int b) throws IOException {
    super.write(b);
    copy.write(b);
  }

  public byte[] toByteArray() {
    return copy.toByteArray();
  }
}
