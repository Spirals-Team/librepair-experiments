package world.bentobox.bentobox.api.placeholders;

import world.bentobox.bentobox.api.user.User;

/**
 * @author Poslovitch
 */
public class Placeholder {

    private String identifier;
    private PlaceholderRequest request;

    Placeholder(String identifier, PlaceholderRequest request) {
        this.identifier = identifier;
        this.request = request;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public PlaceholderRequest getRequest() {
        return request;
    }

    public interface PlaceholderRequest {
        String request(User user);
    }
}
