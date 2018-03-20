package net.runelite.rs.api;

import net.runelite.api.Renderable;
import net.runelite.mapping.Import;

public interface RSDynamicObject extends Renderable, RSRenderable
{
	@Import("id")
	int getId();

	@Import("animFrame")
	int getAnimFrame();

	@Import("animFrame")
	void setAnimFrame(int frame);

	@Import("animCycleCount")
	int getAnimCycleCount();
}
