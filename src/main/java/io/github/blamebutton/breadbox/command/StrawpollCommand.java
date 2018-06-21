package io.github.blamebutton.breadbox.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.util.List;

/**
 * Command for creating strawpolls.
 *
 * <pre>
 * Usage: ?strawpoll &lt;options>
 * </pre>
 */
public class StrawpollCommand implements ICommand {

    private static final Logger logger = LoggerFactory.getLogger(StrawpollCommand.class);

    @Override
    public void handle(IMessage message, List<String> args) {
        message.getChannel().sendMessage("got it");
    }

    @Override
    public String getUsage() {
        return "<title> [options, min. 2]";
    }

    @Override
    public String getDescription() {
        return "Make a [strawpoll](https://strawpoll.me) using the given title and options.";
    }
}
