package net.runelite.rs.api;

import net.runelite.mapping.Import;

public interface RSFrameMap extends RSNode
{
	@Import("count")
	int getCount();

	@Import("types")
	int[] getTypes();

	@Import("list")
	int[][] getList();
}
