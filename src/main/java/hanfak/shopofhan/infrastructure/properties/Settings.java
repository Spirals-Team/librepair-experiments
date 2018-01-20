package hanfak.shopofhan.infrastructure.properties;

public class Settings implements DatabaseSettings, ServerSettings {
    public static final String MAC = "Mac";
    private final PropertiesReader propertiesReader;

    public Settings(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Override
    public int serverPort() {
        return Integer.parseInt(propertiesReader.readProperty("server.port"));
    }

    @Override
    public String databaseURL() {
        return propertiesReader.readProperty("database.shopOfHanUrl");
    }

    @Override
    public String databaseUsername() {
        return propertiesReader.readProperty("database.username");
    }

    @Override
    public String databasePassword() {
        return propertiesReader.readProperty("database.password");
    }
}
