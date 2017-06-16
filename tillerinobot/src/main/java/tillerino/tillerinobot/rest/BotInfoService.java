package tillerino.tillerinobot.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pircbotx.PircBotX;

import lombok.Data;
import tillerino.tillerinobot.BotRunner;

@Singleton
@Path("/botinfo")
public class BotInfoService {
	private final BotRunner bot;

	@Inject
	public BotInfoService(BotRunner bot, BotInfo botInfo) {
		super();
		this.bot = bot;
		this.botInfo = botInfo;
	}

	@Data
	@Singleton
	public static class BotInfo {
		boolean isConnected;
		long runningSince;
		long lastPingDeath;
		long lastInteraction;
		long lastSentMessage;
	}

	private final BotInfo botInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BotInfo botinfo() {
		PircBotX pircBot = bot.getBot();
		if (pircBot != null) {
			botInfo.isConnected = pircBot.isConnected();
		} else {
			botInfo.isConnected = false;
		}
		return botInfo;
	}
}
