package com.tangly.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.ServletContext;

@Configuration
@Slf4j
public class WebSocketConfig {

    @Bean
    public ServletContextAware endpointExporterInitializer(final ApplicationContext applicationContext) {
        return servletContext -> {
            ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
            serverEndpointExporter.setApplicationContext(applicationContext);
            try {
                serverEndpointExporter.afterPropertiesSet();
            } catch (Exception e) {
                //单元测试时没有WebSocket环境 ， 不影响其他模块测试
                log.debug("WebSocket 环境没有启动",e);
            }
        };
    }

}
