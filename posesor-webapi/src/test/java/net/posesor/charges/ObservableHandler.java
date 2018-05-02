package net.posesor.charges;

import io.reactivex.Observer;
import lombok.val;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public final class ObservableHandler<T> implements StompFrameHandler {
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