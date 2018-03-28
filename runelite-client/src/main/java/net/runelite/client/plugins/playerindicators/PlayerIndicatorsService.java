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
package net.runelite.client.plugins.playerindicators;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.ClanMember;
import net.runelite.api.ClanMemberRank;
import net.runelite.api.Client;
import net.runelite.api.IndexedSprite;
import net.runelite.api.Player;
import net.runelite.api.PlayerNameMask;
import net.runelite.api.SpritePixels;

@Singleton
public class PlayerIndicatorsService
{
	private static final String DEFAULT_COLOR = parseColor(new Color(255, 255, 255));
	private static final int NUMBER_OF_RANKS = 8;
	private static final int RGB_PIXEL = 1;
	private final Map<MapDot, Color> mapDotsColorMap = new HashMap<>();
	private final Client client;
	private final PlayerIndicatorsConfig config;

	@Inject
	private PlayerIndicatorsService(Client client, PlayerIndicatorsConfig config)
	{
		this.config = config;
		this.client = client;
	}

	public void updateConfig()
	{
		// Update the map dot colors
		final SpritePixels[] mapDots = client.getMapDots();
		updateColor(mapDots, MapDot.NPC);
		updateColor(mapDots, MapDot.PLAYER);
		updateColor(mapDots, MapDot.CLAN);
		updateColor(mapDots, MapDot.FRIEND);
		updateColor(mapDots, MapDot.TEAM);

		// Update mask
		int baseMask = 0x000000000;

		if (config.drawFriendNames())
		{
			baseMask |= PlayerNameMask.DRAW_FRIEND_NAME;
		}

		if (config.drawClanMemberNames())
		{
			baseMask |= PlayerNameMask.DRAW_CLAN_NAME;
		}

		if (config.drawOwnName())
		{
			baseMask |= PlayerNameMask.DRAW_OWN_NAME;
		}

		if (config.drawNonOwnNames())
		{
			baseMask |= PlayerNameMask.DRAW_ALL_EXCEPT_OWN_NAME;
		}

		client.setPlayerNameMask(baseMask);
	}

	public void updatePlayers()
	{
		// Update player names
		forEachPlayer((player, color) -> injectData(player, parseColor(color)));
	}

	public void forEachPlayer(final BiConsumer<Player, Color> consumer)
	{
		if (!config.drawOwnName() && !config.drawClanMemberNames()
			&& !config.drawFriendNames() && !config.drawNonOwnNames()
			&& !config.drawTeamMemberNames())
		{
			return;
		}

		final Player localPlayer = client.getLocalPlayer();

		for (Player player : client.getPlayers())
		{
			if (player == null || player.getName() == null)
			{
				continue;
			}

			boolean isClanMember = player.isClanMember();

			if (player == client.getLocalPlayer())
			{
				if (config.drawOwnName())
				{
					consumer.accept(player, mapDotsColorMap.get(MapDot.PLAYER));
				}
			}
			else if (config.drawFriendNames() && player.isFriend())
			{
				consumer.accept(player, mapDotsColorMap.get(MapDot.FRIEND));
			}
			else if (config.drawClanMemberNames() && isClanMember)
			{
				consumer.accept(player, mapDotsColorMap.get(MapDot.CLAN));
			}
			else if (config.drawTeamMemberNames() && localPlayer.getTeam() > 0 && localPlayer.getTeam() == player.getTeam())
			{
				consumer.accept(player, mapDotsColorMap.get(MapDot.TEAM));
			}
			else if (config.drawNonOwnNames() && !isClanMember)
			{
				consumer.accept(player, mapDotsColorMap.get(MapDot.PLAYER));
			}
		}
	}

	private void injectData(final Player player, final String color)
	{
		final StringBuilder stringBuilder = new StringBuilder();

		final IndexedSprite[] modIcons = client.getModIcons();
		final int startIndex = modIcons.length - NUMBER_OF_RANKS;

		if (startIndex >= 0 && player.isClanMember())
		{
			final ClanMember[] clanMembersArr = client.getClanMembers();

			if (clanMembersArr != null && clanMembersArr.length > 0)
			{
				for (ClanMember clanMember : clanMembersArr)
				{
					if (clanMember != null && clanMember.getUsername().equals(player.getCleanName()))
					{
						if (clanMember.getRank() != ClanMemberRank.UNRANKED)
						{
							stringBuilder
								.append("<img=")
								.append(startIndex + clanMember.getRank().getValue())
								.append(">");
						}

						break;
					}
				}
			}
		}

		if (!mapDotsColorMap.get(MapDot.PLAYER).equals(color))
		{
			stringBuilder.append("<col=").append(color).append(">");
		}

		if (stringBuilder.length() != 0)
		{
			player.setName(stringBuilder.toString() + player.getCleanName());
		}
	}

	private void updateColor(final SpritePixels[] mapDots, final MapDot mapDot)
	{
		final Color color = new Color(mapDots[mapDot.ordinal()].getPixels()[RGB_PIXEL]);
		mapDotsColorMap.put(mapDot, color);
	}

	private static String parseColor(final Color mapDotColor)
	{
		return String.format("%02X%02X%02X", mapDotColor.getRed(), mapDotColor.getGreen(), mapDotColor.getBlue());
	}
}
