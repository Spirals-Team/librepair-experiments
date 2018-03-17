/*
 * Copyright (c) 2017, Aria <aria@ar1as.space>
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
package net.runelite.client.plugins.grounditems;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemLayer;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.Node;
import net.runelite.api.Region;
import net.runelite.api.Tile;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.http.api.item.ItemPrice;

@PluginDescriptor(
	name = "Ground Items"
)
public class GroundItemsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private GroundItemsConfig config;

	@Inject
	private GroundItemsOverlay overlay;

	@Provides
	GroundItemsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GroundItemsConfig.class);
	}

	@Override
	public Overlay getOverlay()
	{
		return overlay;
	}

	private ItemPrice getItemPrice(ItemComposition itemComposition)
	{
		if (itemComposition.getNote() != -1)
		{
			return itemManager.getItemPriceAsync(itemComposition.getLinkedNoteId());
		}
		else
		{
			return itemManager.getItemPriceAsync(itemComposition.getId());
		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		if ((config.highlightMenuOption() || config.highlightMenuItemName()) && event.getOption().equals("Take")
				&& event.getType() == MenuAction.GROUND_ITEM_THIRD_OPTION.getId())
		{
			String hiddenItemsStr = config.getHiddenItems().toLowerCase();
			List<String> hiddenItems = Arrays.asList(hiddenItemsStr.split(GroundItemsOverlay.DELIMITER_REGEX));

			String highlightItemsStr = config.getHighlightItems().toLowerCase();
			List<String> highlightedItems = Arrays.asList(highlightItemsStr.split(GroundItemsOverlay.DELIMITER_REGEX));

			int itemId = event.getIdentifier();
			ItemComposition itemComposition = client.getItemDefinition(itemId);
			String name = itemComposition.getName().toLowerCase();

			if (hiddenItems.contains(name))
			{
				return;
			}

			Region region = client.getRegion();
			Tile tile = region.getTiles()[client.getPlane()][event.getActionParam0()][event.getActionParam1()];
			ItemLayer itemLayer = tile.getItemLayer();
			if (itemLayer == null)
			{
				return;
			}

			MenuEntry[] menuEntries = client.getMenuEntries();
			MenuEntry lastEntry = menuEntries[menuEntries.length - 1];

			int quantity = 1;
			Node current = itemLayer.getBottom();
			while (current instanceof Item)
			{
				Item item = (Item) current;
				if (item.getId() == itemId)
				{
					quantity = item.getQuantity();
				}
				current = current.getNext();
			}

			ItemPrice itemPrice = getItemPrice(itemComposition);
			int price = itemPrice == null ? itemComposition.getPrice() : itemPrice.getPrice();
			int cost = quantity * price;

			Color color = null;

			if (cost >= GroundItemsOverlay.LOW_VALUE)
			{
				color = overlay.getCostColor(cost);
			}

			if (highlightedItems.contains(name))
			{
				color = config.highlightedColor();
			}

			if (color != null)
			{
				String hexColor = Integer.toHexString(color.getRGB() & 0xFFFFFF);
				String colTag = "<col=" + hexColor + ">";
				if (config.highlightMenuOption())
				{
					lastEntry.setOption(colTag + "Take");
				}
				if (config.highlightMenuItemName())
				{
					String target = lastEntry.getTarget().substring(lastEntry.getTarget().indexOf(">") + 1);
					lastEntry.setTarget(colTag + target);
				}
			}

			if (config.showMenuItemQuantities() && itemComposition.isStackable() && quantity > 1)
			{
				lastEntry.setTarget(lastEntry.getTarget() + " (" + quantity + ")");
			}

			client.setMenuEntries(menuEntries);
		}
	}

}
