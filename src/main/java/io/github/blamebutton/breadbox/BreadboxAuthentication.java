package io.github.blamebutton.breadbox;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

class BreadboxAuthentication {

    static IDiscordClient createClient(String token) {
        try {
            ClientBuilder builder = new ClientBuilder().withToken(token);
            return builder.login();
        } catch (DiscordException e) {
            e.printStackTrace();
            return null;
        }
    }
}
