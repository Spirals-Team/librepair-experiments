/*
 * Copyright (c) 2018, Seth <Sethtroll3@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Lars <lars.oernlo@gmail.com>
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
package net.runelite.client.plugins.motherlode;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;

import net.runelite.api.Client;
import net.runelite.api.Varbits;


import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.WallObject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;

import static net.runelite.api.ObjectID.ORE_VEIN_26661;
import static net.runelite.api.ObjectID.ORE_VEIN_26662;
import static net.runelite.api.ObjectID.ORE_VEIN_26663;
import static net.runelite.api.ObjectID.ORE_VEIN_26664;
import static net.runelite.api.ObjectID.ROCKFALL;
import static net.runelite.api.ObjectID.ROCKFALL_26680;

import net.runelite.api.events.MapRegionChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WallObjectChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;

import net.runelite.api.queries.InventoryItemQuery;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.util.QueryRunner;

@PluginDescriptor(
	name = "Motherlode Mine",
	enabledByDefault = false
)
public class MotherlodePlugin extends Plugin
{
	public int curSackSize;
	public int MAX_SACK_SIZE;

	public boolean inMLM;

	private static final Set<Integer> MOTHERLODE_MAP_REGIONS = ImmutableSet.of(14679, 14680, 14681, 14935, 14936, 14937, 15191, 15192, 15193);
	private static final Set<Integer> MINE_SPOTS = Sets.newHashSet(ORE_VEIN_26661, ORE_VEIN_26662, ORE_VEIN_26663, ORE_VEIN_26664);
	private static final Set<Integer> MLM_ORE_TYPES = Sets.newHashSet(ItemID.RUNITE_ORE, ItemID.ADAMANTITE_ORE,
			ItemID.MITHRIL_ORE, ItemID.GOLD_ORE, ItemID.COAL, ItemID.GOLDEN_NUGGET);
	private static final Set<Integer> ROCK_OBSTACLES = Sets.newHashSet(ROCKFALL, ROCKFALL_26680);

	@Inject
	private MotherlodeOverlay overlay;

	@Inject
	private MotherlodeRocksOverlay rocksOverlay;

	@Inject
	private MotherlodeSackOverlay motherlodeSackOverlay;

	@Inject
	private MotherlodeConfig config;

	@Inject
	private QueryRunner queryRunner;

	@Inject
	private Client client;

	private final MotherlodeSession session = new MotherlodeSession();

	@Getter(AccessLevel.PACKAGE)
	private final Set<WallObject> veins = new HashSet<>();
	@Getter(AccessLevel.PACKAGE)
	private final Set<GameObject> rocks = new HashSet<>();

	@Provides
	MotherlodeConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MotherlodeConfig.class);
	}

	@Override
	public Collection<Overlay> getOverlays()
	{
		return Arrays.asList(overlay, rocksOverlay, motherlodeSackOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		veins.clear();
		rocks.clear();
	}

	public MotherlodeSession getSession()
	{
		return session;
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		if (inMLM)
		{
			curSackSize = client.getSetting(Varbits.SACK_NUMBER);
			boolean SACK_UPGRADED = client.getSetting(Varbits.SACK_UPGRADED) == 1;
			MAX_SACK_SIZE = SACK_UPGRADED ? 162 : 81;
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (!inMLM)
		{
			return;
		}

		if (event.getType() == ChatMessageType.FILTERED)
		{
			if (event.getMessage().equals("You manage to mine some pay-dirt."))
			{
				session.incrementPayDirtMined();
			}
		}
	}

	@Schedule(
		period = 1,
		unit = ChronoUnit.SECONDS
	)
	public void checkMining()
	{
		if (!inMLM)
		{
			return;
		}

		Instant lastPayDirtMined = session.getLastPayDirtMined();
		if (lastPayDirtMined == null)
		{
			return;
		}

		// reset recentPayDirtMined if you haven't mined anything recently
		Duration statTimeout = Duration.ofMinutes(config.statTimeout());
		Duration sinceMined = Duration.between(lastPayDirtMined, Instant.now());

		if (sinceMined.compareTo(statTimeout) >= 0)
		{
			session.resetRecent();
		}
	}

	@Subscribe
	public void onWallObjectSpawned(WallObjectSpawned event)
	{
		if (!inMLM)
		{
			return;
		}

		WallObject wallObject = event.getWallObject();
		if (MINE_SPOTS.contains(wallObject.getId()))
		{
			veins.add(wallObject);
		}
	}

	@Subscribe
	public void onWallObjectChanged(WallObjectChanged event)
	{
		if (!inMLM)
		{
			return;
		}

		WallObject previous = event.getPrevious();
		WallObject wallObject = event.getWallObject();

		veins.remove(previous);
		if (MINE_SPOTS.contains(wallObject.getId()))
		{
			veins.add(wallObject);
		}
	}

	@Subscribe
	public void onWallObjectDespawned(WallObjectDespawned event)
	{
		if (!inMLM)
		{
			return;
		}

		WallObject wallObject = event.getWallObject();
		veins.remove(wallObject);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		if (!inMLM)
		{
			return;
		}

		GameObject gameObject = event.getGameObject();
		if (ROCK_OBSTACLES.contains(gameObject.getId()))
		{
			rocks.add(gameObject);
		}
	}

	@Subscribe
	public void onGameObjectChanged(GameObjectChanged event)
	{
		if (!inMLM)
		{
			return;
		}

		GameObject previous = event.getPrevious();
		GameObject gameObject = event.getGameObject();

		rocks.remove(previous);
		if (ROCK_OBSTACLES.contains(gameObject.getId()))
		{
			rocks.add(gameObject);
		}

	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		if (!inMLM)
		{
			return;
		}

		GameObject gameObject = event.getGameObject();
		rocks.remove(gameObject);
	}

	@Subscribe
	public void onRegionChanged(MapRegionChanged event)
	{
		inMLM = isInMLM();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		inMLM = isInMLM();

		if (!inMLM)
		{
			return;
		}

		if (event.getGameState() == GameState.LOADING)
		{
			// on region changes the tiles get set to null
			veins.clear();
			rocks.clear();
		}
	}

	public Integer getDepositsLeft()
	{
		if (MAX_SACK_SIZE == 0) // check if MAX_SACK_SIZE has been initialized
		{
			return null;
		}

		double depositsLeft = 0;
		int nonPayDirtItems = 0;

		Item[] result = queryRunner.runQuery(new InventoryItemQuery(InventoryID.INVENTORY));

		assert result != null;

		int MAX_INVENTORY_SIZE = result.length;

		for (Item item : result)
		{
			// Assume that MLM ores are being banked and exclude them from the check,
			// so the user doesn't see the Overlay switch between deposits left and N/A.
			//
			// Count other items at nonPayDirtItems so depositsLeft is calculated accordingly.
			if (item.getId() != ItemID.PAYDIRT && item.getId() != -1 && !MLM_ORE_TYPES.contains(item.getId()))
			{
				nonPayDirtItems += 1;
			}
		}

		double inventorySpace = MAX_INVENTORY_SIZE - nonPayDirtItems;
		double sackSizeRemaining = MAX_SACK_SIZE - curSackSize;

		if (inventorySpace > 0 && sackSizeRemaining > 0)
		{
			depositsLeft = Math.ceil(sackSizeRemaining / inventorySpace);

		}
		else if (inventorySpace == 0)
		{
			return null;
		}

		return (int) depositsLeft;
	}

	private boolean isInMLM()
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return false;
		}

		int[] currentMapRegions = client.getMapRegions();

		// Verify that all regions exist in MOTHERLODE_MAP_REGIONS
		for (int region : currentMapRegions)
		{
			if (!MOTHERLODE_MAP_REGIONS.contains(region))
			{
				return false;
			}
		}

		return true;
	}
}
