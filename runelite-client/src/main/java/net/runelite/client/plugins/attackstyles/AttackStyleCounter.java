package net.runelite.client.plugins.attackstyles;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.Counter;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class AttackStyleCounter extends Counter
{
	private AttackStyle attackStyle;

	AttackStyleCounter(BufferedImage image, Plugin plugin, String text, AttackStyle attackStyle)
	{
		super(image, plugin, text);
		this.attackStyle = attackStyle;
	}

	@Override
	public String getTooltip()
	{
		return new String(attackStyle.getName());
	}

	@Override
	public Color getTextColor()
	{
		return Color.RED;
	}
}
