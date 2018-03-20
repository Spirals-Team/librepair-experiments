package net.runelite.client.plugins.smoothanimations;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
	keyName = SmoothAnimationsPlugin.CONFIG_GROUP,
	name = "Smooth Animations",
	description = "Configuration for the smooth animations plugin"
)
public interface SmoothAnimationsConfig extends Config
{

	@ConfigItem(
		keyName = "smoothPlayerAnimations",
		name = "Smooth Player Animations",
		description = "Configures whether the player animations are smooth or not",
		position = 1
	)
	default boolean smoothPlayerAnimations()
	{
		return true;
	}

	@ConfigItem(
		keyName = "smoothNpcAnimations",
		name = "Smooth NPC Animations",
		description = "Configures whether the npc animations are smooth or not",
		position = 2
	)
	default boolean smoothNpcAnimations()
	{
		return true;
	}

	@ConfigItem(
		keyName = "smoothObjectAnimations",
		name = "Smooth Object Animations",
		description = "Configures whether the object animations are smooth or not",
		position = 3
	)
	default boolean smoothObjectAnimations()
	{
		return true;
	}

}
