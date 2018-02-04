package net.runelite.client.discord;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DiscordPresence
{
	private String state;
	private String details;
	private Instant startTimestamp;
	private Instant endTimestamp;
	private String largeImageKey;
	private String largeImageText;
	private String smallImageKey;
	private String smallImageText;
	private String partyId;
	private int partySize;
	private int partyMax;
	private String matchSecret;
	private String joinSecret;
	private String spectateSecret;
	private boolean instance;
}
