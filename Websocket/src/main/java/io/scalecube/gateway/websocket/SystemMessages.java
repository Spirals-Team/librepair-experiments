package io.scalecube.gateway.websocket;

import io.scalecube.services.api.ErrorData;
import io.scalecube.services.api.ServiceMessage;

import java.util.function.Function;

public interface SystemMessages {
  String NAMESPACE = "io.scalecube.websocket.gateway";
  String MSG_QUALIFIER = NAMESPACE + "/%s";

  String SERVICE_DISCONNECTED_Q = String.format(MSG_QUALIFIER, "serviceDisconnected");

  String SERVICE_DISCONNECTED_ERR = "Service %s got disconnected.";

  Function<String, ServiceMessage> SERVICE_DISCONNECTED = (serviceQualifier) -> ServiceMessage.builder()
      .qualifier(SERVICE_DISCONNECTED_Q)
      .data(new ErrorData(503, String.format(SERVICE_DISCONNECTED_ERR, serviceQualifier)))
      .build();

}
