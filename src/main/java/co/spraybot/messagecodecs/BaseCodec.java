package co.spraybot.messagecodecs;

import io.vertx.core.eventbus.MessageCodec;

abstract class BaseCodec<S, R> implements MessageCodec<S, R> {

    @Override
    public byte systemCodecID() {
        return -1;
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public R transform(S object) {
        return (R) object;
    }

}
