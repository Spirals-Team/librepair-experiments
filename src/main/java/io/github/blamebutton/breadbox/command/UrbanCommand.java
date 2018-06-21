package io.github.blamebutton.breadbox.command;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.github.blamebutton.breadbox.util.UrlUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.awt.*;
import java.util.List;

public class UrbanCommand implements ICommand {

    private static final Logger logger = LoggerFactory.getLogger(UrbanCommand.class);
    private static final String URBAN_SEARCH_URL = "https://api.urbandictionary.com/v0/define?term=%s";
    private final String thumbnail = "https://cdn.discordapp.com/attachments/456867763516866570/456899795207061530/" +
            "logo-1b439b7fa6572b659fbef161d8946372f472ef8e7169db1e47d21c91b410b918.png";

    @Override
    public void handle(IMessage message, List<String> args) {
        if (args.size() < 1) {
            RequestBuffer.request(() -> {
                message.getChannel().sendMessage("A word / search term is required.");
            });
            return;
        }
        try {
            String term = String.join(" ", args);
            String url = String.format(URBAN_SEARCH_URL, UrlUtil.encode(term));
            JSONArray list = Unirest.get(url)
                    .asJson()
                    .getBody()
                    .getObject()
                    .getJSONArray("list");
            if (list.length() > 0) {
                RequestBuffer.request(() -> {
                    JSONObject response = list.getJSONObject(0);
                    String definition = response.getString("definition");
                    String example = response.getString("example");
                    String permalink = response.getString("permalink");
                    EmbedBuilder embed = new EmbedBuilder()
                            .withColor(Color.decode("#1D2439"))
                            .withThumbnail(thumbnail)
                            .withTitle(String.format("Urban Definition: %s", term))
                            .withDescription(definition)
                            .withUrl(permalink)
                            .withFooterText("Example: " + example);
                    message.getChannel().sendMessage(embed.build());
                });
            } else {
                RequestBuffer.request(() ->
                        message.getChannel().sendMessage(String.format("Search term `%s` did not yield any results.", term)));
            }
        } catch (UnirestException e) {
            logger.error("Error occured while fetching urban definition", e);
        }
    }

    @Override
    public String getUsage() {
        return "<word / search term>";
    }

    @Override
    public String getDescription() {
        return "Searches the [urban dictionary](https://urbandictionary.com/)";
    }
}
