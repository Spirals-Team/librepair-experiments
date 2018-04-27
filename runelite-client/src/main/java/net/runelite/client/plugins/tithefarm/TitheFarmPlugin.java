/*
 * Copyright (c) 2018, Unmoon <https://github.com/Unmoon>
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
package net.runelite.client.plugins.tithefarm;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.overlay.Overlay;

@Slf4j
@PluginDescriptor(
	name = "Tithe Farm"
)
public class TitheFarmPlugin extends Plugin
{
	@Inject
	private TitheFarmPluginConfig config;

	@Inject
	private TitheFarmPlantOverlay titheFarmOverlay;

	@Inject
	private TitheFarmSackOverlay titheFarmSackOverlay;

	@Inject
	private TitheFarmInventoryOverlay titheFarmInventoryOverlay;

	@Getter
	private final Set<TitheFarmPlant> plants = new HashSet<>();

	@Provides
	TitheFarmPluginConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TitheFarmPluginConfig.class);
	}

	@Override
	public Collection<Overlay> getOverlays()
	{
		return Arrays.asList(titheFarmOverlay, titheFarmSackOverlay, titheFarmInventoryOverlay);
	}

	@Schedule(period = 600, unit = ChronoUnit.MILLIS)
	public void checkPlants()
	{
		plants.removeIf(plant -> plant.getPlantTimeRelative() == 1);
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		GameObject gameObject = event.getGameObject();

		TitheFarmPlantType type = TitheFarmPlantType.getPlantType(gameObject.getId());
		if (type == null)
		{
			return;
		}

		TitheFarmPlantState state = TitheFarmPlantState.getState(gameObject.getId());

		TitheFarmPlant newPlant = new TitheFarmPlant(state, type, gameObject);
		TitheFarmPlant oldPlant = getPlantFromCollection(gameObject);

		if (oldPlant == null && newPlant.getType() != TitheFarmPlantType.EMPTY)
		{
			log.debug("Added plant {}", newPlant);
			plants.add(newPlant);
		}
		else if (oldPlant == null)
		{
			return;
		}
		else if (newPlant.getType() == TitheFarmPlantType.EMPTY)
		{
			log.debug("Removed plant {}", oldPlant);
			plants.remove(oldPlant);
		}
		else if (oldPlant.getGameObject().getId() != newPlant.getGameObject().getId())
		{
			if (oldPlant.getState() != TitheFarmPlantState.WATERED && newPlant.getState() == TitheFarmPlantState.WATERED)
			{
				log.debug("Updated plant (watered)");
				newPlant.setPlanted(oldPlant.getPlanted());
				plants.remove(oldPlant);
				plants.add(newPlant);
			}
			else
			{
				log.debug("Updated plant");
				plants.remove(oldPlant);
				plants.add(newPlant);
			}
		}
	}

	private TitheFarmPlant getPlantFromCollection(GameObject gameObject)
	{
		WorldPoint gameObjectLocation = gameObject.getWorldLocation();
		for (TitheFarmPlant plant : plants)
		{
			if (gameObjectLocation.equals(plant.getWorldLocation()))
			{
				return plant;
			}
		}
		return null;
	}
}
