package io.scalecube.services.transport.server.api;

import io.scalecube.services.methods.ServiceMethodRegistry;

import java.net.InetSocketAddress;

public interface ServerTransport {

  InetSocketAddress bindAwait(InetSocketAddress address, ServiceMethodRegistry methodRegistry);

}
