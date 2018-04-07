package net.runelite.client.plugins.groundmarkers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.config.Config;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.MouseListener;

public class GroundMarkerInputListener extends MouseListener implements KeyListener
{
	private static final int HOTKEY = KeyEvent.VK_ALT;
	private static final int HOTKEYR = KeyEvent.VK_SHIFT;

	@Inject
	private Client client;

	@Inject
	private GroundMarkerConfig config;

	@Inject
	private GroundMarkerPlugin plugin;

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == HOTKEY)
		{
			plugin.setHotKeyPressed(true);
		}
		else if (e.getKeyCode() == HOTKEYR)
		{
			plugin.setResetHotkeyPressed(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == HOTKEY)
		{
			plugin.setHotKeyPressed(false);
		}
		else if (e.getKeyCode() == HOTKEYR)
		{
			plugin.setResetHotkeyPressed(false);
		}
	}

	@Override
	public MouseEvent mousePressed(MouseEvent e)
	{
		if (plugin.isHotKeyPressed())
		{
			if (e.getButton() == MouseEvent.BUTTON3)
			{
				if (plugin.isResetHotkeyPressed() && config.resetHotkeyEnabled())
				{
					plugin.clearPoints();
				}
				else
				{
					plugin.markTile(client.getMouseCanvasPosition());
				}
			}
		}

		return e;
	}
}
