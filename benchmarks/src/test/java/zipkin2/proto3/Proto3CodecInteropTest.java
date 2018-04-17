/**
 * Copyright 2015-2018 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin2.proto3;

import com.google.protobuf.ByteString;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class Proto3CodecInteropTest {
  static final zipkin2.Endpoint ORDER = zipkin2.Endpoint.newBuilder()
    .serviceName("订单维护服务")
    .ip("7::0.128.128.127")
    .build();

  static final zipkin2.Endpoint PROFILE = zipkin2.Endpoint.newBuilder()
    .serviceName("个人信息服务")
    .ip("192.168.99.101")
    .port(9000)
    .build();

  static final zipkin2.Span SPAN = zipkin2.Span.newBuilder()
    .traceId("4d1e00c0db9010db86154a4ba6e91385")
    .parentId("86154a4ba6e91385")
    .id("4d1e00c0db9010db")
    .name("个人信息查询")
    .kind(zipkin2.Span.Kind.CLIENT)
    .localEndpoint(ORDER)
    .remoteEndpoint(PROFILE)
    .timestamp(1472470996199000L)
    .duration(207000L)
    .putTag("http.path", "/person/profile/query")
    .putTag("http.status_code", "403")
    .putTag("clnt/finagle.version", "6.45.0")
    .putTag("error", "此用户没有操作权限")
    .build();

  @Test
  public void spanSerializationIsCompatible() throws Exception{
    Span span = Span.newBuilder()
      .setTraceId(ByteString.copyFrom(Hex.decodeHex(SPAN.traceId().toCharArray())))
      .setParentId(ByteString.copyFrom(Hex.decodeHex(SPAN.parentId().toCharArray())))
      .setId(ByteString.copyFrom(Hex.decodeHex(SPAN.id().toCharArray())))
      .setKind(Span.Kind.valueOf(SPAN.kind().name()))
      .setName(SPAN.name())
      .setTimestamp(SPAN.timestampAsLong())
      .setDuration(SPAN.durationAsLong())
      .setLocalEndpoint(Endpoint.newBuilder()
        .setServiceName(ORDER.serviceName())
        .setIpv6(ByteString.copyFrom(ORDER.ipv6Bytes())
        ).build()
      )
      .setRemoteEndpoint(Endpoint.newBuilder()
        .setServiceName(PROFILE.serviceName())
        .setIpv4(ByteString.copyFrom(PROFILE.ipv4Bytes())).build()
      ).putAllTags(SPAN.tags())
      .build();

    System.err.println(span.toString());
  }
}
