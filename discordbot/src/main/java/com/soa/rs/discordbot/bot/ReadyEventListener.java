package com.soa.rs.discordbot.bot;

import com.soa.rs.discordbot.bot.events.SoaEventListerScheduler;
import com.soa.rs.discordbot.bot.events.SoaNewsListerScheduler;
import com.soa.rs.discordbot.bot.events.SoaTaskScheduler;
import com.soa.rs.discordbot.bot.events.UserTrackingScheduler;
import com.soa.rs.discordbot.cfg.DiscordCfgFactory;
import com.soa.rs.discordbot.util.SoaClientHelper;
import com.soa.rs.discordbot.util.SoaLogging;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.Image;

/**
 * The Ready Event Listener handles the configuration of bot settings along with
 * starting any automated tasks which will run periodically during the bot's
 * runtime. The <tt>ReadyEvent</tt> is triggered upon the bot being ready to
 * interact with the Discord API.
 */
public class ReadyEventListener implements IListener<ReadyEvent> {

	private IDiscordClient client;

	/**
	 * Handles the ReadyEvent sent by the Discord API, this sets up the bot's user
	 * settings and schedules appropriate automated tasks.
	 */
	@Override
	public void handle(ReadyEvent event) {

		client = event.getClient();
		setDiscordUserSettings();

		if (DiscordCfgFactory.getConfig().getEventListingEvent() != null
				&& DiscordCfgFactory.getConfig().getEventListingEvent().isEnabled()) {
			SoaTaskScheduler listScheduler = new SoaEventListerScheduler(client,
					DiscordCfgFactory.getConfig().getEventListingEvent().getUrl());
			listScheduler.scheduleTask();
		}
		if (DiscordCfgFactory.getConfig().getNewsListingEvent() != null
				&& DiscordCfgFactory.getConfig().getNewsListingEvent().isEnabled()) {
			SoaTaskScheduler newsScheduler = new SoaNewsListerScheduler(client,
					DiscordCfgFactory.getConfig().getNewsListingEvent().getUrl());
			newsScheduler.scheduleTask();
		}
		if (DiscordCfgFactory.getConfig().getUserTrackingEvent() != null
				&& DiscordCfgFactory.getConfig().getUserTrackingEvent().isEnabled()) {
			UserTrackingScheduler trackingScheduler = new UserTrackingScheduler(client);
			trackingScheduler.scheduleTask();
		}
	}

	/**
	 * Sets up the bot's user settings
	 */
	private void setDiscordUserSettings() {

		if (DiscordCfgFactory.getInstance().getAvatarUrl() != null) {
			SoaLogging.getLogger().info("Setting bot avatar");

			String imageType = DiscordCfgFactory.getInstance().getAvatarUrl()
					.substring(DiscordCfgFactory.getInstance().getAvatarUrl().lastIndexOf(".") + 1);

			SoaClientHelper.setBotAvatar(client,
					Image.forUrl(imageType, DiscordCfgFactory.getInstance().getAvatarUrl()));
		}
		if (DiscordCfgFactory.getInstance().getBotname() != null) {

			SoaLogging.getLogger()
					.info("Setting bot username to '" + DiscordCfgFactory.getInstance().getBotname() + "'");
			SoaClientHelper.setBotName(client, DiscordCfgFactory.getInstance().getBotname());
		}

		if (DiscordCfgFactory.getConfig().getDefaultStatus() != null)
			SoaClientHelper.setBotPresence(client, StatusType.ONLINE, ActivityType.PLAYING,
					DiscordCfgFactory.getConfig().getDefaultStatus());
	}

}
