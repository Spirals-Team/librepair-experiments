package net.runelite.client.plugins.smoothanimations;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.events.ConfigChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import javax.inject.Inject;

@PluginDescriptor(
	name = "Smooth Animations",
	enabledByDefault = false
)
public class SmoothAnimationsPlugin extends Plugin
{
	static final String CONFIG_GROUP = "smoothanimations";

	@Inject
	private Client client;

	@Inject
	private SmoothAnimationsConfig config;

	@Provides
	SmoothAnimationsConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SmoothAnimationsConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		update();
	}

	@Override
	protected void shutDown() throws Exception
	{
		client.setInterpolatePlayerAnimations(false);
		client.setInterpolateNpcAnimations(false);
		client.setInterpolateObjectAnimations(false);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals(CONFIG_GROUP))
		{
			update();
		}
	}

	public void update()
	{
		client.setInterpolatePlayerAnimations(config.smoothPlayerAnimations());
		client.setInterpolateNpcAnimations(config.smoothNpcAnimations());
		client.setInterpolateObjectAnimations(config.smoothObjectAnimations());
	}
}
