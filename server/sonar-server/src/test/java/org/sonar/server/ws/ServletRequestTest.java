/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.ws;

import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonarqube.ws.MediaTypes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServletRequestTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  HttpServletRequest source = mock(HttpServletRequest.class);

  ServletRequest underTest = new ServletRequest(source);

  @Test
  public void call_method() {
    underTest.method();

    verify(source).getMethod();
  }

  @Test
  public void getMediaType() throws Exception {
    when(source.getHeader(HttpHeaders.ACCEPT)).thenReturn(MediaTypes.JSON);
    when(source.getRequestURI()).thenReturn("/path/to/resource/search");

    assertThat(underTest.getMediaType()).isEqualTo(MediaTypes.JSON);
  }

  @Test
  public void default_media_type_is_octet_stream() throws Exception {
    when(source.getRequestURI()).thenReturn("/path/to/resource/search");

    assertThat(underTest.getMediaType()).isEqualTo(MediaTypes.DEFAULT);
  }

  @Test
  public void media_type_taken_in_url_first() throws Exception {
    when(source.getHeader(HttpHeaders.ACCEPT)).thenReturn(MediaTypes.JSON);
    when(source.getRequestURI()).thenReturn("/path/to/resource/search.protobuf");

    assertThat(underTest.getMediaType()).isEqualTo(MediaTypes.PROTOBUF);
  }

  @Test
  public void has_param_from_source() {
    when(source.getParameterMap()).thenReturn(ImmutableMap.of("param", new String[] {"value"}));
    ServletRequest request = new ServletRequest(source);
    assertThat(request.hasParam("param")).isTrue();
  }

  @Test
  public void read_param_from_source() {
    when(source.getParameter("param")).thenReturn("value");

    assertThat(underTest.readParam("param")).isEqualTo("value");
  }

  @Test
  public void read_multi_param_from_source_with_values() {
    when(source.getParameterValues("param")).thenReturn(new String[]{"firstValue", "secondValue", "thirdValue"});

    List<String> result = underTest.readMultiParam("param");

    assertThat(result).containsExactly("firstValue", "secondValue", "thirdValue");
  }

  @Test
  public void read_multi_param_from_source_with_one_value() {
    when(source.getParameterValues("param")).thenReturn(new String[]{"firstValue"});

    List<String> result = underTest.readMultiParam("param");

    assertThat(result).containsExactly("firstValue");
  }

  @Test
  public void read_multi_param_from_source_without_value() {
    when(source.getParameterValues("param")).thenReturn(null);

    List<String> result = underTest.readMultiParam("param");

    assertThat(result).isEmpty();
  }

  @Test
  public void read_input_stream() throws Exception {
    when(source.getContentType()).thenReturn("multipart/form-data");
    InputStream file = mock(InputStream.class);
    Part part = mock(Part.class);
    when(part.getInputStream()).thenReturn(file);
    when(part.getSize()).thenReturn(10L);
    when(source.getPart("param1")).thenReturn(part);

    assertThat(underTest.readInputStreamParam("param1")).isEqualTo(file);
    assertThat(underTest.readInputStreamParam("param2")).isNull();
  }

  @Test
  public void read_no_input_stream_when_part_size_is_zero() throws Exception {
    when(source.getContentType()).thenReturn("multipart/form-data");
    InputStream file = mock(InputStream.class);
    Part part = mock(Part.class);
    when(part.getInputStream()).thenReturn(file);
    when(part.getSize()).thenReturn(0L);
    when(source.getPart("param1")).thenReturn(part);

    assertThat(underTest.readInputStreamParam("param1")).isNull();
  }

  @Test
  public void return_no_input_stream_when_content_type_is_not_multipart() throws Exception {
    when(source.getContentType()).thenReturn("multipart/form-data");

    assertThat(underTest.readInputStreamParam("param1")).isNull();
  }

  @Test
  public void return_no_input_stream_when_content_type_is_null() throws Exception {
    when(source.getContentType()).thenReturn(null);

    assertThat(underTest.readInputStreamParam("param1")).isNull();
  }

  @Test
  public void throw_ISE_when_invalid_part() throws Exception {
    when(source.getContentType()).thenReturn("multipart/form-data");
    InputStream file = mock(InputStream.class);
    Part part = mock(Part.class);
    when(part.getSize()).thenReturn(0L);
    when(part.getInputStream()).thenReturn(file);
    doThrow(IllegalArgumentException.class).when(source).getPart("param1");

    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage("Can't read file part");

    underTest.readInputStreamParam("param1");
  }

  @Test
  public void getPath() throws Exception {
    when(source.getRequestURI()).thenReturn("/sonar/path/to/resource/search");
    when(source.getContextPath()).thenReturn("/sonar");

    assertThat(underTest.getPath()).isEqualTo("/path/to/resource/search");
  }

  @Test
  public void to_string() {
    when(source.getRequestURL()).thenReturn(new StringBuffer("http:localhost:9000/api/issues"));
    assertThat(underTest.toString()).isEqualTo("http:localhost:9000/api/issues");

    when(source.getQueryString()).thenReturn("components=sonar");

    assertThat(underTest.toString()).isEqualTo("http:localhost:9000/api/issues?components=sonar");
  }
}
