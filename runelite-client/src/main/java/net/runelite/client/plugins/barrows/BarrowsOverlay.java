/*
 * Copyright (c) 2018, Seth <Sethtroll3@gmail.com>
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
package net.runelite.client.plugins.barrows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.ObjectComposition;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.WallObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

class BarrowsOverlay extends Overlay
{
	private static final int MAX_DISTANCE = 2350;

	private final Client client;
	private final BarrowsPlugin plugin;
	private final BarrowsConfig config;

	@Inject
	BarrowsOverlay(Client client, BarrowsPlugin plugin, BarrowsConfig config)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Player local = client.getLocalPlayer();

		// tunnels are only on z=0
		if (!plugin.getWalls().isEmpty() && client.getPlane() == 0 && config.showMinimap())
		{
			//NPC yellow dot
			List<NPC> npcs = client.getNpcs();
			for (NPC npc : npcs)
			{
				net.runelite.api.Point minimapLocation = npc.getMinimapLocation();
				if (minimapLocation != null)
				{
					graphics.setColor(Color.yellow);
					graphics.fillOval(minimapLocation.getX(), minimapLocation.getY(), 4, 4);
				}
			}

			//Render barrows walls/doors
			renderObjects(graphics, local);

			//Player white dot
			graphics.setColor(Color.white);
			graphics.fillRect(local.getMinimapLocation().getX(), local.getMinimapLocation().getY(), 3, 3);
		}
		else if (config.showBrotherLoc())
		{
			renderBarrowsBrothers(graphics);
		}

		return null;
	}

	private void renderObjects(Graphics2D graphics, Player localPlayer)
	{
		LocalPoint localLocation = localPlayer.getLocalLocation();
		for (WallObject wall : plugin.getWalls())
		{
			LocalPoint location = wall.getLocalLocation();
			if (localLocation.distanceTo(location) <= MAX_DISTANCE)
			{
				renderWalls(graphics, wall);
			}
		}

		for (GameObject ladder : plugin.getLadders())
		{
			LocalPoint location = ladder.getLocalLocation();
			if (localLocation.distanceTo(location) <= MAX_DISTANCE)
			{
				renderLadders(graphics, ladder);
			}
		}
	}

	private void renderWalls(Graphics2D graphics, WallObject wall)
	{
		net.runelite.api.Point minimapLocation = wall.getMinimapLocation();

		if (minimapLocation == null)
		{
			return;
		}

		ObjectComposition objectComp = client.getObjectDefinition(wall.getId());
		ObjectComposition impostor = objectComp.getImpostorIds() != null ? objectComp.getImpostor() : null;

		if (impostor != null && impostor.getActions()[0] != null)
		{
			graphics.setColor(Color.green);
		}
		else
		{
			graphics.setColor(Color.gray);
		}

		graphics.fillRect(minimapLocation.getX(), minimapLocation.getY(), 3, 3);
	}

	private void renderLadders(Graphics2D graphics, GameObject ladder)
	{
		net.runelite.api.Point minimapLocation = ladder.getMinimapLocation();

		if (minimapLocation == null)
		{
			return;
		}

		ObjectComposition objectComp = client.getObjectDefinition(ladder.getId());

		if (objectComp.getImpostorIds() != null && objectComp.getImpostor() != null)
		{
			graphics.setColor(Color.orange);
			graphics.fillRect(minimapLocation.getX(), minimapLocation.getY(), 6, 6);
		}
	}

	private void renderBarrowsBrothers(Graphics2D graphics)
	{
		for (BarrowsBrothers brother : BarrowsBrothers.values())
		{
			LocalPoint localLocation = LocalPoint.fromWorld(client, brother.getLocation());

			if (localLocation == null)
			{
				continue;
			}

			net.runelite.api.Point minimapText = Perspective.getCanvasTextMiniMapLocation(client, graphics,
				localLocation, brother.getName());

			if (minimapText != null)
			{
				graphics.setColor(Color.black);
				graphics.drawString(brother.getName(), minimapText.getX() + 1, minimapText.getY() + 1);

				if (client.getSetting(brother.getKilledVarbit()) > 0)
				{
					graphics.setColor(Color.red);
				}
				else
				{
					graphics.setColor(Color.cyan);
				}

				graphics.drawString(brother.getName(), minimapText.getX(), minimapText.getY());
			}
		}
	}
}