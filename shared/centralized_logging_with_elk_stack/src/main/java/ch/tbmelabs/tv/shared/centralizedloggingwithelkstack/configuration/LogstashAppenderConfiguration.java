package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration;

import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.SocketAppender;
import org.apache.logging.log4j.core.layout.JsonLayout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogstashAppenderConfiguration {
  private static final Logger LOGGER = LogManager.getLogger(LogstashAppenderConfiguration.class);

  private static final Integer BUFFER_SIZE = 2048;

  private static String appenderName = "logstash";

  @Value("${LOGSTASH_HOST}")
  private String logstashHost;

  @Value("${LOGSTASH_PORT}")
  private Integer logstashPort;

  @Value("${spring.application.name:null}")
  private String serviceName;

  @PostConstruct
  public void initBean() {
    LOGGER.info("Initializing..");

    if (serviceName == null) {
      throw new IllegalArgumentException(
          "Specify an application name (\"spring.application.name\") to use the centralized logging.");
    }

    // @formatter:off
    LOGGER.info("Configuring new " + SocketAppender.class + " with Logstash and ELK stack\n"
        + "Using the following configuration:\n"
        + " - Server: " + logstashHost + "\n"
        + " - Port: " + logstashPort + "\n"
        + " - Name: " + serviceName + "\n"
        + " - Buffer: "+ BUFFER_SIZE);

    SocketAppender logstashAppender = SocketAppender.newBuilder()
        .withName(appenderName)
        .withHost(logstashHost)
        .withPort(logstashPort)
        .withImmediateFail(false)
        .withBufferSize(BUFFER_SIZE)
        .withReconnectDelayMillis(-1)
        .withLayout(
            // JsonLayout.createDefaultLayout()
            JsonLayout.createLayout(
                JsonLayout.createDefaultLayout().getConfiguration(),
                true,
                true,
                true,
                true,
                true,
                true, 
                "", // Header patterns
                "", // Footer pattern
                StandardCharsets.UTF_8,
                true)
            )
        .build();
    // @formatter:on

    // TODO: How to add service name in json body?
    // MDC.put("serviceName", serviceName);

    logstashAppender.start();
    ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addAppender(logstashAppender);
  }

  public static String getAppenderName() {
    return LogstashAppenderConfiguration.appenderName;
  }

  public static void setAppenderName(String appenderName) {
    LogstashAppenderConfiguration.appenderName = appenderName;
  }
}