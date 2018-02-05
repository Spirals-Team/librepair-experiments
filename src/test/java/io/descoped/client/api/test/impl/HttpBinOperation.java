package io.descoped.client.api.test.impl;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public class HttpBinOperation extends OperationHandlerImpl implements OperationHandler {

    private static final Logger log = LoggerFactory.getLogger(HttpBinOperation.class);

    public HttpBinOperation(OutcomeHandler outcomeHandler) {
        super(outcomeHandler);
    }

    @Override
    public boolean execute() {
        HttpRequest req = HttpRequest.post("http://httpbin.org/post");
        req.send("name=ove");
        getOutcomeHandler().setStatusCode(req.code());
        getOutcomeHandler().setReceivedBytes(req.bytes());
        log.trace("{} - {}", req.code(), getOutcomeHandler().getContent());
        getOutcomeHandler().setContentLength(req.contentLength());
        getOutcomeHandler().setResponseHeaders(req.headers());
        return getOutcomeHandler().ok(); // here we should do a DealWithSuccess | DealWithFailure
    }


}
