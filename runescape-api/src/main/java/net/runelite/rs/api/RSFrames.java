package net.runelite.rs.api;

import net.runelite.mapping.Import;

public interface RSFrames extends RSCacheableNode
{
	@Import("skeletons")
	RSFrame[] getFrames();
}
