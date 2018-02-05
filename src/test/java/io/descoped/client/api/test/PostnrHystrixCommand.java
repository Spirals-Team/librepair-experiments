package io.descoped.client.api.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.descoped.client.external.posten.PostalCode;
import io.descoped.client.http.Client;
import io.descoped.client.http.Request;
import io.descoped.client.http.Response;
import io.descoped.client.http.ResponseBodyHandler;

import java.net.URI;
import java.util.Map;

public class PostnrHystrixCommand extends HystrixCommand<Map<String,PostalCode>> {

    protected PostnrHystrixCommand() {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("OperationCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(300000))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(100)
                                .withQueueSizeRejectionThreshold(100)
                                .withCoreSize(4)));

        HystrixRequestContext.initializeContext();
    }

    @Override
    protected Map<String, PostalCode> run() throws Exception {
        Request request = Request.builder(URI.create("https://www.bring.no/postnummerregister-ansi.txt")).GET().build();
        ResponseBodyHandler<Map<String, PostalCode>> handler = new PostnrProcessor();
        Response<Map<String,PostalCode>> response = Client.create().send​(request, handler);
        return response.body().get();
    }

}
