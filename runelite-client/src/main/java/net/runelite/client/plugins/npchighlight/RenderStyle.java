package net.runelite.client.plugins.npchighlight;

public enum RenderStyle
{
	OFF("Off"),
	TILE("Tile"),
	HULL("Hull");

	private final String name;

	RenderStyle(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
