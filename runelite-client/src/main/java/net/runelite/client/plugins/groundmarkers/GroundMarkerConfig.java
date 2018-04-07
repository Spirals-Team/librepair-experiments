package net.runelite.client.plugins.groundmarkers;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.Color;

@ConfigGroup(
	keyName = "groundMarker",
	name = "Ground marker",
	description = "Mark ground tiles"
)
public interface GroundMarkerConfig extends Config
{
	@ConfigItem(
			keyName = "markerColor",
			name = "Color of the tile",
			description = "Configures the color of marked tile"
	)
	default Color markerColor()
	{
		return Color.YELLOW;
	}

	@ConfigItem(
			keyName = "resetHotkeyEnabled",
			name = "Reset hotkey",
			description = "Toggles the state of the reset hotkey"
	)
	default boolean resetHotkeyEnabled()
	{
		return false;
	}
}
