package up.god.micro.async;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;

@Queue
public class JavaDirectWorker {

    @Address("ZERO://ASYNC/JAVA/DIRECT")
    public String sayHello(final String age) {
        return "Hello: " + age;
    }

    @Address("ZERO://ASYNC/JAVA/POJO")
    public String sayPojo(final JavaJson pojo) {
        return pojo.toString();
    }
}
