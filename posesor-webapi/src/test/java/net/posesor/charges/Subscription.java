package net.posesor.charges;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

@Slf4j
public class Subscription extends StompSessionHandlerAdapter {

    private final String destination;
    private final StompFrameHandler handler;

    public Subscription(String destination, StompFrameHandler handler) {
        this.destination = destination;
        this.handler = handler;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("Connected. Trying to subscribe...");
        session.subscribe(this.destination, handler);
    }
}