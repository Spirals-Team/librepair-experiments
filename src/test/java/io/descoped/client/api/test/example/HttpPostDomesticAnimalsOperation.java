package io.descoped.client.api.test.example;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;
import io.descoped.client.api.test.impl.OperationHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public class HttpPostDomesticAnimalsOperation extends OperationHandlerImpl implements OperationHandler {

    private static final Logger log = LoggerFactory.getLogger(HttpPostDomesticAnimalsOperation.class);

    public HttpPostDomesticAnimalsOperation(OutcomeHandler outcomeHandler) {
        super(outcomeHandler);
    }

    @Override
    public boolean execute() {
        HttpRequest req = HttpRequest.post("http://data.ssb.no/api/v0/en/table/03791");
        String query = "{\n" +
                "  \"query\": [\n" +
                "    {\n" +
                "      \"code\": \"Region\",\n" +
                "      \"selection\": {\n" +
                "        \"filter\": \"item\",\n" +
                "        \"values\": [\n" +
                "          \"0\",\n" +
                "          \"01\",\n" +
                "          \"02-03\"\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"Husdyr\",\n" +
                "      \"selection\": {\n" +
                "        \"filter\": \"item\",\n" +
                "        \"values\": [\n" +
                "          \"01\",\n" +
                "          \"13a\",\n" +
                "          \"02\"\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"ContentsCode\",\n" +
                "      \"selection\": {\n" +
                "        \"filter\": \"item\",\n" +
                "        \"values\": [\n" +
                "          \"HusdyrTal\"\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"Tid\",\n" +
                "      \"selection\": {\n" +
                "        \"filter\": \"item\",\n" +
                "        \"values\": [\n" +
                "          \"1998\",\n" +
                "          \"1999\",\n" +
                "          \"2000\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"response\": {\n" +
                "    \"format\": \"json-stat\"\n" +
                "  }\n" +
                "}\n";

        log.trace("Request query:\n{}", query);
        req.send(query.getBytes());
        getOutcomeHandler().setStatusCode(req.code());
        getOutcomeHandler().setReceivedBytes(req.bytes());
        log.trace("Response {} - \n{}", req.code(), HttpPostDomesticAnimalsOutcome.prettyPrint( getOutcomeHandler().getContent() ));
        getOutcomeHandler().setContentLength(req.contentLength());
        getOutcomeHandler().setResponseHeaders(req.headers());
        return getOutcomeHandler().ok(); // here we should do a DealWithSuccess | DealWithFailure
    }


}
