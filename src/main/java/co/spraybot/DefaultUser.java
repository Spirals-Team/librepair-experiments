package co.spraybot;

public class DefaultUser implements User {

    private String id;
    private String name;

    public DefaultUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

}
