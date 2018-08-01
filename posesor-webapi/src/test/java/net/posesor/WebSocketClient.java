package net.posesor;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.ReplaySubject;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Base64;

import static java.util.Collections.singletonList;

public final class WebSocketClient {

    private final WebSocketStompClient client;

    public WebSocketClient() {
        val sockClient = new SockJsClient(singletonList(new WebSocketTransport(new StandardWebSocketClient())));
        client = new WebSocketStompClient(sockClient);
        client.setMessageConverter(new MappingJackson2MessageConverter());
    }

    /**
     *
     * @param port
     * @param userName
     * @param topic e.g. "/TOPIC/charges"
     * @return
     */
    @SneakyThrows
    public <T> Observable<T> connect(int port, String userName, String topic, Class<T> clazz) {
        val credentials = new Credentials(userName, userName);
        val dispatcher = ReplaySubject.<T>create();
        val handler = new ObservableHandler<T>(clazz, dispatcher);
        val session = new Subscription( "/user" + topic , handler);
        client
                .connect("ws://{host}:{port}/api/stomp-endpoint", new WebSocketHttpHeaders(credentials.httpHeaders()), session, "127.0.0.1", port)
                .get();
        return dispatcher;
    }

    public static class Credentials {
        private final String username;
        private final String password;

        public Credentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public HttpHeaders httpHeaders() {
            String notEncoded = this.username + ":" + this.password;
            String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + encodedAuth);
            return headers;
        }

    }

    final class ObservableHandler<T> implements StompFrameHandler {
        private final Class<T> clazz;
        private final Observer<T> listener;

        public ObservableHandler(Class<T> clazz, Observer<T> listener) {
            this.clazz = clazz;
            this.listener = listener;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return clazz;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            val value = (T) payload;
            listener.onNext(value);
        }
    }

    public class Subscription extends StompSessionHandlerAdapter {

        private final String destination;
        private final StompFrameHandler handler;

        public Subscription(String destination, StompFrameHandler handler) {
            this.destination = destination;
            this.handler = handler;
        }

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            session.subscribe(this.destination, handler);
        }
    }
}