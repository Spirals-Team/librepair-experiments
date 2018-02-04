/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.google.common.eventbus.EventBus;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLiteProperties;
import net.runelite.client.discord.events.DiscordDisconnected;
import net.runelite.client.discord.events.DiscordErrored;
import net.runelite.client.discord.events.DiscordJoinGame;
import net.runelite.client.discord.events.DiscordJoinRequest;
import net.runelite.client.discord.events.DiscordReady;
import net.runelite.client.discord.events.DiscordSpectateGame;

@Singleton
@Slf4j
public class DiscordService implements AutoCloseable
{
	@Inject
	private EventBus eventBus;

	@Inject
	private RuneLiteProperties runeLiteProperties;

	@Inject
	private ScheduledExecutorService executorService;

	private final DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers();
	private final DiscordRPC discordRPC = DiscordRPC.INSTANCE;

	public void init()
	{
		log.info("Initializing Discord RPC service.");
		discordEventHandlers.ready = this::ready;
		discordEventHandlers.disconnected = this::disconnected;
		discordEventHandlers.errored = this::errored;
		discordEventHandlers.joinGame = this::joinGame;
		discordEventHandlers.spectateGame = this::spectateGame;
		discordEventHandlers.joinRequest = this::joinRequest;
		discordRPC.Discord_Initialize(runeLiteProperties.getDiscordAppId(), discordEventHandlers, true, null);
		executorService.scheduleAtFixedRate(discordRPC::Discord_RunCallbacks, 0, 2, TimeUnit.SECONDS);
	}

	@Override
	public void close()
	{
		discordRPC.Discord_Shutdown();
	}

	public void updatePresence(DiscordPresence discordPresence)
	{
		final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
		discordRichPresence.state = discordPresence.getState();
		discordRichPresence.details = discordPresence.getDetails();
		discordRichPresence.startTimestamp = discordPresence.getStartTimestamp() != null
			? discordPresence.getStartTimestamp().getEpochSecond()
			: 0;
		discordRichPresence.endTimestamp = discordPresence.getEndTimestamp() != null
			? discordPresence.getEndTimestamp().getEpochSecond()
			: 0;
		discordRichPresence.largeImageKey = Strings.isNullOrEmpty(discordPresence.getLargeImageKey())
			? "default"
			: discordPresence.getLargeImageKey();
		discordRichPresence.largeImageText = discordPresence.getLargeImageText();
		discordRichPresence.smallImageKey = Strings.isNullOrEmpty(discordPresence.getSmallImageKey())
			? "default"
			: discordPresence.getLargeImageKey();
		discordRichPresence.smallImageText = discordPresence.getSmallImageText();
		discordRichPresence.partyId = discordPresence.getPartyId();
		discordRichPresence.partySize = discordPresence.getPartySize();
		discordRichPresence.partyMax = discordPresence.getPartyMax();
		discordRichPresence.matchSecret = discordPresence.getMatchSecret();
		discordRichPresence.joinSecret = discordPresence.getJoinSecret();
		discordRichPresence.spectateSecret = discordPresence.getSpectateSecret();
		discordRichPresence.instance = (byte) (discordPresence.isInstance() ? 1 : 0);
		discordRPC.Discord_UpdatePresence(discordRichPresence);
	}

	private void ready()
	{
		log.info("Discord RPC service is ready.");
		eventBus.post(new DiscordReady());
	}

	private void disconnected(int errorCode, String message)
	{
		eventBus.post(new DiscordDisconnected(errorCode, message));
	}

	private void errored(int errorCode, String message)
	{
		eventBus.post(new DiscordErrored(errorCode, message));
	}

	private void joinGame(String joinSecret)
	{
		eventBus.post(new DiscordJoinGame(joinSecret));
	}

	private void spectateGame(String spectateSecret)
	{
		eventBus.post(new DiscordSpectateGame(spectateSecret));
	}

	private void joinRequest(club.minnced.discord.rpc.DiscordJoinRequest joinRequest)
	{
		eventBus.post(new DiscordJoinRequest(
			joinRequest.userId,
			joinRequest.username,
			joinRequest.discriminator,
			joinRequest.avatar));
	}
}
