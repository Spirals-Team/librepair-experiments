package up.god.micro.worker;

public class DynamicActor implements DynamicApi {
    @Override
    public String sayEnvelop(final String name) {
        return name;
    }
}
