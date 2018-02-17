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
package net.runelite.client.plugins.chathistory;

import com.google.common.collect.EvictingQueue;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.SetMessage;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Chat History"
)
@Slf4j
public class ChatHistoryPlugin extends Plugin
{
	private static final String WELCOME_MESSAGE = "Welcome to RuneScape.";
	private Queue<QueuedMessage> messageQueue;

	@Inject
	private Client client;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private ChatHistoryConfig config;

	@Override
	protected void startUp()
	{
		messageQueue = EvictingQueue.create(100);
	}

	@Override
	protected void shutDown()
	{
		messageQueue.clear();
		clearTimestamps();
	}

	@Provides
	private ChatHistoryConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ChatHistoryConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals("chathistory"))
		{
			return;
		}

		if (!config.showTimestamps())
		{
			clearTimestamps();
		}
		else if (!config.persistChatHistory())
		{
			messageQueue.clear();
		}
	}

	@Subscribe
	public void onSetMessage(SetMessage setMessage)
	{
		if (!config.showTimestamps())
		{
			return;
		}

		if (setMessage.getMessageNode().getRuneLiteTime() == null)
		{
			setMessage.getMessageNode().setRuneLiteTime(Instant.now());
			chatMessageManager.update(setMessage.getMessageNode());
		}
	}

	@Subscribe
	public void onAddMessage(ChatMessage chatMessage)
	{
		if (!config.persistChatHistory())
		{
			return;
		}

		if (chatMessage.getMessage().equals(WELCOME_MESSAGE)) {
			QueuedMessage message;

			while ((message = messageQueue.poll()) != null)
			{
				chatMessageManager.queue(message);
			}

			return;
		}

		if (chatMessage.getType() != ChatMessageType.SERVER && chatMessage.getType() != ChatMessageType.CLANCHAT_INFO)
		{
			messageQueue.offer(QueuedMessage.builder()
				.type(chatMessage.getType())
				.sender(chatMessage.getSender())
				.message(chatMessage.getMessage())
				.clan(chatMessage.getClan())
				.build());
		}
	}

	private void clearTimestamps()
	{

		client.getChatLineMap().values().stream()
			.flatMap(chatLineBuffer -> Arrays.stream(chatLineBuffer.getLines()))
			.filter(Objects::nonNull)
			.forEach(line -> line.setRuneLiteTime(null));

		chatMessageManager.refreshAll();
	}
}
